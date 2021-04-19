package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.travel_request.EstimatedCostTravelRequestModel


interface CallbackEstimatedCostTravelRequest {
    fun successLoad(data: EstimatedCostTravelRequestModel)
    fun failedLoad(message:String)
}