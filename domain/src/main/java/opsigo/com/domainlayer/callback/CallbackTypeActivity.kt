package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.travel_request.TypeActivityTravelRequestModel


interface CallbackTypeActivity {
    fun success(data: ArrayList<TypeActivityTravelRequestModel>)
    fun failed(message:String)
}