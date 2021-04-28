package com.opsigo.travelaja.module.create_trip.newtrip_pertamina.viewmodel

import kotlinx.coroutines.coroutineScope
import opsigo.com.datalayer.model.result.City
import opsigo.com.datalayer.model.result.Result
import opsigo.com.datalayer.network.ServiceApi

class CityDefaultRepository(private val api : ServiceApi) : CityRepository {
    override suspend fun getCities(): Result<List<City>> = coroutineScope {
            try {
                val result = api.getCities()
                Result.Success(result)
            } catch (ex: Throwable) {
                Result.Error(ex)
            }
        }
}