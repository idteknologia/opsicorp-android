package opsigo.com.domainlayer.usecase

import opsigo.com.domainlayer.callback.*

interface TripPlaneRequestRepository {
    fun getTypeActivity(token:String,callback: CallbackString)
    fun getEstimatedCost(token: String,data:HashMap<Any,Any>, callback: CallbackString)
    fun submitTravelTravelRequest(token: String,data:HashMap<Any,Any>,callback: CallbackString)
    fun approveAllTrip(token: String,data:HashMap<Any,Any>,callback: CallbackString)
    fun issuedAllTrip(token: String,data:HashMap<Any,Any>,callback: CallbackString)
}