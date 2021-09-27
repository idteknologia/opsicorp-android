package opsigo.com.domainlayer.model.my_booking

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PurchaseDetailTripFlightAndTrainModel (
    var status                  :String = "",
    var totalHourDuration       :String = "",
    var terminalDeparture       :String = "",
    var nameFlight              :String = "",
    var numberSeat              :String = "",
    var classFlight             :String = "",
    var codeFlight              :String = "",
    var timeDeparture           :String = "",
    var dateDepartute           :String = "",
    var nameAirportDepature     :String = "",
    var origin                  :String = "",
    var timeArrival             :String = "",
    var dateArrival             :String = "",
    var nameStasiunArrival      :String = "",
    var destinantion            :String = "",
    var layover                 :String = "",
    var nameAirportLayover      :String = "",
    var imageFlight             :String = "",
    var isRefund                :Boolean=false,
    var isReschedule            :Boolean=false,
    var flightNumber            :String ="",
    var isConnecting            :Boolean = false,
):Parcelable