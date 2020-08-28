package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.hotel.BookingHotelModel

interface CallbackBookingHotel {
    fun success(data:BookingHotelModel)
    fun failed(string: String)
}