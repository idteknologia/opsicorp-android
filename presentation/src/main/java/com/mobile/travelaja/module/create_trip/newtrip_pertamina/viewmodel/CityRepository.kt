package com.mobile.travelaja.module.create_trip.newtrip_pertamina.viewmodel

import opsigo.com.datalayer.model.result.City
import opsigo.com.datalayer.model.result.Result

interface CityRepository {
    suspend fun getCities() : Result<List<City>>
}