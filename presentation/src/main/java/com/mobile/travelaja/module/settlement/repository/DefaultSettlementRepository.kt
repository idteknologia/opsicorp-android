package com.mobile.travelaja.module.settlement.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mobile.travelaja.utility.Utils
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import opsigo.com.datalayer.model.create_trip_plane.trip_plan.UploadFileEntity
import opsigo.com.datalayer.model.result.Result
import opsigo.com.datalayer.network.ServiceApi
import opsigo.com.domainlayer.model.settlement.*
import opsigo.com.domainlayer.model.trip.Trip
import java.io.File


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

    override suspend fun getDetailTrip(idTrip: String): Result<DetailSettlementResult> =
        try {
            val result = api.getDetailTrip(idTrip)
//            val typeWorker = result.trip.Golper
//            val rateStay = api.putSpecificAreaCompensation(mutableMapOf("Golper" to typeWorker,"IsStay" to 1))
//            result.rateStay = rateStay.result
            if (result.trip != null) {
                result.trip?.TicketRefunds?.addAll(result.listTicket)
                Result.Success(result)
            } else {
                Result.Error(Throwable(result.errorMessage))
            }
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
            if (result.isNotEmpty()) {
                Result.Success(result)
            } else {
                Result.Error(Throwable(Utils.EMPTY))
            }
        } catch (t: Throwable) {
            Result.Error(t)
        }

    override suspend fun submitSettlement(submit: DetailSettlement,path : String): Result<SubmitResult> =
        try {
            val result = api.submitSettlement(submit,path)
            Result.Success(result)
        } catch (t: Throwable) {
            Result.Error(t)
        }

    override suspend fun getIntercityTransportCompensation(
        route: RouteTransport,
        golper: Int
    ): Result<IntercityTransport> =
        try {
            val origin = route.City.split("-").first()
            val dest = route.City.split("-").last()
            val body = mutableMapOf<String, Any>(
                "Golper" to golper,
                "Origin" to origin,
                "Destination" to dest
            )
            val result = api.getIntercityTransportCompensation(body)
            Result.Success(result.result)
        } catch (t: Throwable) {
            Result.Error(t)
        }

    override suspend fun getDetailDraft(idTrip: String): Result<DetailDraftSettlement> =
        try {
            val result = api.getDetailSettlementDraft(idTrip)
            if (result.model != null) {
                Result.Success(result)
            } else {
                Result.Error(Throwable(Utils.EMPTY))
            }
        } catch (t: Throwable) {
            Result.Error(t)
        }

    override suspend fun uploadFile(uri: String,type : String?): Result<UploadFileEntity> =
        try {
            val typeFile = type ?: "image/jpeg"
            val file = File(uri)
            val fileName = file.name
            val requestFile = file.asRequestBody(typeFile.toMediaType())
            val multipart = MultipartBody.Part.createFormData("file",fileName,requestFile)
            val result = api.uploadFile(multipart)
            Result.Success(result)
        }catch (t : Throwable){
            Result.Error(t)
        }

}