package com.mobile.travelaja.module.home.repository

import opsigo.com.datalayer.model.result.Result
import opsigo.com.domainlayer.model.trip.TripResult

interface HomeRepository {
    suspend fun getTrip(query : MutableMap<String,String>) : Result<TripResult>
}