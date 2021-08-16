package opsigo.com.domainlayer.model.accomodation.train

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
class ResultListTrainModel (

    var fareBaseCode   :String = "",
    var subClass       :String = "",
    var titleTrain     :String = "",
    var className      :String = "",
    var totalSeat      :String = "",
    var price          :String = "",
    var statusTrain    :String = "Reserved",
    var nameStation    :String = "",
    var timeArrifal    :String = "",
    var timeDeparture  :String = "",
    var dateArrival    :Date = Date(),
    var dateDeparture  :Date = Date(),
    var duration       :String  = "",
    var notComply      :Boolean = false,

    var classKey       :String = "",
    var carrierNumber  :String = "",
    var origin         :String = "",
    var destination    :String = "",
    var journeyCode    :String = "",
    var num            :String = "",
    var trainName      :String = "",
    var durationTime   :String = "",

    var descSecuritySensitivity :String = "",
    var causeViolatedTrainRules :String = "",
    var dateArrivalString       :String = "",
    var dateDepartureString     :String = "",
    var isSecuritySensitivity   :Boolean= false,
    var isViolatedTrainRules    :Boolean= false

):Parcelable