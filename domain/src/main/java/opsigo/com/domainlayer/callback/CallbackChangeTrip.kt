package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.travel_request.ChangeTripModel


interface CallbackChangeTrip {
    fun successLoad(data: ChangeTripModel)
    fun failedLoad(message:String)
}