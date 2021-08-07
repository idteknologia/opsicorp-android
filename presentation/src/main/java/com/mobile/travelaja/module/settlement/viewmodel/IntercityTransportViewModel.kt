package com.mobile.travelaja.module.settlement.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.travelaja.module.settlement.repository.SettlementRepository
import kotlinx.coroutines.launch
import opsigo.com.datalayer.model.result.Result
import opsigo.com.domainlayer.model.settlement.IntercityTransport
import opsigo.com.domainlayer.model.settlement.RouteTransport

class IntercityTransportViewModel(val repository: SettlementRepository) : ViewModel() {
    val isRemoveVisible = ObservableBoolean(false)
    val items = mutableListOf<IntercityTransport>()

    fun setRoute(pos: Int, route: RouteTransport,golper : Int) {
        val data = items[pos]
        data.Route = route.Route
        data.City = route.City
        getIntercityTransport(pos,route,golper)
    }

    fun setDistance(distance: Number, pos: Int) {
        val data = items[pos]
        if (data.Distance == distance)
            return
        data.Distance = distance
        val type = if (data.TripType == 1) 2 else 1
        val cost = (data.Cost.toDouble() * distance.toDouble()) * type
        data.TotalAmount = cost

    }

    fun addItem() {
        items.add(IntercityTransport())
        isRemoveVisible.set(true)
    }

    fun removeItem(pos: Int) {
        items.removeAt(pos)
        isRemoveVisible.set(items.size > 1)
    }

    fun switchTransport(pos : Int , checked : Boolean){
        val data = items[pos]
        data.TripType = if (checked) 1 else 0
        data.TotalAmount = if (checked) data.TotalAmount.toDouble() * 2 else data.TotalAmount.toDouble() / 2
    }

    fun getIntercityTransport(pos: Int, route: RouteTransport,golper: Int) {
        viewModelScope.launch {
            val result = repository.getIntercityTransportCompensation(route,golper)
            compareIntercityTransport(result,pos)
        }
    }

    fun compareIntercityTransport(result: Result<IntercityTransport>,pos: Int) {
        if (result is Result.Success) {
            val data = items[pos]
            data.IsFromPolicy = result.data.IsFromPolicy
            data.Cost = result.data.Cost
            data.TotalAmount = result.data.TotalAmount
        }
    }


}