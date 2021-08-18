package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.myboking.ListMyBookingEntity
import opsigo.com.domainlayer.model.my_booking.MyBookingModel
import java.util.ArrayList

class ListMyBookingMapper {
    fun mapping(deserialize: ListMyBookingEntity): ArrayList<MyBookingModel> {
        val data = ArrayList<MyBookingModel>()
        if (!deserialize.data.isNullOrEmpty()){
            deserialize.data.forEach {
                val mData = MyBookingModel(
                    "",
                    it.itemType,
                    it.created.toString(),
                    it.id.toString(),
                    it.totalPaid.toString(),
                    it.originCity.toString(),
                    it.destinationCity.toString(),
                    it.hotelName.toString(),
                    it.paymentStatusText.toString(),
                    it.isRoundtrip
                )
                data.add(mData)
            }
        }
        return data
    }
}