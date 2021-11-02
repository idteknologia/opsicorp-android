package com.mobile.travelaja.module.settlement.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mobile.travelaja.base.paging.PageKeyedPagingSource
import kotlinx.coroutines.flow.Flow
import opsigo.com.datalayer.network.ServiceApi
import opsigo.com.domainlayer.model.trip.Trip

class TripListRepository(private val api : ServiceApi) : TripRepository {
    override fun getTrips(query : MutableMap<String,Any>) : Flow<PagingData<Trip>> =
        Pager(PagingConfig(30)){
            TripPagingSource(api, query)
        }.flow

    override fun pagingSettlement(query: MutableMap<String, Any>): Pager<Int, Trip> =
        Pager(PagingConfig(PageKeyedPagingSource.SIZE,enablePlaceholders = false)){
            TripPagingSource(api,query)
        }
}