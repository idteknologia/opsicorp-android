package opsigo.com.datalayer.datanetwork

import opsigo.com.domainlayer.usecase.MyBookingRequestRepository
import opsigo.com.data.network.UrlEndpoind
import opsigo.com.domainlayer.callback.*
import okhttp3.ResponseBody
import opsigo.com.datalayer.mapper.DetailMyBookingMapper
import opsigo.com.datalayer.mapper.ListMyBookingMapper
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.datalayer.model.myboking.DetailMyBookingEntity
import opsigo.com.datalayer.model.myboking.ListMyBookingEntity
import javax.inject.Inject
import java.lang.Exception
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Callback
import retrofit2.Call

class GetDataMyBooking(baseUrl:String) : BaseGetData(), MyBookingRequestRepository {
    @Inject
    lateinit var apiOpsicorp : UrlEndpoind

    init {
        BASE_URL = baseUrl
        apiDependency().inject(this)
    }

    override fun getListMyBooking(token: String,size :Int,index:Int,itemTypes:String,dateFrom:String,dateTo:String, callback: CallbackListMyBooking) {
        apiOpsicorp.getListMyBooking(token,size,index,itemTypes,dateFrom ,dateTo).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val data = response.body()?.string()
                        callback.success(ListMyBookingMapper().mapping(Serializer.deserialize(data,ListMyBookingEntity::class.java)))
                    }
                    else{
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failed(message)
                    }
                }catch (e: Exception){
                    callback.failed(messageFailed)
                }
            }


            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }
        })
    }

    override fun getDetailMyBooking(token: String, id: String,callback: CallbackDetailMyBooking) {
        apiOpsicorp.getDetailMyBooking(token,id).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                   if (response.isSuccessful){
                       val data = response.body()?.string()
                       callback.success(DetailMyBookingMapper().mapping(Serializer.deserialize(data, DetailMyBookingEntity::class.java)))
                   }
                    else{
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failed(message)
                    }
                }catch (e: Exception){
                    callback.failed(messageFailed)
                }
            }


            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failed(t.message.toString())
            }

        })
    }
}
