package com.mobile.travelaja.module.settlement.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mobile.travelaja.utility.Utils
import kotlinx.coroutines.flow.Flow
import opsigo.com.datalayer.model.result.Result
import opsigo.com.datalayer.network.ServiceApi
import opsigo.com.domainlayer.model.settlement.*
import opsigo.com.domainlayer.model.trip.DetailTripResult
import opsigo.com.domainlayer.model.trip.Trip

class DefaultSettlementRepository(private val api: ServiceApi) : SettlementRepository {
    override fun getSettlements(query: MutableMap<String, Any>): Flow<PagingData<Settlement>> =
        Pager(PagingConfig(30)) {
            SettlementPagingSource(api, query)
        }.flow

    override suspend fun getBanks(): Result<List<Bank>> =
        try {
            val list = api.getBanks()
            Result.Success(list)
        } catch (t: Throwable) {
            Result.Error(t)
        }

    override suspend fun getTripCodes(): Result<List<Trip>> =
        try {
            val list = api.getTripCodes()
            Result.Success(list)
        } catch (t: Throwable) {
            Result.Error(t)
        }

    override suspend fun getDetailTrip(idTrip: String): Result<DetailTripResult> =
        try {
            val result = api.getDetailTrip(idTrip)
//            val typeWorker = result.trip.Golper
//            val rateStay = api.putSpecificAreaCompensation(mutableMapOf("Golper" to typeWorker,"IsStay" to 1))
//            result.rateStay = rateStay.result
            Result.Success(result)
        } catch (t: Throwable) {
            Result.Error(t)
        }

    override suspend fun updateRateOvernight(typeWork: Int, countDay: Int): Result<RateStayResult> =
        try {
            val rateStay = api.putSpecificAreaCompensation(
                mutableMapOf(
                    "Golper" to typeWork,
                    "IsStay" to countDay
                )
            )
            Result.Success(rateStay)
        } catch (t: Throwable) {
            Result.Error(t)
        }

    override suspend fun getModeTransport(): Result<List<ModeTransport>> =
        try {
            val modeTransports = api.getModeTransport()
            if (modeTransports.isNotEmpty()) {
                modeTransports.forEachIndexed { index, modeTransport ->
                    modeTransport.id = index
                }
                Result.Success(modeTransports)
            } else {
                Result.Error(Throwable(Utils.EMPTY))
            }
        } catch (t: Throwable) {
            Result.Error(t)
        }

    override suspend fun calculateTransportExpense(body: MutableMap<String, Any>): Result<CalculateTransportResult> =
        try {
            val result = api.calculateTransportExpense(body)
            Result.Success(result)
        } catch (t: Throwable) {
            Result.Error(t)
        }

    override suspend fun getExpenseType(): Result<List<ExpenseType>> =
        try {
            val result = api.getExpenseType()
            Result.Success(result)
        } catch (t: Throwable) {
            Result.Error(t)
        }

}