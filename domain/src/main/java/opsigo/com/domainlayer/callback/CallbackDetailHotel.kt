package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.hotel.ResultListHotelModel

interface CallbackDetailHotel {
    fun success(data: ResultListHotelModel)
    fun failed(error: String)
}