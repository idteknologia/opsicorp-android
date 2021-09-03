package opsigo.com.domainlayer.model.summary

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ItemTrainModel(
    var isComply        :Boolean = false,
    var referenceCode   :String = "",
    var tripItemID      :String = "",
    var tripID          :String = "",
    var progressTrain   :String = "",
    var idTrain         :String = "",
    var typeView        :String = "", //one way

    var titleTrain      :String = "",
    var imageTrain      :String = "",
    var carrierNumber   :String = "",
    var fareBasisCode   :String = "",

    var seatNumber      :String = "",
    var seatName        :String = "",
    var seatText        :String = "",
    var classCode       :String = "",

    var status          :String = "",
    var pnrCode         :String = "",
    var pnrID           :String = "",
    var classTrain      :String = "",

    var oriDest         :String = "",

    //depart
    var origin           :String = "",
    var originName       :String = "",
    var stationDeparture :String = "",
    var dateDeparture    :String = "",
    var timeDeparture    :String = "",

    //arrive
    var destination     :String = "",
    var destinationName :String = "",
    var stationArrival  :String = "",
    var dateArrival     :String = "",
    var timeArrival     :String = "",

    //var date            = ""

    var price           :String = "",
    var transit         :String = "",
    var employId        :String = "",

    var isBackTrain     :Boolean = false,
    var typeTrip        :Int = 0

    ):Parcelable