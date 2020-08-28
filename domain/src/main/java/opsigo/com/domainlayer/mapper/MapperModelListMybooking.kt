package opsigo.com.domainlayer.mapper

import opsigo.com.domainlayer.model.my_booking.MyBookingModel

class MapperModelListMybooking {

    fun mapper(data :ArrayList<MyBookingModel>): ArrayList<MyBookingModel>{
        val mData = ArrayList<MyBookingModel>()

        var lastDate = ""
        data.forEachIndexed { index, myBookingModel ->
            if (!lastDate.equals(myBookingModel.date.split("-")[1])){
                val headerData = MyBookingModel()
                headerData.type = "Header"
                headerData.date = myBookingModel.date
                mData.add(headerData)
                mData.add(myBookingModel)
            }
            else{
                mData.add(myBookingModel)
            }
            lastDate = myBookingModel.date.split("-")[1]
        }
        return mData
    }
}