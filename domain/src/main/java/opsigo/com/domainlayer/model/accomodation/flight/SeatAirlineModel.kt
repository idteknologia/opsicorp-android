package opsigo.com.domainlayer.model.accomodation.flight

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class SeatAirlineModel (
    var nameFlight   :String = "",
    var nameAirCraft :String = "",
    var flightNumber :String = "",
    var totalRows    :Int = 0,
    var dataSeat     :ArrayList<SeatFlightModel> = ArrayList()
): Parcelable