package com.mobile.travelaja.module.settlement.viewmodel

import android.renderscript.Sampler
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.mobile.travelaja.module.settlement.repository.SettlementRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import opsigo.com.datalayer.model.result.Result
import opsigo.com.domainlayer.model.Event
import opsigo.com.domainlayer.model.settlement.*
import opsigo.com.domainlayer.model.trip.DetailTripResult
import opsigo.com.domainlayer.model.trip.Trip

class SettlementViewModel(private val repository: SettlementRepository) : ViewModel() {
    val buttonNextEnabled = ObservableBoolean(false)
    val selectedCode = ObservableField<String>()

    val isEnabledDetailInformation = ObservableBoolean(false)
    val isEnableOverNight = ObservableBoolean(false)
    val isTravellingEnabled = ObservableBoolean(false)

    private var currentResult: Flow<PagingData<Settlement>>? = null

    private val _banks = MutableLiveData<List<Bank>>()
    val bank: LiveData<List<Bank>> = _banks

    private val _tripCodes = MutableLiveData<List<Trip>>()
    val tripCodes: LiveData<List<Trip>> = _tripCodes

    private val _detailTrip = MutableLiveData<DetailTripResult>()
    val detailTrip: LiveData<DetailTripResult> = _detailTrip

    private val _dayRate = MutableLiveData<RateStayResult>()
    val dayRate: LiveData<RateStayResult> = _dayRate

    private val _error = MutableLiveData<Event<Throwable>>()
    val error: LiveData<Event<Throwable>> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    val submitSettlement = SubmitSettlement()
    private var _idTrip = ""
    private var _tripCode = ""

    private val _dayValueEvent = MutableLiveData<Event<Int>>()
    val dayValueEvent: LiveData<Event<Int>> = _dayValueEvent
    val loadingPostDay = MutableLiveData(false)
    private var job: Job? = null

    var modeTransports = listOf<ModeTransport>()
    var modeFlight = ""
    val isRemoveVisible = ObservableBoolean(false)
    var jobCalculateTransport: Job? = null

    /*
      cek your id trip is not same from default or not empty
     */
    fun getDetailTrip(idTrip: String) {
        if (idTrip.isEmpty() || idTrip == _idTrip) {
            return
        }
        viewModelScope.launch {
            val result = repository.getDetailTrip(idTrip)
            compareDetailTrip(result)
        }
    }

    private fun compareDetailTrip(result: Result<DetailTripResult>) {
        if (result is Result.Success) {
            _tripCode = submitSettlement.TripCode
            _idTrip = submitSettlement.TripId
            submitSettlement.Golper = result.data.trip.Golper
            _detailTrip.value = result.data
        } else {
            submitSettlement.TripCode = _tripCode
            submitSettlement.TripId = _idTrip
            val e = result as Result.Error
            _error.value = Event(e.exception)
        }
        _loading.value = false
    }

    //Todo observe list trip code
    fun getTripCodes() {
        _loading.value = true
        viewModelScope.launch {
            val result = repository.getTripCodes()
            compareTripCodes(result)
        }
    }

    private fun compareTripCodes(result: Result<List<Trip>>) {
        if (result is Result.Success) {
            _tripCodes.value = result.data
        } else {
            val e = result as Result.Error
            _error.value = Event(e.exception)
        }
        _loading.value = false
    }

    fun getBank() {
        _loading.value = true
        viewModelScope.launch {
            val result = repository.getBanks()
            compareBank(result)
        }
    }

    private fun compareBank(result: Result<List<Bank>>) {
        if (result is Result.Success) {
            _banks.value = result.data
        } else {
            val e = result as Result.Error
            _error.value = Event(e.exception)
        }
        _loading.value = false
    }

    fun getSettlement(query: MutableMap<String, Any>): Flow<PagingData<Settlement>> {
        val lastResult = currentResult
        if (lastResult != null) {
            return lastResult
        }
        val newResult: Flow<PagingData<Settlement>> = repository.getSettlements(query)
        currentResult = newResult
        return newResult
    }

    fun checkedInformation(checked: Boolean) {
        isEnabledDetailInformation.set(checked)
    }

    fun checkedTravelling(checked: Boolean) {
        isTravellingEnabled.set(checked)
        if (!checked && isEnableOverNight.get()) {
            checkedOverNight(false)
        }
    }

    fun checkedOverNight(checked: Boolean) {
        isEnableOverNight.set(checked)
        if (checked) {
            updateRateDay()
        } else {
            submitSettlement.SpecificAreaTariff = 0
            submitSettlement.SpecificAreaDays = 0
            submitSettlement.TotalSpecificAreaExpense = 0
            _dayValueEvent.value = Event(0)
            loadingPostDay.value = false
            job?.cancel()
        }
    }

    fun calculateOvernight(countDay: Int) {
        val price = submitSettlement.SpecificAreaTariff
        submitSettlement.SpecificAreaDays = countDay
        val totalPrice = price.toInt() * countDay
        submitSettlement.TotalSpecificAreaExpense = totalPrice
    }

    private fun updateRateDay() {
        val typeWork = submitSettlement.Golper
        loadingPostDay.value = true
        job?.cancel()
        job = viewModelScope.launch {
            val result = repository.updateRateOvernight(typeWork, 1)
            compareRateDay(result)
        }
    }

    private fun compareRateDay(result: Result<RateStayResult>) {
        if (result is Result.Success) {
            submitSettlement.SpecificAreaTariff = result.data.result
            submitSettlement.TotalSpecificAreaExpense = result.data.result
            submitSettlement.SpecificAreaDays = 1
        } else {
            val e = result as Result.Error
            checkedOverNight(false)
            _error.value = Event(e.exception)
        }
        loadingPostDay.value = false
    }


    //Todo Mode Transport Observe
    private val _modeTransports = MutableLiveData<List<ModeTransport>>()
    val modeTransport : LiveData<List<ModeTransport>> = _modeTransports
    fun getModeTransports() {
        if (!_modeTransports.value.isNullOrEmpty()){
            return
        }
        viewModelScope.launch {
            val result = repository.getModeTransport()
            compareModeTransport(result)
        }
    }

    private fun compareModeTransport(result: Result<List<ModeTransport>>) {
        if (result is Result.Success) {
            _modeTransports.value = result.data
            modeFlight = result.data.last { it.Value == 1 }.Text
        }else{
            val t = result as Result.Error
            _error.value = Event(t.exception)
        }
    }


    fun calculateTransportByCity(city: String, pos: Int) {
        val transportExpenses = getTransportExpense(pos)
        val type = transportExpenses.TransportationType
        var mode = transportExpenses.TransportationMode
        if (modeFlight.isNotEmpty() && mode.isEmpty() && type == 1) {
            mode = modeFlight
        }
        calculateTransportExpense(
            city,
            type,
            mode,
            pos
        )
    }

    fun switchNonFlight(checked: Boolean, position: Int) {
        if (checked) {
            if (!_modeTransports.value.isNullOrEmpty()){

            }else{
                getModeTransports()
            }
        } else {
            calculateTransportByType(1, modeFlight, position)
        }
    }

    fun calculateTransportByType(type: Int, mode: String, pos: Int) {
        val city = getTransportExpense(pos).City
        calculateTransportExpense(city, type, mode, pos)
    }

    fun calculateTransportExpense(city: String, type: Int, mode: String, pos: Int) {
        jobCalculateTransport?.cancel()
        val body = mutableMapOf<String, Any>(CITY to city, TYPE to type, MODA to mode)
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
        if (result is Result.Success) {
            val tripType = getTransportExpense(pos).TripType
            val amount = result.data.amount
            getTransportExpense(pos).Amount = result.data.amount
            getTransportExpense(pos).City = city
            getTransportExpense(pos).TransportationType = type
            getTransportExpense(pos).TransportationMode = mode
            getTransportExpense(pos).TotalAmount = if (tripType == 0) amount else amount * 2
        } else {
            val t = result as Result.Error
            _error.value = Event(t.exception)
        }
    }

    fun getTransportExpense(pos: Int): TransportExpenses {
        return submitSettlement.TransportExpenses[pos]
    }

    fun checkedSwitchRoundtrip(checked: Boolean, pos: Int) {
        getTransportExpense(pos).TripType = if (checked) 1 else 0
        val amount = getTransportExpense(pos).Amount.toDouble()
        getTransportExpense(pos).TotalAmount = if (!checked) amount else amount * 2
    }

    fun getVisibleRemove(): Boolean {
        return submitSettlement.TransportExpenses.size > 1
    }

    fun addTransport() {
        submitSettlement.TransportExpenses.add(TransportExpenses())
        isRemoveVisible.set(true)
    }

    fun removeTransport(pos: Int) {
        submitSettlement.TransportExpenses.removeAt(pos)
        isRemoveVisible.set(submitSettlement.TransportExpenses.size > 1)
    }

    fun isEmptyCityTransport(): Boolean {
        val isEmptyCity = submitSettlement.TransportExpenses.none {
            it.City.isEmpty()
        }
        return !isEmptyCity
    }

    companion object {
        const val TYPE = "Type"
        const val MODA = "Moda"
        const val CITY = "City"
    }

}