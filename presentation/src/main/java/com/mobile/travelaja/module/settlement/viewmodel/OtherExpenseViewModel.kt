package com.mobile.travelaja.module.settlement.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.travelaja.module.settlement.repository.SettlementRepository
import kotlinx.coroutines.launch
import opsigo.com.datalayer.model.result.Result
import opsigo.com.domainlayer.model.Event
import opsigo.com.domainlayer.model.settlement.ExpenseType
import opsigo.com.domainlayer.model.settlement.OtherExpense

class OtherExpenseViewModel(private val repository: SettlementRepository) : ViewModel() {
    val expenseTypes = mutableListOf<ExpenseType>()
    val otherExpenses = mutableListOf<OtherExpense>()

    private val _error = MutableLiveData<Event<Throwable>>()
    val error: LiveData<Event<Throwable>> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getExpenseType(){
        _loading.value = true
        viewModelScope.launch {
            val result = repository.getExpenseType()
            compareExpenseType(result)
        }
    }

    private fun compareExpenseType(result : Result<List<ExpenseType>>){
        if (result is Result.Success){
            expenseTypes.addAll(result.data)
        }else {
            val t = result as Result.Error
            _error.value = Event(t.exception)
        }
        _loading.value = false
    }
}