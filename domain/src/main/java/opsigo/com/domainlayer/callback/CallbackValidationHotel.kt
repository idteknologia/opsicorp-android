package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.hotel.ValidationHotelModel

interface CallbackValidationHotel {
    fun success(data:ValidationHotelModel)
    fun failed(message: String)
}