package opsigo.com.domainlayer.usecase

import opsigo.com.domainlayer.callback.*

interface TravelRequestRepository {
    fun getTypeActivity(token:String,callback: CallbackTypeActivity)
    fun getEstimatedCost(token: String,data:HashMap<Any,Any>, callback: CallbackEstimatedCostTravelRequest)
    fun submitTravelRequest(token: String, data:HashMap<String,Any>, callback: CallbackSaveAsDraft)
    fun approveAllTrip(token: String,data:HashMap<Any,Any>,callback: CallbackString)
    fun issuedAllTrip(token: String,tripid: HashMap<Any,Any>,callback: CallbackApprovAll)
    fun checkDateAvaibility(token: String, tripid: HashMap<Any,Any>, callback: CallbackString)
    fun checkCashAdvance(token: String, tripData: HashMap<Any,Any>, callback: CallbackCashAdvance)
}