package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.my_booking.DetailMyBookingModel

interface CallbackDetailMyBooking {
    fun success(data:DetailMyBookingModel)
    fun failed(message:String)
}