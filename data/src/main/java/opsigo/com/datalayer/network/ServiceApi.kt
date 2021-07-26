package opsigo.com.datalayer.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import opsigo.com.datalayer.model.result.City
import opsigo.com.datalayer.model.signin.LoginEntity
import opsigo.com.domainlayer.model.ResultList
import opsigo.com.domainlayer.model.settlement.*
import opsigo.com.domainlayer.model.trip.Trip
import opsigo.com.domainlayer.model.trip.TripResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ServiceApi {

    @GET(MyURL.LIST_APPROVE)
    suspend fun getTripResult(@QueryMap map: MutableMap<String, String>): TripResult

    @GET(MyURL.LIST_APPROVE)
    suspend fun getTripList(@QueryMap map: MutableMap<String, Any>): ResultList<Trip>

    @FormUrlEncoded
    @POST
    suspend fun onLogin(@Url url: String, @FieldMap body: MutableMap<String, Any>): LoginEntity

    @GET("Settlement/ManageTrip/Gets")
    suspend fun getSettlement(@QueryMap map: MutableMap<String, Any>): ResultList<Settlement>

    @GET(MyURL.CITY)
    suspend fun getCities(): List<City>

    @GET("api/Settlement/GetBankTransfer")
    suspend fun getBanks():List<Bank>

    @GET("api/Settlement/GetTripList")
    suspend fun getTripCodes():List<Trip>

    @GET("api/Settlement/GetDetailTrip")
    suspend fun getDetailTrip(@Query("tripId") tripId : String) : DetailSettlementResult

    @POST("api/Settlement/GetSpecificAreaCompensation")
    suspend fun putSpecificAreaCompensation(@Body body:MutableMap<String,Int>) : RateStayResult

    @GET("api/Settlement/GetTransportModeList")
    suspend fun getModeTransport() :  List<ModeTransport>

    @POST("api/Settlement/CalculateTransportExpense")
    suspend fun calculateTransportExpense(@Body body:MutableMap<String,Any>) : CalculateTransportResult

    @GET("api/Settlement/GetExpenseTypeList")
    suspend fun getExpenseType() : List<ExpenseType>

    @POST("api/Settlement/Submit")
    suspend fun submitSettlement(@Body submit : SubmitSettlement) : SubmitResult

    companion object {
        fun createRequest(token: String, baseUrl: String): ServiceApi {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder().apply {
                addInterceptor(Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    if (token.isNotEmpty() || token.isNotBlank()){
                        builder.header("Authorization", token)
                    }
                    builder.header("Content-Type", "application/json")

                    return@Interceptor chain.proceed(builder.build())
                })

                addInterceptor(interceptor)
            }
            val retrofit = Retrofit.Builder().apply {
                baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                client(httpClient.build())
            }
            return retrofit.build().create(ServiceApi::class.java)
        }
    }
}