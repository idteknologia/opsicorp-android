package com.mobile.travelaja.module.settlement.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.travelaja.module.settlement.repository.SettlementRepository
import kotlinx.coroutines.launch
import opsigo.com.datalayer.model.result.Result
import opsigo.com.domainlayer.model.Event
import opsigo.com.domainlayer.model.settlement.DetailDraftSettlement
import opsigo.com.domainlayer.model.settlement.DetailSettlement
import opsigo.com.domainlayer.model.settlement.SubmitResult

class DraftSettlementViewModel(private val repository: SettlementRepository) : ViewModel() {
    var submitSettlement = MutableLiveData(DetailSettlement())
    private val _error = MutableLiveData<Event<Throwable>>()
    val error: LiveData<Event<Throwable>> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _successSubmit = MutableLiveData<Event<Boolean>>()
    val successSubmit : LiveData<Event<Boolean>> = _successSubmit

    var tripCode : String = ""
    fun getDetailTrip(_tripCode : String) {
        if (this.tripCode.isNotEmpty()){
            return
        }
        this.tripCode = _tripCode
        _loading.value = true
        viewModelScope.launch {
            val result = repository.getDetailDraft(tripCode)
            compareDetailTrip(result)
        }
    }

    private fun compareDetailTrip(result: Result<DetailDraftSettlement>) {
        if (result is Result.Success) {
            submitSettlement.value = result.data.model
        } else {
            val e = result as Result.Error
            _error.value = Event(e.exception)
        }
        _loading.value = false
    }

    //Todo submit
    fun submit(path : String) {
        if (getDetailSubmit() == null){
            return
        }
        viewModelScope.launch {
            val result = repository.submitSettlement(getDetailSubmit()!!,path)
            compareSubmitResult(result)
        }
    }

    private fun compareSubmitResult(result: Result<SubmitResult>) {
        if (result is Result.Success) {
            _successSubmit.value = Event(result.data.isSuccess)
        } else {
            _error.value = Event((result as Result.Error).exception)
        }
    }

    fun getDetailSubmit() : DetailSettlement?{
        return submitSettlement.value
    }

}