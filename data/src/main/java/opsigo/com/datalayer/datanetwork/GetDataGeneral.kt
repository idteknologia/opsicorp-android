package opsigo.com.datalayer.datanetwork

import android.util.Log
import com.google.gson.JsonObject
import opsigo.com.data.network.UrlEndpoind
import opsigo.com.datalayer.mapper.*
import opsigo.com.datalayer.model.*
import opsigo.com.datalayer.model.approval.list_approval.ListApprovalEntity
import opsigo.com.domainlayer.callback.*
import opsigo.com.domainlayer.usecase.GeneralRepository

import okhttp3.ResponseBody
import opsigo.com.datalayer.model.calendar.CalendarHolidayEntity
import opsigo.com.datalayer.model.signin.ResponseVersionEntity
import opsigo.com.datalayer.model.signin.version.RespVersionEntity
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class GetDataGeneral(baseUrl:String) : BaseGetData(), GeneralRepository {
    @Inject
    lateinit var apiOpsicorp : UrlEndpoind

    init {
        BASE_URL = baseUrl
        apiDependency().inject(this)
    }

    override fun getDataUpcomingFlight(token:String, callbackUpcomingFlight: CallbackUpcomingFlight) {
        apiOpsicorp.getDataUpcomingFlight(token).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callbackUpcomingFlight.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        callbackUpcomingFlight.successLoad(UpcomingFlightEntityDataMapper().transform(Serializer.deserialize(response.body()?.string().toString(), UpcomingFlightEntity::class.java)))
                    }
                    else{
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callbackUpcomingFlight.failedLoad(message)
                    }
                }catch (e:Exception){
                    callbackUpcomingFlight.failedLoad(messageFailed)
                }
            }
        })
    }

    override fun getCheckVersion(token: String,version:String,type:String, callback: CallbackCheckVersion) {
        apiOpsicorp.checkVersion(version,type).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){

                        val xxx = response.body()?.string().toString()
                        val data = CheckVersionMapper().mapping(Serializer.deserialize(xxx, RespVersionEntity::class.java))

                        if (data.enable){
                            callback.successLoad(data)
                        }
                        else {
                            callback.failedLoad(data.message)
                        }
                    }
                    else{
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("message")
                        callback.failedLoad(message)
                    }
                }catch (e:Exception){
                    callback.failedLoad(messageFailed)
                }
            }
        })
    }

    override fun getHolidayApi(year: String, idCountry: String, callbackHoliday: CallbackHoliday) {
        apiOpsicorp.getCalendarHoliday(year,idCountry).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callbackHoliday.failed(messageFailed)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        callbackHoliday.success(HolidayDataMapper().transform(Serializer.deserialize(response.body()?.string().toString(), CalendarHolidayEntity::class.java)))
                    }
                }catch (e:Exception){
                    callbackHoliday.failed(messageFailed)
                }
            }
        })
    }

    override fun getCheckIdDevice(year: String, idDevice: String, callback: CallbackIdDevice) {
        apiOpsicorp.getCheckDeviceId(year,idDevice).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failed(messageFailed)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val string = response.body()?.string()
                        val data = JSONObject(string)
                        callback.success (data.getBoolean("status"))
                    }
                }catch (e:Exception){
                    callback.failed(messageFailed)
                }
            }
        })
    }
//
//    override fun getDataAirlinePreference(token: String,jobTitleId:String,companyCode:String ,callbackAirlinePreference: CallbackAirlinePreference) {
//        apiOpsicorp.getAirlineprefered(token,jobTitleId,companyCode).enqueue(object :Callback<ResponseBody>{
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                callbackAirlinePreference.failedLoad(t.message!!)
//            }
//
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                try {
//                    if (response.isSuccessful){
//                        callbackAirlinePreference.successLoad(AirlinePreferenceEntityDataMapper().transform(Serializer.deserialize(response.body()?.string().toString(), AirlinePreferenceEntity::class.java)))
//                    }
//                    else{
//                        val json = JSONObject(response.errorBody()?.string())
//                        val message = json.optString("error_description")
//                        callbackAirlinePreference.failedLoad(message)
//                    }
//                }catch (e:Exception){
//                    callbackAirlinePreference.failedLoad(messageFailed)
//                }
//            }
//        })
//    }

    override fun setProfile(token: String, mobilePhone: String, nationality: String, callbackSetProfile: CallbackSetProfile) {
        apiOpsicorp.setProfile(token, mobilePhone, nationality).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callbackSetProfile.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){

                        val resp = response.body()?.string().toString()
                        Log.d("xcatx",": " + resp)
                        val json = JSONObject(resp)
                        val isSuccesss = json.optBoolean("success")
                        callbackSetProfile.successLoad(isSuccesss)
                    }
                    else{
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callbackSetProfile.failedLoad(message)
                    }
                }catch (e:Exception){
                    callbackSetProfile.failedLoad(messageFailed)
                }
            }
        })
    }

    override fun setDeviceId(token: String, userName: String, deviceId: String, modelPhone: String, callbackDevice: CallbackSetDeviceId) {
        apiOpsicorp.setDeviceId(token, userName, deviceId, modelPhone).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callbackDevice.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){

                        val resp = response.body()?.string().toString()
                        Log.d("xcatx",": " + resp)
                        val json = JSONObject(resp)
                        val isSuccesss = json.optBoolean("success")
                        callbackDevice.successLoad(isSuccesss)
                    }
                    else{
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callbackDevice.failedLoad(message)
                    }
                }catch (e:Exception){
                    callbackDevice.failedLoad(messageFailed)
                }
            }
        })
    }

    override fun removeDeviceId(token: String, userName: String, deviceId: String, modelPhone: String, callbackDevice: CallbackSetDeviceId) {
        apiOpsicorp.removeDeviceId(token, userName, deviceId, modelPhone).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callbackDevice.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val resp = response.body()?.string().toString()
                        Log.d("xcatx",": " + resp)
                        val json = JSONObject(resp)
                        val isSuccesss = json.optBoolean("success")
                        callbackDevice.successLoad(isSuccesss)
                    }
                    else{
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callbackDevice.failedLoad(message)
                    }
                }catch (e:Exception){
                    callbackDevice.failedLoad(messageFailed)
                }
            }
        })
    }

    override fun getListTripplan(token: String, size: String, index: String, orderBy: String, direction: String, tripDateFrom: String, tripDateTo: String, callback: CallbackListTripplan) {
        apiOpsicorp.getListTripplan(token,size,index,orderBy,direction,tripDateFrom,tripDateTo).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                val resp = response
//                callback.successLoad(MapperModelListTripplan().mapper(Serializer.deserialize(resp.body()?.string().toString(), ListApprovalEntity::class.java)))

                 try{
                     val resp = response
                     if(resp.isSuccessful){
                         callback.successLoad(MapperModelListTripplan().mapper(Serializer.deserialize(resp.body()?.string().toString(), ListApprovalEntity::class.java)))
                     }else{
                         if(resp.code() == 401){
                             Log.d("log","lakukan aksi ketika Unauthorized, login ulang")
                         }
                     }
                 }catch (e : Exception){
                     val resp = response
                     Log.d("log",":" + resp.body().toString())
                     callback.failedLoad("something wrong with data mapper")
                 }

            }
        })
    }

//    override fun getListCart(token: String, size: String, index: String, orderBy: String, direction: String, tripDateFrom: String, tripDateTo: String, callback: CallbackListCart) {
    override fun getListCart(token: String, size: String, index: String, orderBy: String, direction: String, callback: CallbackListCart) {
        apiOpsicorp.getListCart(token,size,index,orderBy,direction).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try{
                    val resp = response
                    if(resp.isSuccessful){
                        callback.successLoad(MapperModelListCart().mapper(Serializer.deserialize(response.body()?.string().toString(), ListApprovalEntity::class.java)))
                    }else{
                        if(resp.code() == 401){
                            Log.d("log","lakukan aksi ketika Unauthorized, login ulang")
                        }
                    }
                }catch (e : Exception){
                    val resp = response
                    Log.d("log",":" + resp.body().toString())
                    callback.failedLoad("something wrong with data mapper")
                }

            }
        })
    }

    override fun getDataSummary(token:String, id: String, callbackSummary: CallbackSummary) {
        apiOpsicorp.getDataSummary(token, id).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callbackSummary.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val summaryEntity = Serializer.deserialize(response.body()?.string().toString(), SummaryEntity::class.java)
                        val summ = SummaryEntityMapper().mapFrom(summaryEntity)
                        callbackSummary.successLoad(summ)
                    }
                    else{
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callbackSummary.failedLoad(message)
                    }
                }catch (e:Exception){
                    callbackSummary.failedLoad(messageFailed)
                }
            }
        })
    }

}
