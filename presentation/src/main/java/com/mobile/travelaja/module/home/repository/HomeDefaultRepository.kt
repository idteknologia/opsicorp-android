package com.mobile.travelaja.module.home.repository

import kotlinx.coroutines.coroutineScope
import opsigo.com.datalayer.model.result.Result
import opsigo.com.datalayer.network.ServiceApi
import opsigo.com.domainlayer.model.trip.TripResult

class HomeDefaultRepository(private val api: ServiceApi) : HomeRepository {
    override suspend fun getTrip(query: MutableMap<String, String>): Result<TripResult> = coroutineScope {
        try {
            val result = api.getTripResult(query)
            Result.Success(result)
        } catch (ex: Throwable) {
            Result.Error(ex)
        }
    }
}