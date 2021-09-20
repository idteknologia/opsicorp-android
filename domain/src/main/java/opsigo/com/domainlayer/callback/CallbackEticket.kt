package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.my_booking.DetailMyBookingModel

interface CallbackEticket {
    fun successLoad(summaryModel: DetailMyBookingModel)
    fun failedLoad(message:String)
}