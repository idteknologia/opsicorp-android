package opsigo.com.domainlayer.usecase

import opsigo.com.domainlayer.callback.*
import okhttp3.MultipartBody

interface CreateTripPlaneRepository {
    fun getDataPurpose(token:String,callbackPurpose: CallbackPurpose)
    fun getDataCityFlight(token: String, callback: CallbackArrayListCity)
    fun uploadFile(token: String, data: MultipartBody.Part?, callback: CallbackUploadFile)
    fun getDataBudget(token: String, employeeId: String, travelAgentCode: String, callbackBudget: CallbackBudget)
    fun getDataCostCenter(token: String,employeeId: String,codeBudget:String, callback: CallbackCostCenter)
    fun saveAsDraftTripPlant(token: String, data:HashMap<String,Any>, callback:CallbackSaveAsDraft)
    fun submitTripPlant(token: String, data:HashMap<String,Any>, callback:CallbackSubmitTripPlant)
    fun getDataCity(token:String,callback: CallbackArrayListCity)
    fun cancelTripplan(token: String,id:String,callback:CallbackCancelTripplan)
}