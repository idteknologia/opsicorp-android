package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.hotel.ConfirmationHotelModel


interface CallbackConfirmationHotel {
    fun success(confirmationHotelModel: ConfirmationHotelModel)
    fun failed(string: String)
}