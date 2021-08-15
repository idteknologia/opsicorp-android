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
import opsigo.com.domainlayer.model.settlement.IntercityTransport
import opsigo.com.domainlayer.model.settlement.RouteTransport
import java.util.*

class IntercityTransportViewModel(val repository: SettlementRepository) : ViewModel() {
    private val _hasUpdate = MutableLiveData<Boolean>()
    val hasUpdate : LiveData<Boolean> = _hasUpdate

    val isRemoveVisible = ObservableBoolean(false)
    val items = ObservableArrayList<IntercityTransport>()
    val indexEmpty = ObservableInt(-1)

    private val _total = MutableLiveData<Number>(0)
    val total: LiveData<Number> = _total

    var loading = false

    private val _error = MutableLiveData<Event<Throwable>>()
    val error: LiveData<Event<Throwable>> = _error

    fun addItems(items: Array<IntercityTransport>) {
        this.items.clear()
        this.items.addAll(items)
        isRemoveVisible.set(items.size > 1)
        _hasUpdate.value = true
    }

    fun setRoute(pos: Int, route: RouteTransport, golper: Int) {
        getIntercityTransport(pos, route, golper)
    }

    fun setDistance(distance: Number, pos: Int) {
        val data = getItem(pos)
        if (data != null) {
            val tempTotal = data.TotalAmount
            if (data.Distance == distance)
                return
            val type = if (data.TripType == 1) 2 else 1
            val cost = (data.Cost.toDouble() * distance.toDouble()) * type
            val item = data.copy(Distance = distance, TotalAmount = cost)
            updateItem(pos, item)
            updateTotal(cost, tempTotal, true)
        }
    }

    private fun updateItem(position: Int, data: IntercityTransport) {
        items[position] = data
        _hasUpdate.value = true
    }

    fun addItem(currency: String) {
        items.add(IntercityTransport(Currency = currency))
        isRemoveVisible.set(true)
    }

    fun removeItem(pos: Int) {
        val totalAmount = items[pos].TotalAmount
        items.removeAt(pos)
        updateTotal(totalAmount, 0, false)
        isRemoveVisible.set(items.size > 1)
        _hasUpdate.value = true
    }

    fun switchTransport(pos: Int, checked: Boolean) {
        val data = getItem(pos)
        if (data != null) {
            val tempTotal = data.TotalAmount.toDouble()
            val total = if (checked) tempTotal * 2 else tempTotal / 2
            val item = data.copy(TotalAmount = total, TripType = if (checked) 1 else 0)
            updateItem(pos, item)
            updateTotal(total, tempTotal, checked)
        }
    }

    //Todo get request
    fun getIntercityTransport(pos: Int, route: RouteTransport, golper: Int) {
        loading = true
        viewModelScope.launch {
            val result = repository.getIntercityTransportCompensation(route, golper)
            compareIntercityTransport(result, pos, route)
        }
    }

    fun compareIntercityTransport(
        result: Result<IntercityTransport>,
        pos: Int,
        route: RouteTransport
    ) {
        if (result is Result.Success) {
            val data = getItem(pos)
            if (data != null) {
                data.Route = route.Route
                data.City = route.City
                val item = data.copy(
                    Route = route.Route,
                    City = route.City,
                    IsFromPolicy = result.data.IsFromPolicy,
                    Cost = result.data.Cost,
                    TotalAmount = result.data.TotalAmount
                )
                updateItem(pos, item)
                updateTotal(result.data.TotalAmount, data.TotalAmount, true)
            }
        } else {
            val t = result as Result.Error
            _error.value = Event(t.exception)
        }
        loading = false
    }

    fun indexFirstEmpty(): Int {
        val i = items.indexOfFirst { it.Distance == null || it.City.isEmpty() }
        indexEmpty.set(i)
        return i
    }

    private fun updateTotal(amount: Number, tempAmount: Number, isAdding: Boolean) {
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

    private fun getItem(pos: Int): IntercityTransport? {
        return try {
            items[pos]
        } catch (e: Exception) {
            null
        }
    }
}