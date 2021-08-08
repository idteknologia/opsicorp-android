package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.myboking.ListMyBookingEntity
import opsigo.com.domainlayer.model.my_booking.MyBookingModel
import java.util.ArrayList

class MyBookingListMapper {
    fun mapper(deserialize: ListMyBookingEntity): ArrayList<MyBookingModel> {
        return ArrayList()
    }
}