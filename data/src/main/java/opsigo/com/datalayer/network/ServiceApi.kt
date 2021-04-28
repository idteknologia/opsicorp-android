package opsigo.com.datalayer.network

import android.content.Context
import android.preference.PreferenceManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import opsigo.com.datalayer.model.result.City
import opsigo.com.datalayer.model.result.Result
import opsigo.com.domainlayer.model.trip.TripResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ServiceApi {

    @GET(MyURL.LIST_APPROVE)
    suspend fun getTripResult(@QueryMap map: MutableMap<String, String>): TripResult

    @GET(MyURL.CITY)
    suspend fun getCities() : List<City>


    companion object {
        fun createRequest(context: Context): ServiceApi {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder().apply {
                getDataPref(context, "token").let {
                    addInterceptor(Interceptor { chain ->
                        val builder = chain.request().newBuilder()
                        builder.header("Authorization", it)
                        builder.header("Content-Type","application/json")

                        return@Interceptor chain.proceed(builder.build())
                    })

                }
                addInterceptor(interceptor)
            }

            val baseUrl = getDataPref(context,"BASE_URL")
            val retrofit = Retrofit.Builder().apply {
                baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                client(httpClient.build())
            }
            return retrofit.build().create(ServiceApi::class.java)
        }

        private fun getDataPref(context: Context, key: String): String {
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
            return sharedPref.getString(key, "") ?: ""
        }
    }
}