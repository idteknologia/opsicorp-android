package com.mobile.travelaja.module.settlement.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
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
import opsigo.com.domainlayer.model.settlement.Bank
import opsigo.com.domainlayer.model.settlement.RateStayResult
import opsigo.com.domainlayer.model.settlement.Settlement
import opsigo.com.domainlayer.model.settlement.SubmitSettlement
import opsigo.com.domainlayer.model.trip.DetailTripResult
import opsigo.com.domainlayer.model.trip.Trip

class SettlementViewModel(private val repository: SettlementRepository) : ViewModel() {
    val buttonNextEnabled = ObservableBoolean(false)
    val selectedCode = ObservableField<String>()

    val isEnabledDetailInformation = ObservableBoolean(false)
    val isEnableOverNight = ObservableBoolean(false)

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

    private val _dayValueEvent = MutableLiveData<Event<Int>>()
    val dayValueEvent : LiveData<Event<Int>> = _dayValueEvent
    val loadingPostDay = MutableLiveData(false)
    fun checkedOverNight(checked: Boolean) {
        isEnableOverNight.set(checked)
        if (checked) {
            updateRateDay(1)
        } else {
            submitSettlement.SpecificAreaTariff = 0
            submitSettlement.SpecificAreaDays = 0
            _dayValueEvent.value = Event(0)
            loadingPostDay.value = false
            job?.cancel()
        }
    }

    private var job: Job? = null
    fun updateRateDay(countDay: Int) {
        val typeWork = submitSettlement.Golper
        loadingPostDay.value =  true
        job?.cancel()
        job = viewModelScope.launch {
            val result = repository.updateRateOvernight(countDay, typeWork)
            compareRateDay(result,countDay)
        }
    }

    private fun compareRateDay(result: Result<RateStayResult>,countDay: Int) {
        if (result is Result.Success) {
            submitSettlement.SpecificAreaTariff = result.data.result
            submitSettlement.SpecificAreaDays = countDay
        } else {
            val e = result as Result.Error
            val countDayOld = submitSettlement.SpecificAreaDays
            if (countDayOld == 0) {
                checkedOverNight(false)
            } else {
                submitSettlement.SpecificAreaDays = countDayOld
                _dayValueEvent.postValue(Event(countDayOld))
            }
            _error.value = Event(e.exception)
        }
        loadingPostDay.value = false
    }

}