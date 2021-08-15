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
    private val _hasUpdate = MutableLiveData<Boolean>()
    val hasUpdate : LiveData<Boolean> = _hasUpdate

    val expenseTypes = mutableListOf<ExpenseType>()
    val isRemoveVisible = ObservableBoolean(false)
    val indexEmpty = ObservableInt(-1)
    var items = ObservableArrayList<OtherExpense>()

    private val _error = MutableLiveData<Event<Throwable>>()
    val error: LiveData<Event<Throwable>> = _error

    private val _loading = MutableLiveData<Event<Boolean>>()
    val loading: LiveData<Event<Boolean>> = _loading
    var isLoading = false

    private val _expenseType = MutableLiveData<List<ExpenseType>>()
    val expenseType: LiveData<List<ExpenseType>> = _expenseType

    fun getExpenseType(isPcu : Boolean) {
        isLoading = true
        _loading.value = Event(true)
        viewModelScope.launch {
            val result = repository.getExpenseType()
            compareExpenseType(result,isPcu)
        }
    }

    private fun compareExpenseType(result: Result<List<ExpenseType>>,isPcu: Boolean) {
        val typeFor = if (isPcu) 2 else 1
        if (result is Result.Success) {
            val list = result.data.filter {
                !it.Description.isNullOrEmpty() &&
                      !it.ExpenseType.isNullOrEmpty() &&
                            it.TypeFor == typeFor
            }
            expenseTypes.addAll(list)
        } else {
            val t = result as Result.Error
            _error.value = Event(t.exception)
        }
        _loading.value = Event(false)
        isLoading = false
    }

    fun setExpenseType(expenseType: ExpenseType, position: Int, currency: String) {
        val item = getItem(position)
        if (item != null) {
            val tempData = OtherExpense(
                expenseType.Description,
                expenseType.ExpenseType,
                0,
                "",
                currency
            )
            items[position] = tempData
            _hasUpdate.value = true
        }
    }

    fun setCurrency(currency: String, pos: Int) {
        val item = getItem(pos)
        if (item != null) {
            val tempData = item.copy(
                Amount   = 0,
                Currency = currency)
            items[pos] = tempData
            _hasUpdate.value = true
        }
    }

    fun setAmount(amount: Number, pos: Int) {
        val item = getItem(pos)
        if (item != null) {
            val tempData = item.copy(
                Amount = amount
            )
            items[pos] = tempData
            _hasUpdate.value = true
        }
    }

    fun addDescription(notes: String, pos: Int) {
        val item = getItem(pos)
        if (item != null) {
            val tempData = item.copy(
                Description = notes
            )
            items[pos] = tempData
            _hasUpdate.value = true
        }
    }

    fun addItem(currency: String) {
        val item = OtherExpense()
        item.Currency = currency
        items.add(item)
        isRemoveVisible.set(true)
        _hasUpdate.value = true
    }

    fun removeItem(pos: Int) {
        items.removeAt(pos)
        isRemoveVisible.set(items.size > 1)
        _hasUpdate.value = true
    }

    fun indexFirstEmpty(): Int {
        val i =
            items.indexOfFirst { it.ExpenseType.isEmpty() || it.Amount == 0 || it.Description.isEmpty() }
        indexEmpty.set(i)
        return i
    }

    fun getItem(pos: Int): OtherExpense? {
        return try {
            items[pos]
        } catch (e: Exception) {
            null
        }
    }
}