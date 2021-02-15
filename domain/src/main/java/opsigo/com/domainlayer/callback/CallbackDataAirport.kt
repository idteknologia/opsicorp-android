package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.hotel.NearbyAirportModel

interface CallbackDataAirport {
    fun success(data: ArrayList<NearbyAirportModel>)
    fun failed(message:String)
}