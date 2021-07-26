package com.mobile.travelaja.module.settlement.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.mobile.travelaja.module.settlement.repository.SettlementRepository
import opsigo.com.domainlayer.model.settlement.IntercityTransport
import opsigo.com.domainlayer.model.settlement.RouteTransport

class IntercityTransportViewModel(val repository : SettlementRepository) : ViewModel() {
    val isRemoveVisible = ObservableBoolean(false)
    val items = mutableListOf<IntercityTransport>()

    fun setRoute(pos : Int, route : RouteTransport){
        val data = items[pos]
        data.Route = route.Route
        data.City = route.City
    }

    fun setDistance(distance : Number,pos: Int){
        val data = items[pos]
        data.Distance = distance
    }

    fun addItem() {
        items.add(IntercityTransport())
        isRemoveVisible.set(true)
    }

    fun removeItem(pos: Int) {
        items.removeAt(pos)
        isRemoveVisible.set(items.size > 1)
    }
}