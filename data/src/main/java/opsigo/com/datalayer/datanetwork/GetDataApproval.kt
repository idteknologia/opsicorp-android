package opsigo.com.datalayer.datanetwork

import opsigo.com.data.network.UrlEndpoind
import opsigo.com.datalayer.mapper.ApprovalAllMapper
import opsigo.com.datalayer.mapper.TotalApprovalMapper
import opsigo.com.domainlayer.callback.CallbackApprovAll
import opsigo.com.domainlayer.callback.CallbackTotalApproval
import opsigo.com.domainlayer.usecase.ApprovalRepository
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class GetDataApproval(baseUrl:String) : BaseGetData(), ApprovalRepository {
    @Inject
    lateinit var apiOpsicorp : UrlEndpoind

    init {
        BASE_URL = baseUrl
        apiDependency().inject(this)
    }

    override fun getDataTotalApproval(token: String, size: String, index: String, orderBy: String, direction: String, tripDateFrom: String, tripDateTo: String,callback: CallbackTotalApproval) {
        apiOpsicorp.getTotalApproval(token,size,index,orderBy,direction,tripDateFrom,tripDateTo)
                .enqueue(object :Callback<ResponseBody>{
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        callback.failedLoad(t.message!!)
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        try {
                            if (response.isSuccessful){
                                val responseString = response.body()?.string()
                                callback.successLoad(TotalApprovalMapper().mapping(JSONArray(responseString)))
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
                })
    }

    override fun approveAll(token: String, data: HashMap<Any,Any>,callback: CallbackApprovAll) {

        apiOpsicorp.approvAll(token,data)
                .enqueue(object :Callback<ResponseBody>{
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        callback.failedLoad(t.message!!)
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        try {
                            if (response.isSuccessful){
                                val responseString = response.body()?.string()
                                val json = JSONObject(responseString)
                                if (json.getBoolean("isSuccess")){
                                    callback.successLoad(ApprovalAllMapper().mapping(responseString!!))
                                }
                                else{
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
                })
    }

    override fun approveItem(token: String, data: HashMap<Any, Any>, callback: CallbackApprovAll) {
        apiOpsicorp.approvePerItem(token,data)
                .enqueue(object :Callback<ResponseBody>{
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        callback.failedLoad(t.message!!)
                    }

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
                })
    }

    override fun approvePerPax(token: String, data: HashMap<Any,Any>,callback: CallbackApprovAll) {
        apiOpsicorp.approvPerPax(token,data)
                .enqueue(object :Callback<ResponseBody>{
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        callback.failedLoad(t.message!!)
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful){
                            val responseString = response.body()?.string()
                            val json = JSONObject(responseString)
                            if (json.getBoolean("isSuccess")){
                                callback.successLoad(ApprovalAllMapper().mapping(responseString!!))
                            }
                            else{
                                callback.failedLoad(json.getString("errorMessage"))
                            }
                        }
                        else {
                            val json = JSONObject(response.errorBody()?.string())
                            val message = json.optString("error_description")
                            callback.failedLoad(message)
                        }
                    }
                })
    }

    /*
    override fun saveAsDraftTripPlane(token: String, data: HashMap<String, Any>, callback: CallbackSaveAsDraft) {
        //test
        GetDataGeneral().apiOpsicorp.posDataSaveDraft(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: PaymentResponse<ResponseBody>) {
                if (response.isSuccessful){
                    val responseString = response.body()?.string()
                    val data = Serializer.deserialize(responseString.toString(),SaveAsDraftEntity::class.java)
                    callback.successLoad(SaveAsDraftMapper().mapping(data))
                }
                else {
                    val json = JSONObject(response.errorBody()?.string())
                    val message = json.optString("error_description")
                    callback.failedLoad(message)
                }
            }
        })

    }*/

}
