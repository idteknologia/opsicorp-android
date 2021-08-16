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
import com.mobile.travelaja.module.settlement.view.TransportExpenseFragment.Companion.WARNING_SAME_DATA
import com.mobile.travelaja.module.settlement.view.TransportExpenseFragment.Companion.WARNING_ISLOADING

class TransportExpenseViewModel(private val repository: SettlementRepository) : ViewModel() {

    var jobCalculateTransport: Job? = null
    var modeFlight: ModeTransport? = null

    private val _hasUpdate = MutableLiveData<Boolean>()
    val hasUpdate : LiveData<Boolean> = _hasUpdate

    private val _error = MutableLiveData<Event<Throwable>>()
    val error: LiveData<Event<Throwable>> = _error

    private val _warning = MutableLiveData<Event<Int>>()
    val warning: LiveData<Event<Int>> = _warning

    private var _loading = false
    val transportExpenses = mutableListOf<TransportExpenses>()
    val isRemoveVisible = ObservableBoolean(false)
    val selection: MutableMap<String, List<ModeTransport>> = mutableMapOf()
    val indexEmpty = ObservableInt(-1)

    var modeTransports = mutableListOf<ModeTransport>()
//    val totalTransport = MutableLiveData<Number>(0)

    private val _total = MutableLiveData<Number>(0)
    val total: LiveData<Number> = _total

    fun getModeTransports(city: String, pos: Int) {
        if (modeTransports.isNotEmpty()) {
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
            modeTransports.addAll(result.data.toMutableList())
            modeFlight = result.data.last { it.Value == SettlementViewModel.TYPE_FLIGHT }
            modeFlight?.Disabled = true
            calculateTransportByCity(city, pos)
        } else {
            val t = result as Result.Error
            _error.value = Event(t.exception)
        }
    }


    fun calculateTransportByCity(city: String, pos: Int) {
        if (_loading){
            _warning.value = Event(WARNING_ISLOADING)
            return
        }
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
        if (_loading){
            _warning.value = Event(WARNING_ISLOADING)
            return
        }
        val transport = getTransportExpense(pos)
        val city = transport.City
        val tempId = transport.TransportationMode
        val tempType = transport.TransportationType
        if (hasSameTransport(city, mode)) {
            _warning.value = Event(WARNING_SAME_DATA)
            getTransportExpense(pos).TransportationMode = tempId
            getTransportExpense(pos).TransportationType = tempType
            return
        }
        calculateTransportExpense(city, type, mode, pos)
    }

    //Todo fetch transport calculate
    fun calculateTransportExpense(city: String, type: Int, mode: String, pos: Int) {
        if (_loading){
            _warning.value = Event(WARNING_ISLOADING)
            return
        }
        _loading = true
        val body = mutableMapOf<String, Any>(
            SettlementViewModel.CITY to city,
            SettlementViewModel.TYPE to type,
            SettlementViewModel.MODA to mode
        )
        viewModelScope.launch {
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
        val idMode = if (result is Result.Success) modeTransports.first { it.Text == mode }.id else transport.TransportationMode
        transport.TransportationType = if (result is Result.Success) type else transport.TransportationType
        transport.TransportationMode = idMode
        if (result is Result.Success) {
            val tempAmount = transport.TotalAmount
            val amount = result.data.amount
            val tripType = transport.TripType
            transport.Amount = amount
            transport.City = city
            transport.nameTransportationMode = mode
            transport.TotalAmount = if (tripType == 0) amount else amount * 2
            updateTotal(amount, true,tempAmount)
            _hasUpdate.value = true
        } else {
            val t = result as Result.Error
            _error.value = Event(t.exception)
        }
        _loading = false
    }

    private fun hasSameTransport(city: String, mode: String): Boolean {
        return transportExpenses.any { it.City == city && it.nameTransportationMode == mode }
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
            updateTotal(amount, true,0)
        } else {
            updateTotal(amount, false,0)
        }
        _hasUpdate.value = true
    }

    fun getVisibleRemove(): Boolean {
        return transportExpenses.size > 1
    }

    //Todo add item Transport Expense
    fun addTransport() {
        transportExpenses.add(TransportExpenses())
        isRemoveVisible.set(true)
        _hasUpdate.value = true
    }

    //Todo remove item Transport Expense
    fun removeTransport(pos: Int) {
        val totalAmount = getTransportExpense(pos).TotalAmount
        transportExpenses.removeAt(pos)
        updateTotal(totalAmount, false,0)
        isRemoveVisible.set(transportExpenses.size > 1)
        _hasUpdate.value = true
    }

    fun indexFirstEmpty() : Int {
      val i =   transportExpenses.indexOfFirst {
          it.City.isEmpty() || (it.TransportationType == NON_FLIGHT && it.nameTransportationMode.isEmpty()) }
        indexEmpty.set(i)
      return  i
    }

    fun switchNonFlight(checked: Boolean, pos: Int) {
        if (checked) {
            val transport = getTransportExpense(pos)
            val totalAmount = transport.TotalAmount
            transport.TransportationType = NON_FLIGHT
            transport.nameTransportationMode = ""
            transport.Amount = 0
            transport.TotalAmount = 0
            updateTotal(totalAmount, false,0)
        } else {
            calculateTransportByType(TYPE_FLIGHT, modeFlight!!.Text, pos)
        }
        _hasUpdate.value = true
    }

    private fun updateTotal(amount: Number, isAdding: Boolean,tempAmount : Number) {
        var total = _total.value?.toDouble() ?: 0.0
        if (isAdding) {
            total -= tempAmount.toDouble()
            total += amount.toDouble()
        } else {
            total -= amount.toDouble()
        }
        _total.value = total
    }
    fun addTotal(total: Double) {
        _total.value = total
    }

    fun addingTransportExpense(list : List<TransportExpenses>){
        transportExpenses.clear()
        list.forEach {
            val data = TransportExpenses()
            data.City = it.City
            data.Amount = it.Amount
            data.Currency = it.Currency
            data.TotalAmount = it.TotalAmount
            data.TransportationMode = it.TransportationMode
            data.TransportationType = it.TransportationType
            data.nameTransportationMode = it.nameTransportationMode
            data.TripType = it.TripType
            transportExpenses.add(data)
        }
    }

    companion object {
        const val TYPE = "Type"
        const val CITY = "City"
        const val TYPE_FLIGHT = 1
        const val NON_FLIGHT = 2
    }
}