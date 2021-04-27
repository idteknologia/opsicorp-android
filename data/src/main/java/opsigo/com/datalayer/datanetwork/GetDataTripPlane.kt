package opsigo.com.datalayer.datanetwork

import opsigo.com.data.network.UrlEndpoind
import opsigo.com.datalayer.mapper.*
import opsigo.com.datalayer.model.create_trip_plane.trip_plan.UploadFileEntity
import opsigo.com.datalayer.model.create_trip_plane.save_as_daft.SaveAsDraftEntity
import opsigo.com.domainlayer.callback.*
import opsigo.com.domainlayer.usecase.CreateTripPlaneRepository
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import opsigo.com.datalayer.model.payment.PaymentEntity
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class GetDataTripPlane(baseUrl:String) : BaseGetData(), CreateTripPlaneRepository {
    @Inject
    lateinit var apiOpsicorp : UrlEndpoind

    init {
        BASE_URL = baseUrl
        apiDependency().inject(this)
    }

    override fun getDataPurpose(token:String, callbackPurpose: CallbackPurpose) {
        apiOpsicorp.getDataPurpose(token).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callbackPurpose.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val data = JSONArray(response.body()?.string())
                        callbackPurpose.successLoad(PurposeEntityDataMapper().transform(data))
                    }
                    else{
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callbackPurpose.failedLoad(message)
                    }
                }catch (e:Exception){
                    callbackPurpose.failedLoad(messageFailed)
                }
            }
        })
    }

    override fun getDataAiport(token: String, callback: CallbackDataAirport) {
        apiOpsicorp.getDataAirport(token).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failed(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val data = JSONArray(response.body()?.string())
                        callback.success(AirportMapper().mapper(data))
                    }
                    else{
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failed(message)
                    }
                }catch (e:Exception){
                    callback.failed(messageFailed)
                }
            }
        })
    }

    override fun uploadFile(token: String,data: MultipartBody.Part?, callback: CallbackUploadFile) {
        apiOpsicorp.posDataAttachment(token,data).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val data = response.body()?.string()!!
                        val dataResponse = Serializer.deserialize(data, UploadFileEntity::class.java)
                        callback.successLoad(UploadFileMapper().mapping(dataResponse))
                    }
                    else{
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

    override fun getDataBudget(token: String, employeeId: String, travelAgentCode: String, callbackBudget: CallbackBudget) {
        apiOpsicorp.getDataBudget(token,employeeId,travelAgentCode).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callbackBudget.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){

                        val responsString = response.body()?.string().toString()
                        callbackBudget.successLoad(BudgetEntityDataMapper().transform(JSONArray(responsString)))
                    }
                    else{
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callbackBudget.failedLoad(message)
                    }
                }catch (e:Exception){
                    callbackBudget.failedLoad(messageFailed)
                }
            }
        })
    }

    override fun getDataCostCenter(token: String,employeeId: String,codeBudget:String,callback: CallbackCostCenter){
        apiOpsicorp.posDataSelectCost(token,codeBudget,employeeId).enqueue(object :Callback<ResponseBody>{
           override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
               callback.failedLoad(t.message!!)
           }

           override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
               try {
                   if (response.isSuccessful){
                       val responsString = response.body()?.string()
                       callback.successLoad(CostCenterDataMapper().transform(JSONArray(responsString)))
                   }
                   else{
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

    override fun saveAsDraftTripPlant(token: String, data: HashMap<String, Any>, callback: CallbackSaveAsDraft) {
        //test
       apiOpsicorp.posDataSaveDraft(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
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
                }catch (e:Exception){
                    callback.failedLoad(messageFailed)
                }
            }
        })

    }

    override fun saveAsDraftTripPlantPersonal(token: String, data: HashMap<String, Any>, callback: CallbackSaveAsDraft) {
        //test
        apiOpsicorp.posDataSaveDraftPersonal(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
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
                }catch (e:Exception){
                    callback.failedLoad(messageFailed)
                }
            }
        })

    }

    override fun submitTripPlant(token: String, data: HashMap<String, Any>, callback: CallbackSubmitTripPlant) {
        apiOpsicorp.submitTrip(token,data).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        callback.successLoad(SaveAsDraftMapper().mappingSuccesCheckout(responseString))
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


    override fun getDataCity(token: String, callback: CallbackListCityTrip) {
        apiOpsicorp.getCity(token).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val data = JSONArray(response.body()?.string())
                        callback.successLoad(CityTripMapper().mapper(data))
                    }
                    else{
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

    override fun cancelTripplan(token: String, id: String, callback: CallbackCancelTripplan) {
       apiOpsicorp.postCancelTrip(token,id).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        val json = JSONObject(responseString)
                        val isSuccess = json.optBoolean("isSuccess")
                        callback.successLoad(isSuccess)
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        callback.failedLoad(json.toString())
                    }
                }catch (e:Exception){
                    callback.failedLoad(messageFailed)
                }
            }
        })
    }

    override fun checkExistTripPersonal(token: String, callback: CallbackString) {
        apiOpsicorp.checkExistTripPersonal(token).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        val json = JSONObject(responseString)
//                        if (json.getBoolean("isExist")){
//                            callback.successLoad(json.getString("tripId"))
//                        }
//                        else {
//                            callback.successLoad("")
//                        }

                        callback.successLoad("")

                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        callback.failedLoad(json.toString())
                    }
                }catch (e:Exception){
                    callback.failedLoad(messageFailed)
                }
            }
        })
    }

    fun getPaymentLink(token: String,id: String, callback: CallbackPayment) {
        apiOpsicorp.getPaymentLink(token,id).enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.failedLoad(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful){
                        val responseString = response.body()?.string()
                        val data = Serializer.deserialize(responseString.toString(),PaymentEntity::class.java)
                        callback.successLoad(PaymentGatewayMapper().mapping(data))
                    }
                    else {
                        val json = JSONObject(response.errorBody()?.string())
                        val message = json.optString("error_description")
                        callback.failedLoad(message)
                    }
                } catch (e:Exception){
                    callback.failedLoad(messageFailed)
                }
            }

        })
    }
}
