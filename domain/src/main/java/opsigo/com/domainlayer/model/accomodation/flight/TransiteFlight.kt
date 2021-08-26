package opsigo.com.domainlayer.model.accomodation.flight

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class TransiteFlight (
    var numberFlight     :String = "",//GA 303
    var originId         :String = "", // SUB
    var destinationId    :String = "",
    var airlineImageUrl  :String = "",
    var priceTotal       :String = "", //2598000.0
    var isMultiClass     :Boolean =  false,
    var isAvailable      :Boolean = false,
    var departDate       :String = "",// "2020-09-22"
    var departTime       :String = "",//05:25
    var arriveDate       :String = "",//: "2020-09-22",
    var arriveTime       :String = "",//07:00"
    var duration         :String = "",//01:35:00",
    var durationView     :String = "",//1h 35m",
    var transitDuration     :String = "",//00:45:00",
    var transitDurationView :String = "", //0h 45m

    var operatingNumber :String = "", //GA
    var titleAirline    :String = "", //Garuda Indonesia
    var classFlight     :String = "",
    var seatNumber      :String = "",
    var originTerminal  :String = "",//1
    var destinationTerminal :String = "",//2
    var originCity      :String = "",//Surabaya
    var originAirport   :String = "",//"Juanda Airport"
    var destinationCity :String = "",//Jakarta",
    var destinationAirport :String = "",//Soekarno Hatta",
    var crossDay        :String = "",// 0.0,
    var airlineName     :String = "",//Garuda Indonesia",
    ): Parcelable