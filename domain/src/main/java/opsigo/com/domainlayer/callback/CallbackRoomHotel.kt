package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.hotel.SelectRoomModel

interface CallbackRoomHotel {
    fun successLoad(data: ArrayList<SelectRoomModel>)
    fun failedLoad(message:String)
}