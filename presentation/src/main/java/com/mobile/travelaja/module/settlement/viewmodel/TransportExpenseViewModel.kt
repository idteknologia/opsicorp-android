package com.mobile.travelaja.module.settlement.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.travelaja.module.settlement.repository.SettlementRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import opsigo.com.datalayer.model.result.Result
import opsigo.com.domainlayer.model.Event
import opsigo.com.domainlayer.model.settlement.CalculateTransportResult
import opsigo.com.domainlayer.model.settlement.ModeTransport
import opsigo.com.domainlayer.model.settlement.TransportExpenses

class TransportExpenseViewModel(private val repository: SettlementRepository) : ViewModel() {

    var jobCalculateTransport: Job? = null
    var modeFlight: ModeTransport? = null

    private val _error = MutableLiveData<Event<Throwable>>()
    val error: LiveData<Event<Throwable>> = _error

    private val _warning = MutableLiveData<Event<Boolean>>()
    val warning: LiveData<Event<Boolean>> = _warning

    val transportExpenses = mutableListOf(TransportExpenses())
    val isRemoveVisible = ObservableBoolean(false)
    val selection: MutableMap<String, List<ModeTransport>> = mutableMapOf()

    private val _modeTransports = MutableLiveData<List<ModeTransport>>()
    val modeTransport: LiveData<List<ModeTransport>> = _modeTransports

    val totalTransport = MutableLiveData<Number>(0)

    fun getModeTransports(city: String, pos: Int) {
        if (_modeTransports.value != null) {
            calculateTransportByCity(city, pos)
        } else {
            viewModelScope.launch {
                val result = repository.getModeTransport()
                compareModeTransport(result, city, pos)
            }
        }
    }

    private fun compareModeTransport(result: Result<List<ModeTransport>>, city: String, pos: Int) {
        if (result is Result.Success) {
            _modeTransports.value = result.data
            modeFlight = result.data.last { it.Value == SettlementViewModel.TYPE_FLIGHT }
            modeFlight?.Disabled = true
            calculateTransportByCity(city, pos)
        } else {
            val t = result as Result.Error
            _error.value = Event(t.exception)
        }
    }


    fun calculateTransportByCity(city: String, pos: Int) {
        val mode = modeFlight!!.Text
        if (hasSameTransport(city, mode)) {
            getTransportExpense(pos).TransportationType = NON_FLIGHT
            getTransportExpense(pos).City = city
            return
        }
        calculateTransportExpense(
            city,
            TYPE_FLIGHT,
            mode,
            pos
        )
    }

    fun calculateTransportByType(type: Int, mode: String, pos: Int) {
        val transport = getTransportExpense(pos)
        val city = transport.City
        val tempId = transport.transportationModeId
        val tempType = transport.TransportationType
        if (hasSameTransport(city, mode)) {
            _warning.value = Event(true)
            getTransportExpense(pos).transportationModeId = tempId
            getTransportExpense(pos).TransportationType = tempType
            return
        }
        calculateTransportExpense(city, type, mode, pos)
    }

    private fun hasFlightByCity(city: String, mode: String): Boolean {
        return selection[city]?.any { it.Text == mode && it.Disabled } ?: false
    }

    fun calculateTransportExpense(city: String, type: Int, mode: String, pos: Int) {
        jobCalculateTransport?.cancel()
        val body = mutableMapOf<String, Any>(
            SettlementViewModel.CITY to city,
            SettlementViewModel.TYPE to type,
            SettlementViewModel.MODA to mode
        )
        jobCalculateTransport = viewModelScope.launch {
            val result = repository.calculateTransportExpense(body)
            compareCalculateTransportExpense(result, pos, city, type, mode)
        }
    }

    private fun compareCalculateTransportExpense(
        result: Result<CalculateTransportResult>,
        pos: Int,
        city: String, type: Int, mode: String
    ) {
        val transport = getTransportExpense(pos)
        val idMode = if (result is Result.Success) _modeTransports.value?.first { it.Text == mode }?.id else transport.transportationModeId
        transport.TransportationType = if (result is Result.Success) type else transport.TransportationType
        transport.transportationModeId = idMode ?: 0
        if (result is Result.Success) {
            val amount = result.data.amount
            val tripType = transport.TripType
            transport.Amount = amount
            transport.City = city
            transport.TransportationMode = mode
            transport.TotalAmount = if (tripType == 0) amount else amount * 2
            updateTotal(amount, true)
        } else {
            val t = result as Result.Error
            _error.value = Event(t.exception)
        }
    }

    private fun putSelected(city: String) {
        if (!_modeTransports.value.isNullOrEmpty()) {
            selection[city] = _modeTransports.value!!
            selection[city]?.last { it.Value == TYPE_FLIGHT }?.Disabled = true
        }
    }

    private fun hasSameTransport(city: String, mode: String): Boolean {
        return transportExpenses.any { it.City == city && it.TransportationMode == mode }
    }

    fun getTransportExpense(pos: Int): TransportExpenses {
        return transportExpenses[pos]
    }

    //Todo switch round trip
    fun checkedSwitchRoundtrip(checked: Boolean, pos: Int) {
        getTransportExpense(pos).TripType = if (checked) 1 else 0
        val amount = getTransportExpense(pos).Amount.toDouble()
        getTransportExpense(pos).TotalAmount = if (!checked) amount else amount * 2
        if (checked) {
            updateTotal(amount, true)
        } else {
            updateTotal(amount, false)
        }
    }

    fun getVisibleRemove(): Boolean {
        return transportExpenses.size > 1
    }

    //Todo add item Transport Expense
    fun addTransport() {
        transportExpenses.add(TransportExpenses())
        isRemoveVisible.set(true)
    }

    //Todo remove item Transport Expense
    fun removeTransport(pos: Int) {
        val totalAmount = getTransportExpense(pos).TotalAmount
        transportExpenses.removeAt(pos)
        updateTotal(totalAmount, false)
        isRemoveVisible.set(transportExpenses.size > 1)
    }

    fun isEmptyCityTransport(): Boolean {
        val nonEmptyCity = transportExpenses.none {
            it.City.isEmpty()
        }
        val nonEmptyMode = transportExpenses.none {
            it.TransportationType == NON_FLIGHT && it.TransportationMode.isEmpty()
        }
        return !nonEmptyCity || !nonEmptyMode
    }

    fun switchNonFlight(checked: Boolean, pos: Int) {
        if (checked) {
            val transport = getTransportExpense(pos)
            val totalAmount = transport.TotalAmount
            transport.TransportationType = NON_FLIGHT
            transport.TransportationMode = ""
            transport.Amount = 0
            transport.TotalAmount = 0
            updateTotal(totalAmount, false)
        } else {
            calculateTransportByType(TYPE_FLIGHT, modeFlight!!.Text, pos)
        }
    }

    private fun updateTotal(amount: Number, isAdding: Boolean) {
        var total = totalTransport.value?.toDouble() ?: 0.0
        if (isAdding) {
            total += amount.toDouble()
        } else {
            total -= amount.toDouble()
        }
        totalTransport.value = total
    }

    companion object {
        const val TYPE = "Type"
        const val MODA = "Moda"
        const val CITY = "City"
        const val TYPE_FLIGHT = 1
        const val NON_FLIGHT = 2
    }
}