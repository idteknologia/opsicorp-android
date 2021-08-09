package com.mobile.travelaja.module.settlement.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
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
//    val items = mutableListOf<OtherExpense>()
    val isRemoveVisible = ObservableBoolean(false)
    val indexEmpty = ObservableInt(-1)
    var items = ObservableArrayList<OtherExpense>()
    var items2 = arrayListOf<OtherExpense>()

    private val _error = MutableLiveData<Event<Throwable>>()
    val error: LiveData<Event<Throwable>> = _error

    private val _loading = MutableLiveData<Event<Boolean>>()
    val loading: LiveData<Event<Boolean>> = _loading

    private val _expenseType = MutableLiveData<List<ExpenseType>>()
    val expenseType: LiveData<List<ExpenseType>> = _expenseType


    fun getExpenseType() {
        _loading.value = Event(true)
        viewModelScope.launch {
            val result = repository.getExpenseType()
            compareExpenseType(result)
        }
    }

    private fun compareExpenseType(result: Result<List<ExpenseType>>) {
        if (result is Result.Success) {
            val list = result.data.filter {
                !it.Description.isNullOrEmpty() || !it.ExpenseType.isNullOrEmpty()
            }
            expenseTypes.addAll(list)
        } else {
            val t = result as Result.Error
            _error.value = Event(t.exception)
        }
        _loading.value = Event(false)
    }

    fun setItem(data: OtherExpense,position: Int){
        val t = OtherExpense()
        t.ExpenseType = data.ExpenseType
        t.expenseName = data.expenseName
        t.Description = data.Description
        t.Amount = data.Amount
        t.Currency = data.Currency
        items[position] = data
    }

    fun setExpenseType(expenseType : ExpenseType,position: Int, currency : String){
        val item = items[position]
        item.ExpenseType = expenseType.ExpenseType
        item.expenseName = expenseType.Description
        item.Amount = 0
        item.Currency = currency
        item.Description = ""
    }

    fun setCurrency(currency: String, pos: Int) {
        val item = items[pos]
        item.Currency = currency
        if (item.Amount.toLong() > 0){
            item.Amount = 0
        }
    }

    fun setAmount(amount: Number, pos: Int) {
        items[pos].Amount = amount
    }

    fun addDescription(notes: String, pos: Int) {
        items[pos].Description = notes
    }

    fun addItem(currency: String) {
        val item = OtherExpense()
        item.Currency = currency
        items.add(item)
        isRemoveVisible.set(true)
    }

    fun removeItem(pos: Int) {
        items.removeAt(pos)
        isRemoveVisible.set(items.size > 1)
    }

    fun indexFirstEmpty() : Int {
        val i =  items.indexOfFirst { it.ExpenseType.isEmpty() || it.Amount == 0 || it.Description.isEmpty() }
        indexEmpty.set(i)
        return  i
    }


}