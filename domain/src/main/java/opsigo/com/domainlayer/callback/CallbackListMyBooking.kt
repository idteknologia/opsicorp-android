package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.my_booking.MyBookingModel

interface CallbackListMyBooking {
    fun success(data:ArrayList<MyBookingModel>)
    fun failed(message:String)
}