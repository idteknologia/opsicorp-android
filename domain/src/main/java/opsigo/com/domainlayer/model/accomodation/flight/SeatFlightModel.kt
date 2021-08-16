package opsigo.com.domainlayer.model.accomodation.flight

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class SeatFlightModel (
    var type       :String = "",
    var numberSeat :String = "",
    var x          :Int = 0,
    var y          :Int = 0,
    var number          :String = "",
    var status          :String = "",
    var seatName        :String = "",
    var price           :String = "",
    var ccy             :String = "",
    var seatGroup       :String = "",
    var seatClass       :String = "",
    var seatClassCode   :String = "",
    var seatRow      :String = "",
    var flightNumber :String = ""
):Parcelable