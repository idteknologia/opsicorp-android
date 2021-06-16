package opsigo.com.datalayer.datanetwork

import okhttp3.ResponseBody
import opsigo.com.data.network.UrlEndpoind
import opsigo.com.datalayer.mapper.*
import opsigo.com.datalayer.model.create_trip_plane.save_as_daft.SaveAsDraftEntity
import opsigo.com.datalayer.model.travel_request.EstimatedCostEntity
import opsigo.com.domainlayer.callback.*
import opsigo.com.domainlayer.usecase.TravelRequestRepository
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class GetDataTravelRequest(baseUrl:String) : BaseGetData(),TravelRequestRepository {
    @Inject
    lateinit var apiOpsicorp : UrlEndpoind

    init {
        BASE_URL = baseUrl
        apiDependency().inject(this)
    }

    override fun getTypeActivity(token: String, callback: CallbackTypeActivity) {
        apiOpsicorp.getTypeActivity(token,token).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val data = response.body()?.string()
                        callback.success(TypeActivityMapper().mapping(data.toString()))
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

    override fun getEstimatedCost(token: String, data: HashMap<Any, Any>, callback: CallbackEstimatedCostTravelRequest) {
        apiOpsicorp.getEstimatedCost(token,data).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val data = response.body()?.string()
                        callback.successLoad(EstimatedCostMapper().mapping(Serializer.deserialize(data,EstimatedCostEntity::class.java)))
                    }
                    else{
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failedLoad(message)
                    }
                }catch (e: Exception){
                    callback.failedLoad(messageFailed)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message.toString())
            }
        })
    }

    override fun submitTravelRequest(token: String, data: HashMap<String, Any>, callback: CallbackSaveAsDraft) {
        apiOpsicorp.submitTravelRequest(token,data).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        val data = Serializer.deserialize(responseString.toString(), SaveAsDraftEntity::class.java)
                        callback.successLoad(SaveAsDraftMapper().mapping(data))
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failedLoad(message)
                    }
                }catch (e:Exception){
                    callback.failedLoad(messageFailed)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }
        })
    }

    override fun approveAllTrip(token: String, data: HashMap<Any, Any>, callback: CallbackString) {
        apiOpsicorp.getTypeActivity(token,token).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }
        })
    }

    override fun issuedAllTrip(token: String, tripid: String, callback: CallbackApprovAll) {
        apiOpsicorp.issuedAllTravelRequest(token, tripid).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        val json = JSONObject(responseString)
                        if (json.getBoolean("isSuccess")){
                            callback.successLoad(ApprovalAllMapper().mapping(responseString!!))
                        }else{
                            callback.failedLoad(json.getString("errorMessage"))
                        }
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failedLoad(message)
                    }
                }catch (e:Exception){
                    callback.failedLoad(messageFailed)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }
        })
    }

}
