package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.hotel.CityHotelModel
interface CallbackListCityHotel {
    fun successLoad(data: ArrayList<CityHotelModel>)
    fun failedLoad(message:String)
}