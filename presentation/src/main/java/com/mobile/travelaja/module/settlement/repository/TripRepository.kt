package com.mobile.travelaja.module.settlement.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import opsigo.com.domainlayer.model.trip.Trip

interface TripRepository  {
    fun getTrips(query : MutableMap<String,Any>) : Flow<PagingData<Trip>>
    fun pagingSettlement(query: MutableMap<String, Any>) : Pager<Int,Trip>
}