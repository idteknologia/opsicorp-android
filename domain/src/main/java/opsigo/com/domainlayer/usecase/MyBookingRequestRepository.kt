package opsigo.com.domainlayer.usecase

import opsigo.com.domainlayer.callback.CallbackDetailMyBooking
import opsigo.com.domainlayer.callback.CallbackListMyBooking

interface MyBookingRequestRepository {
    fun getListMyBooking(token: String,size :Int,index:Int,itemTypes:String, dateFrom:String,dateTo:String,callback: CallbackListMyBooking)
    fun getDetailMyBooking(token: String,id:String, callback: CallbackDetailMyBooking)
}