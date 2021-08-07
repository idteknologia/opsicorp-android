package com.mobile.travelaja.module.my_booking.eticket.model

import android.os.Parcelable
import com.mobile.travelaja.utility.Utils
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TicketResult(
    var PnrCode: String,
    var Origin: String,
    var Destination: String,
    var OriginView:String,
    var DestinationView: String,
    var Passengers: List<Passenger>,
    var Segments: List<Segment>,
    var Amount: Number,
) : Parcelable

@Parcelize
data class Segment(
    var Id: String,
    var TrainName: String,
    var CurrierNumber: String,
    var DepartDate: String,
    var DepartTime: String,
    var ArriveDate: String,
    var ArriveTime: String,
    var OriginName: String,
    var AirportOrigin: String,
    var DestinationName: String,
    var AirportDestination: String,
    var ClassCode: String,
    var Category: String,
    var KaiImageUrl: String,
    var DepartDateTime: String,
    var ArriveDateTime: String,
) : Parcelable {
    fun getDurationTime() : String ?{
        return Utils.getDurationTime(DepartDateTime,ArriveDateTime)
    }
}

@Parcelize
data class Passenger(
    var Id: String,
    var Type: String,
    var FirstName: String,
    var LastName: String,
    var SeatNumber: String,
    var SeatName: String,
    var SeatText: String,
    var IdNumber: String
) : Parcelable{
    fun name(): String {
        return "$FirstName $LastName"
    }
}
