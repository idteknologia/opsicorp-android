package com.mobile.travelaja.module.settlement.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import opsigo.com.datalayer.model.result.Result
import opsigo.com.domainlayer.model.settlement.*
import opsigo.com.domainlayer.model.trip.DetailTripResult
import opsigo.com.domainlayer.model.trip.Trip
import retrofit2.http.Body

interface SettlementRepository {
    fun getSettlements(query : MutableMap<String,Any>) : Flow<PagingData<Settlement>>
    suspend fun getBanks() : Result<List<Bank>>
    suspend fun getTripCodes() : Result<List<Trip>>
    suspend fun getDetailTrip(idTrip : String) : Result<DetailTripResult>
    suspend fun updateRateOvernight(typeWork : Int , countDay : Int) : Result<RateStayResult>
    suspend fun getModeTransport() : Result<List<ModeTransport>>
    suspend fun calculateTransportExpense(body:MutableMap<String,Any>) : Result<CalculateTransportResult>
    suspend fun getExpenseType() : Result<List<ExpenseType>>
}