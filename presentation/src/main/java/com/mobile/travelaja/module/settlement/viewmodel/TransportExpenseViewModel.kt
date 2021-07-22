package com.mobile.travelaja.module.settlement.viewmodel

import androidx.databinding.ObservableBoolean
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
    var modeFlight : ModeTransport ?= null

    private val _error = MutableLiveData<Event<Throwable>>()
    val error: LiveData<Event<Throwable>> = _error

    val transportExpenses = mutableListOf(TransportExpenses())
    val isRemoveVisible = ObservableBoolean(false)
    val selection: MutableMap<String, List<ModeTransport>> = mutableMapOf()


    private val _modeTransports = MutableLiveData<List<ModeTransport>>()
    val modeTransport: LiveData<List<ModeTransport>> = _modeTransports
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
        val transportExpenses = getTransportExpense(pos)
        val type = transportExpenses.TransportationType
        var mode = transportExpenses.TransportationMode
        if (mode.isEmpty()){
            getTransportExpense(pos).TransportationType = NON_FLIGHT
            getTransportExpense(pos).City = city
            return
        }
        if (modeFlight?.Text != null && mode.isEmpty() && type == 1) {
            mode = modeFlight!!.Text
        }
        calculateTransportExpense(
            city,
            type,
            mode,
            pos
        )
    }

    private fun hasFlightByCity(city: String,mode: String) : Boolean{
        return selection[city]?.any { it.Text ==  mode && it.Disabled} ?: false
    }

    fun calculateTransportByType(type: Int, mode: String, pos: Int) {
        val city = getTransportExpense(pos).City
        calculateTransportExpense(city, type, mode, pos)
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
        val idMode = if (result is Result.Success) _modeTransports.value?.first { it.Text == mode }?.id else getTransportExpense(pos).transportationModeId
        if (result is Result.Success) {
            val tripType = getTransportExpense(pos).TripType
            val amount = result.data.amount
            getTransportExpense(pos).Amount = result.data.amount
            getTransportExpense(pos).City = city
            getTransportExpense(pos).TransportationType = type
            getTransportExpense(pos).TransportationMode = mode
            getTransportExpense(pos).transportationModeId = idMode ?: 0
            getTransportExpense(pos).TotalAmount = if (tripType == 0) amount else amount * 2
            if (!selection.containsKey(city)) {
                putSelected(city)
            }
        } else {
            getTransportExpense(pos).transportationModeId = idMode ?: 0
            val t = result as Result.Error
            _error.value = Event(t.exception)
        }
    }

    private fun putSelected(city: String) {
        if (!_modeTransports.value.isNullOrEmpty()){
            selection[city] = _modeTransports.value!!
            selection[city]?.last { it.Value == TYPE_FLIGHT }?.Disabled = true
        }
    }

    private fun hasSameTransport(city : String , mode : String): Boolean{
        return transportExpenses.any { it.City == city && it.TransportationMode == mode }
    }

    fun getTransportExpense(pos: Int): TransportExpenses {
        return transportExpenses[pos]
    }

    fun checkedSwitchRoundtrip(checked: Boolean, pos: Int) {
        getTransportExpense(pos).TripType = if (checked) 1 else 0
        val amount = getTransportExpense(pos).Amount.toDouble()
        getTransportExpense(pos).TotalAmount = if (!checked) amount else amount * 2
    }

    fun getVisibleRemove(): Boolean {
        return transportExpenses.size > 1
    }

    fun addTransport() {
        transportExpenses.add(TransportExpenses())
        isRemoveVisible.set(true)
    }

    fun removeTransport(pos: Int) {
        transportExpenses.removeAt(pos)
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

    val type = ObservableInt(-1)

    companion object {
        const val TYPE = "Type"
        const val MODA = "Moda"
        const val CITY = "City"
        const val TYPE_FLIGHT = 1
        const val NON_FLIGHT = 2
    }
}