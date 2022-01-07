package com.mobile.travelaja.module.settlement.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.travelaja.module.settlement.repository.SettlementRepository
import kotlinx.coroutines.launch
import opsigo.com.datalayer.model.result.Result
import opsigo.com.domainlayer.model.Event
import opsigo.com.domainlayer.model.settlement.*

class DraftSettlementViewModel(private val repository: SettlementRepository) : ViewModel() {
    var submitSettlement = MutableLiveData(DetailSettlement())
    private val _error = MutableLiveData<Event<Throwable>>()
    val error: LiveData<Event<Throwable>> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _successSubmit = MutableLiveData<Event<Boolean>>()
    val successSubmit: LiveData<Event<Boolean>> = _successSubmit

    var tripCode: String = ""
    fun getDetailTrip(path: String, _tripCode: String) {
        if (this.tripCode.isNotEmpty()) {
            return
        }
        this.tripCode = _tripCode
        _loading.value = true
        viewModelScope.launch {
            val result = repository.getDetailDraft(path, tripCode)
            compareDetailTrip(result)
        }
    }

    private fun compareDetailTrip(result: Result<DetailDraftSettlement>) {
        if (result is Result.Success) {
            completingDetail(result.data.model)
        } else {
            val e = result as Result.Error
            _error.value = Event(e.exception)
        }
        _loading.value = false
    }

    private fun completingDetail(detail: DetailSettlement?) {
        detail?.let {
            val totTransportExpense =
                detail.TransportExpenses.filter { it.Currency.isNullOrEmpty() || it.Currency == SettlementViewModel.IDR }
                    .sumByDouble { it.TotalAmount.toDouble() }
            val totOtherTransportExpense =
                detail.OtherTransportExpenses.filter { it.Currency.isNullOrEmpty() || it.Currency == SettlementViewModel.IDR }
                    .sumByDouble { it.TotalAmount.toDouble() }
            val totOtherIdr =
                detail.OtherExpense.filter { it.Currency.isNullOrEmpty() || it.Currency == SettlementViewModel.IDR }
                    .sumByDouble { it.Amount.toDouble() }
            val totOtherUsd = detail.OtherExpense.filter { it.Currency == SettlementViewModel.USD }
                .sumByDouble { it.Amount.toDouble() }
            detail.TotalTransportExpense = totTransportExpense
            detail.TotalOtherTransportExpense = totOtherTransportExpense
            detail.TotalOtherExpense = totOtherIdr
            detail.TotalOtherExpenseUsd = totOtherUsd
            var laundry = 0.0
            if (detail.AmountLaundry != null) {
                laundry = detail.AmountLaundry!!.toDouble()
            }
            detail.TotalExpenseSubmit =
                totOtherIdr + totTransportExpense + totOtherTransportExpense + laundry + detail.getTotalSpecificArea()
                    .toDouble()
            detail.TotalExpenseSubmitUsd = totOtherUsd
            addPassengers(detail.TripItemTypes)
            submitSettlement.value = detail
        }
    }

    val passengers = ObservableArrayList<Passenger>()
    private fun addPassengers(list: List<TripItemTypes>) {
        val mapPassenger = mutableMapOf<String, Passenger>()
        val segmentAirlines = mutableListOf<Segment>()
        list.forEach {
            it.TripItems.forEach {
                it.TripFlights.forEach { tripData ->
                    tripData.Passengers.forEach { passenger ->
                        mapPassenger[passenger.Id] = passenger
                    }
                    segmentAirlines.addAll(tripData.Segments)
                }
                it.TripHotels.forEach {

                }
            }
        }
        passengers.clear()
//        passengers.addAll(mapPassenger.map { it.value })
    }

    //Todo submit
    fun submit(path: String) {
        if (getDetailSubmit() == null) {
            return
        }
        _loading.value = true
        viewModelScope.launch {
            val result = repository.submitSettlement(getDetailSubmit()!!, path)
            compareSubmitResult(result)
        }
    }

    private fun compareSubmitResult(result: Result<SubmitResult>) {
        if (result is Result.Success) {
            _successSubmit.value = Event(result.data.isSuccess)
        } else {
            _error.value = Event((result as Result.Error).exception)
        }
        _loading.value = false
    }

    fun getDetailSubmit(): DetailSettlement? {
        return submitSettlement.value
    }

}