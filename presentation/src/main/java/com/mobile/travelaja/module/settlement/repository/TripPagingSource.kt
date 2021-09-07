package com.mobile.travelaja.module.settlement.repository

import com.mobile.travelaja.base.paging.PageKeyedPagingSource
import opsigo.com.domainlayer.model.ResultList
import opsigo.com.datalayer.network.ServiceApi
import opsigo.com.domainlayer.model.trip.Trip

class TripPagingSource(private val api : ServiceApi, private val query : MutableMap<String,Any>) : PageKeyedPagingSource<Trip>() {
    override suspend fun getResultFromService(page: Int): ResultList<Trip> {
        query[INDEX_KEY] = page
        query[SIZE_KEY] = SIZE
        query["Direction"] = 1
        query["OrderBy"] = "Code"
        return api.getTripList(query)
    }

}