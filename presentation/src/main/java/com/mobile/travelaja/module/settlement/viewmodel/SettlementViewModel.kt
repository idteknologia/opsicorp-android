package com.mobile.travelaja.module.settlement.viewmodel

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
    val isEnabledDetailExpense = ObservableBoolean(false)
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

    var submitSettlement = MutableLiveData(DetailSettlement())
    var tickets = mutableListOf<Ticket>()
    var routes = mutableListOf<RouteTransport>()

    var idTripSelected = ""
    private var bankAccountSelected = ""
    private var bankTransferSelected = ""

    private val _dayValueEvent = MutableLiveData<Event<Int>>()
    val dayValueEvent: LiveData<Event<Int>> = _dayValueEvent
    val loadingPostDay = MutableLiveData(false)
    private var job: Job? = null

    var modeTransports = mutableListOf<ModeTransport>()
    var typeExpense = arrayOf<ExpenseType>()
    var modeFlight = ""
    val isRemoveVisible = ObservableBoolean(false)
    var jobCalculateTransport: Job? = null

    var totalTransport = ObservableField<Number>(0)

    var saveAction : (() -> Unit)? = null


    fun onClickSave() {
        saveAction?.invoke()
    }

    fun selectedBank(account : String , transfer : String ){
        bankAccountSelected = account
        bankTransferSelected = transfer
        submitSettlement.value!!.BankAccount = account
        submitSettlement.value!!.BankTransfer = transfer
    }

    /*
      cek your id trip is not same from default or not empty
     */
    fun getDetailTrip() {
        if (idTripSelected == submitSettlement.value!!.TripId) {
            return
        }
        isEnabledDetailInformation.set(false)
        viewModelScope.launch {
            val result = repository.getDetailTrip(idTripSelected)
            compareDetailTrip(result)
        }
    }

    private fun compareDetailTrip(result: Result<DetailSettlementResult>) {
        if (result is Result.Success) {
            submitSettlement.value = result.data.trip
            submitSettlement.value!!.BankAccount = bankAccountSelected
            submitSettlement.value!!.BankTransfer = bankTransferSelected
            tickets.clear()
            tickets.addAll(result.data.listTicket)
            routes.clear()
            routes.addAll(result.data.trip!!.routes())
        } else {
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

    fun checkedExpense(checked: Boolean){
        isEnabledDetailExpense.set(checked)
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
            submitSettlement.value!!.SpecificAreaTariff = 0
            submitSettlement.value!!.SpecificAreaDays = 0
            submitSettlement.value!!.TotalSpecificAreaExpense = 0
            _dayValueEvent.value = Event(0)
            loadingPostDay.value = false
            job?.cancel()
        }
    }

    fun calculateOvernight(countDay: Int) {
        val price = submitSettlement.value!!.SpecificAreaTariff
        submitSettlement.value!!.SpecificAreaDays = countDay
        val totalPrice = price.toInt() * countDay
        submitSettlement.value!!.TotalSpecificAreaExpense = totalPrice
    }

    private fun updateRateDay() {
        val typeWork = submitSettlement.value!!.Golper
        loadingPostDay.value = true
        job?.cancel()
        job = viewModelScope.launch {
            val result = repository.updateRateOvernight(typeWork, 1)
            compareRateDay(result)
        }
    }

    private fun compareRateDay(result: Result<RateStayResult>) {
        if (result is Result.Success) {
            submitSettlement.value!!.SpecificAreaTariff = result.data.result
            submitSettlement.value!!.TotalSpecificAreaExpense = result.data.result
            submitSettlement.value!!.SpecificAreaDays = 1
        } else {
            val e = result as Result.Error
            checkedOverNight(false)
            _error.value = Event(e.exception)
        }
        loadingPostDay.value = false
    }

    fun submit(){
        viewModelScope.launch {
//            val result = repository.submitSettlement(submitSettlement)
//            compareSubmitResult(result)
        }
    }

    private fun compareSubmitResult(result : Result<SubmitResult>){
        if (result is Result.Success){

        }else {
            _error.value = Event((result as Result.Error).exception)
        }
    }

    var otherExpenses : ArrayList<OtherExpense> = arrayListOf()
    fun addingOtherExpense(list : List<OtherExpense>){
        otherExpenses.clear()
        otherExpenses.addAll(list)
    }


    companion object {
        const val TYPE = "Type"
        const val MODA = "Moda"
        const val CITY = "City"
        const val TYPE_FLIGHT = 1
        const val NON_FLIGHT = 2
    }

}