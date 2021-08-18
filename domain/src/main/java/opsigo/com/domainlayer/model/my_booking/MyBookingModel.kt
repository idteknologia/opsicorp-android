package opsigo.com.domainlayer.model.my_booking

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyBookingModel (
    var type : String = "",
    var typeAccomdation : Int = 0,
    var date : String = "",
    var idBooking : String = "",
    var prize     : String = "",
    var cityDeparture : String = "",
    var cityArrival   : String = "",
    var nameAccomodation : String = "",
    var statusPayment : String = "",
    var isRoundtrip:Boolean = false
        ):Parcelable