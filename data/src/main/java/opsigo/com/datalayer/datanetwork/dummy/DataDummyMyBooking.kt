package opsigo.com.datalayer.datanetwork.dummy

import opsigo.com.domainlayer.model.my_booking.MyBookingModel

class DataDummyMyBooking {
    fun addDataListMybooking(): ArrayList<MyBookingModel> {

        val data = ArrayList<MyBookingModel>()
        val date = ArrayList<String>()
        val type = ArrayList<Int>()
        val name = ArrayList<String>()

        name.add("Trans Luxury Bandung")
        name.add("Air asia")
        name.add("Garuda")
        name.add("Gayana")

        date.add("20-10-2019")
        date.add("20-10-2019")
        date.add("20-9-2019")
        date.add("20-8-2019")

        type.add(1)
        type.add(0)
        type.add(0)
        type.add(1)

        date.forEachIndexed { index, s ->
            val mData = MyBookingModel()

            mData.date = date[index]
            mData.nameAccomodation = name[index]
            mData.typeAccomdation  = type[index]
            mData.idBooking        = System.currentTimeMillis().toString()
            mData.prize            = "5.00${index}.000"
            mData.cityDeparture    = "Surabaya"
            mData.cityArrival      = "Jakarta"
            mData.statusPayment    = "Purchase Successful"

            data.add(mData)
        }

        return data
    }
}