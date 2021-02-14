package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.hotel.CityHotel
interface CallbackArrayListCity {
    fun successLoad(data: ArrayList<CityHotel>)
    fun failedLoad(message:String)
}