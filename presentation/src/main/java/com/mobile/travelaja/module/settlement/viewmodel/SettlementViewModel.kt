package com.mobile.travelaja.module.settlement.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.google.gson.annotations.SerializedName
import com.mobile.travelaja.module.settlement.CloneDetail
import com.mobile.travelaja.module.settlement.repository.SettlementRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import opsigo.com.datalayer.model.create_trip_plane.trip_plan.UploadFileEntity
import opsigo.com.datalayer.model.result.Result
import opsigo.com.domainlayer.model.Event
import opsigo.com.domainlayer.model.settlement.*
import opsigo.com.domainlayer.model.trip.Route
import opsigo.com.domainlayer.model.trip.Trip

class SettlementViewModel(private val repository: SettlementRepository) : ViewModel() {
    val buttonNextEnabled = ObservableBoolean(false)
    val selectedCode = ObservableField<String>()

    val isEnableRefundTicket = ObservableBoolean(false)
    val isEnabledDetailTransport = ObservableBoolean(false)
    val isEnabledOtherExpense = ObservableBoolean(false)
    val isEnabledDetailIntercity = ObservableBoolean(false)
    val isTravellingEnabled = ObservableBoolean(false)
    val isDraftLabelVisible = ObservableBoolean(false)

    val isExpandDetail = ObservableBoolean(false)

    private var currentResult: Flow<PagingData<Settlement>>? = null

    private val _banks = MutableLiveData<List<Bank>>()
    val bank: LiveData<List<Bank>> = _banks

    private val _tripCodes = MutableLiveData<List<Trip>>()
    val tripCodes: LiveData<List<Trip>> = _tripCodes

    private val _dayRate = MutableLiveData<RateStayResult>()
    val dayRate: LiveData<RateStayResult> = _dayRate

    private val _error = MutableLiveData<Event<Throwable>>()
    val error: LiveData<Event<Throwable>> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _successSubmit = MutableLiveData<Event<SubmitResult>>()
    val successSubmit: LiveData<Event<SubmitResult>> = _successSubmit

    var submitSettlement = MutableLiveData(DetailSettlement())
    var tickets = listOf<Ticket>()
    var routes = listOf<RouteTransport>()

    private var bankAccountSelected = ""
    private var bankTransferSelected = ""

    private val _dayValueEvent = MutableLiveData<Event<Int>>()
    val dayValueEvent: LiveData<Event<Int>> = _dayValueEvent
    val loadingPostDay = MutableLiveData(false)
    private var job: Job? = null

    var modeTransports = mutableListOf<ModeTransport>()
    private val _successFetchModeTransport = MutableLiveData<Event<Boolean>>()
    val successFetchModeTransport: LiveData<Event<Boolean>> = _successFetchModeTransport

    var typeExpense = arrayOf<ExpenseType>()
    var jobCalculateTransport: Job? = null
    var tempTripId = ""

    fun selectedBank(account: String, transfer: String) {
        bankAccountSelected = account
        bankTransferSelected = transfer
        val detail = getDetailSubmit()
        detail?.BankAccount = account
        detail?.BankTransfer = transfer
    }

    /*
      cek your id trip is not same from default or not empty
     */
    fun getDetailTrip() {
        _loading.value = true
        viewModelScope.launch {
            val result = repository.getDetailTrip(tempTripId)
            compareDetailTrip(result)
        }
    }

    private fun compareDetailTrip(result: Result<DetailSettlementResult>) {
        if (result is Result.Success) {
            val data = result.data
            tempTripId = ""
            completingDetail(data.trip, false)
            tickets = data.listTicket
        } else {
            val e = result as Result.Error
            _error.value = Event(e.exception)
        }
        _loading.value = false
    }

    private fun completingDetail(detail: DetailSettlement?, isDraft: Boolean) {
        detail?.let {
            if (!isDraft) {
                isEnabledDetailTransport.set(false)
                isEnabledDetailIntercity.set(false)
                isEnabledOtherExpense.set(false)
            }
            val totTransportExpense =
                detail.TransportExpenses.filter { it.Currency.isNullOrEmpty() || it.Currency == IDR }
                    .sumByDouble { it.TotalAmount.toDouble() }
            val totOtherTransportExpense =
                detail.OtherTransportExpenses.filter { it.Currency.isNullOrEmpty() || it.Currency == IDR }
                    .sumByDouble { it.TotalAmount.toDouble() }
            val totOtherIdr =
                detail.OtherExpense.filter { it.Currency.isNullOrEmpty() || it.Currency == IDR }
                    .sumByDouble { it.Amount.toDouble() }
            val totOtherUsd = detail.OtherExpense.filter { it.Currency == USD }
                .sumByDouble { it.Amount.toDouble() }
            detail.TotalTransportExpense = totTransportExpense
            detail.TotalOtherTransportExpense = totOtherTransportExpense
            detail.TotalOtherExpense = totOtherIdr
            detail.TotalOtherExpenseUsd = totOtherUsd
            var laundry = 0.0
            if (detail.AmountLaundry != null) {
                laundry = detail.AmountLaundry.toDouble()
            }
            detail.TotalExpenseSubmit =
                totOtherIdr + totTransportExpense + totOtherTransportExpense + laundry + detail.getTotalSpecificArea()
                    .toDouble()
            detail.TotalExpenseSubmitUsd = totOtherUsd
            routes = detail.routes()
            if (bankAccountSelected.isNotEmpty() || detail.BankAccount.isNullOrEmpty()) {
                detail.BankAccount = bankAccountSelected
            }
            if (bankTransferSelected.isNotEmpty() || detail.BankTransfer.isNullOrEmpty()) {
                detail.BankTransfer = bankTransferSelected
            }
            submitSettlement.value = detail
        }
    }

    //Todo observe list trip code
    fun getTripCodes(tripType: Int) {
        _loading.value = true
        viewModelScope.launch {
            val result = repository.getTripCodes(tripType)
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

    fun getModeTransport() {
        _loading.value = true
        viewModelScope.launch {
            val result = repository.getModeTransport()
            compareModeTransport(result)
        }
    }

    private fun compareModeTransport(result: Result<List<ModeTransport>>) {
        if (result is Result.Success) {
            val modes = result.data
            modeTransports.clear()
            modeTransports.addAll(modes)
            _successFetchModeTransport.value = Event(modes.isNotEmpty())
        } else {
            val t = result as Result.Error
            _error.value = Event(t.exception)
        }
        _loading.value = false
    }

    fun checkedInformation(checked: Boolean) {
        isEnabledDetailTransport.set(checked)
    }

    fun checkedExpense(checked: Boolean) {
        isEnabledOtherExpense.set(checked)
    }

    fun checkedTravelling(checked: Boolean) {
        isTravellingEnabled.set(checked)
        submitSettlement.value?.UseSpecificArea = checked
        val isVisibleOverNight = submitSettlement.value?.IsStaySpecArea ?: false
        if (!checked && isVisibleOverNight) {
            checkedOverNight(false)
        }
    }

    fun checkedOverNight(checked: Boolean) {
        submitSettlement.value?.IsStaySpecArea = checked
        if (checked) {
            updateRateDay()
        } else {
            submitSettlement.value?.SpecificAreaTariff = 0
            submitSettlement.value?.SpecificAreaDays = 0
            submitSettlement.value?.TotalSpecificAreaExpense = 0
            _dayValueEvent.value = Event(0)
            loadingPostDay.value = false
            job?.cancel()
        }
    }

    fun calculateOvernight(countDay: Int) {
        val price = submitSettlement.value!!.SpecificAreaTariff
        submitSettlement.value?.SpecificAreaDays = countDay
        val totalPrice = price.toDouble() * countDay
        submitSettlement.value?.TotalSpecificAreaExpense = totalPrice
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
            submitSettlement.value?.SpecificAreaTariff = result.data.result
            submitSettlement.value?.TotalSpecificAreaExpense = result.data.result
            submitSettlement.value?.SpecificAreaDays = 1
        } else {
            val e = result as Result.Error
            checkedOverNight(false)
            _error.value = Event(e.exception)
        }
        loadingPostDay.value = false
    }

    fun checkedTicketRefunds(checked: Boolean) {
        isEnableRefundTicket.set(checked)
    }

    //Todo submit
    fun submit(path: String, errorDesc: String) {
        if (getDetailSubmit() == null) {
            return
        }
        val detail = CloneDetail.cloneDetail(getDetailSubmit()!!)
        if (!isEnableRefundTicket.get()){
            detail.TicketRefunds = emptyList()
        }
        _loading.value = true
        viewModelScope.launch {
            val result = repository.submitSettlement(detail, path)
            compareSubmitResult(result, errorDesc)
        }
    }


    private fun compareSubmitResult(result: Result<SubmitResult>, errorDesc: String) {
        if (result is Result.Success) {
            val submit = result.data
            _successSubmit.value = Event(submit)
            if (!submit.isSuccess && submit.idDraft.isNullOrEmpty()) {
                val error = submit.errorMessage
                var strError = errorDesc
                if (error is String)
                    strError = error
                if (error is List<*>) {
                    error.forEach {
                        if (it is String) {
                            strError += it
                        }
                    }
                }
                _error.value = Event(Throwable(strError))
            }
        } else {
            _error.value = Event((result as Result.Error).exception)
        }
        _loading.value = false
    }

    val isLoadingFile = ObservableInt(-1)
    val isErrorFile = ObservableInt(-1)
    val attachments = ObservableArrayList<Attachment>()

    fun deleteFile(pos: Int) {
        submitSettlement.value?.Attachments?.removeAt(pos)
        val attachments = submitSettlement.value?.Attachments
        if (attachments != null) {
            submitSettlement.value?.Attachments = attachments
        }
    }

    fun uploadFile(position: Int, data: Attachment) {
        if (isLoadingFile.get() != -1) {
            return
        }
        val attachments = submitSettlement.value?.Attachments
        if (attachments != null) {
            attachments += data
            submitSettlement.value?.Attachments = attachments
        }
        isLoadingFile.set(position + 1)
        viewModelScope.launch {
            val result = repository.uploadFile(data.Url, data.type)
            compareFile(position, result)
        }
    }

    private fun compareFile(position: Int, result: Result<UploadFileEntity>) {
        if (result is Result.Success) {
            val isSuccess = result.data.errMsg.isNullOrEmpty()
            isErrorFile.set(if (isSuccess) -1 else position + 1)
            val detail = getDetailSubmit()
            val attachments = detail?.Attachments?.get(position)
            if (isSuccess || attachments != null) {
                attachments!!.Id = result.data.url
                attachments.Url = result.data.url
                attachments.TripPlanId = detail.Id
                submitSettlement.value?.Attachments?.set(position, attachments)
            }
        } else {
            val e = result as Result.Error
            _error.value = Event(e.exception)
            isErrorFile.set(position + 1)
            isLoadingFile.set(position + 1)
        }
        isLoadingFile.set(-1)
    }

//    fun addListRefunds() {
//        getDetailSubmit()?.TicketRefunds?.addAll(tickets)
//    }

    fun addComment(char: CharSequence) {
        submitSettlement.value?.Comment = char.toString()
    }

    fun addingOtherExpense(list: List<OtherExpense>) {
        val detail = getDetailSubmit()
        val totalIdr = list.filter { it.Currency.isNullOrEmpty() || it.Currency == IDR }
            .sumByDouble { it.Amount.toDouble() }
        val totalUsd = list.filter { it.Currency == USD }.sumByDouble { it.Amount.toDouble() }
        detail?.OtherExpense = list.toMutableList()
        detail?.TotalOtherExpense = totalIdr
        detail?.TotalOtherExpenseUsd = totalUsd
        val enabled = detail?.OtherExpense != null && detail.OtherExpense.size > 0
        isEnabledOtherExpense.set(enabled)
    }

    fun addingIntercityTransport(list: List<IntercityTransport>) {
        val detail = getDetailSubmit()
        val totalIdr = list.filter { it.Currency.isNullOrEmpty() || it.Currency == IDR }
            .sumByDouble { it.TotalAmount.toDouble() }
        detail?.OtherTransportExpenses = list.toMutableList()
        detail?.TotalOtherTransportExpense = totalIdr
        val enabled =
            detail?.OtherTransportExpenses != null && detail.OtherTransportExpenses.size > 0
        isEnabledDetailIntercity.set(enabled)
    }

    fun addingTransportExpense(list: List<TransportExpenses>, modes: List<ModeTransport>) {
        val detail = getDetailSubmit()
        val totalIdr = list.filter { it.Currency.isNullOrEmpty() || it.Currency == IDR }
            .sumByDouble { it.TotalAmount.toDouble() }
        detail?.TransportExpenses?.clear()
        list.forEach {
            val data = TransportExpenses()
            data.City = it.City
            data.Amount = it.Amount
            data.Currency = it.Currency
            data.TotalAmount = it.TotalAmount
            data.TransportationMode = it.TransportationMode
            data.TransportationType = it.TransportationType
            data.TransportationModeName = it.TransportationModeName
            data.TripType = it.TripType
            detail?.TransportExpenses?.add(data)
        }
        detail?.TotalTransportExpense = totalIdr
        if (modeTransports.isEmpty()) {
            modeTransports.addAll(modes)
        }
        val enabled = detail?.TransportExpenses != null && detail.TransportExpenses.size > 0
        isEnabledDetailTransport.set(enabled)
    }

    fun clearTransportExpense() {
        val detail = getDetailSubmit()
        detail?.TransportExpenses?.clear()
        detail?.TotalTransportExpense = 0
        isEnabledOtherExpense.set(false)
    }

    fun clearTransportIntercity() {
        val detail = getDetailSubmit()
        detail?.OtherTransportExpenses?.clear()
        detail?.TotalOtherTransportExpense = 0
        isEnabledDetailIntercity.set(false)
    }

    fun clearOtherExpenses() {
        val detail = getDetailSubmit()
        detail?.OtherExpense?.clear()
        detail?.TotalOtherExpense = 0
        isEnabledOtherExpense.set(false)
    }

    fun changeEditDraft(detail: DetailSettlement) {
        completingDetail(detail, true)
        isEnableRefundTicket.set(detail.TicketRefunds.isNotEmpty())
        isEnabledDetailTransport.set(detail.TransportExpenses.isNotEmpty())
        isEnabledOtherExpense.set(detail.OtherExpense.isNotEmpty())
        isEnabledDetailIntercity.set(detail.OtherTransportExpenses.isNotEmpty())
    }

    fun getDetailSubmit(): DetailSettlement? {
        return submitSettlement.value
    }

    companion object {
        const val TYPE = "Type"
        const val MODA = "Moda"
        const val CITY = "City"
        const val TYPE_FLIGHT = 1
        const val NON_FLIGHT = 2
        const val IDR = "IDR"
        const val USD = "USD"
    }

}