package com.mobile.travelaja.module.settlement.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.mobile.travelaja.module.settlement.repository.TripRepository
import kotlinx.coroutines.flow.Flow
import opsigo.com.domainlayer.model.Event
import opsigo.com.domainlayer.model.trip.Trip

class TripViewModel(private val repository : TripRepository) : ViewModel() {
    private var currentResult: Flow<PagingData<Trip>>? = null

    private val _searchQuery = MutableLiveData<Event<String>>()
    val searchQuery : LiveData<Event<String>> = _searchQuery


    fun getTrips(query : MutableMap<String,Any>) : Flow<PagingData<Trip>> {
        val lastResult = currentResult
        if (lastResult != null){
            return lastResult
        }
        val newResult : Flow<PagingData<Trip>> = repository.getTrips(query)
        currentResult = newResult
        return newResult
    }

    fun onSearch(query : String){
        _searchQuery.value = Event(query)
    }

}