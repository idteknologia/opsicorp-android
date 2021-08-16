package opsigo.com.domainlayer.model.accomodation.flight

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import opsigo.com.domainlayer.model.booking_contact.BookingContactAdapterModel
import java.util.*
import kotlin.collections.ArrayList


@Parcelize
class ResultListFlightModel(

    /*var dataSSR         = SsrModel()*/
    var dataFareRules   : ArrayList<FareRulesModel> = ArrayList(),
    var dataSeat        : SeatAirlineModel = SeatAirlineModel(),
    var facility        : ArrayList<FacilityFlightModel> = ArrayList(),
    var passenger       : ArrayList<BookingContactAdapterModel> = ArrayList(),
    var notComply       :Boolean = false,
    var airline         :Int = 0,
    var totalTransit    :Int = 0,

    var originAirport      :String = "",
    var destinationAirport :String = "",
    var titleAirline       :String = "",
    var numberSeat         :String = "",
    var terminal           :String = "",
    var imgAirline         :String = "",
    var flightNumber       :String = "",
    var number             :String = "",
    var flightType         :String = "",
    var flightTypeView     :String = "",

    var departDate      :String = "",
    var departureDate   :String = "",
    var departTime      :String = "",
    var arrivalDate     :String = "",
    var arriveDate      :String = "",
    var arriveTime      :String = "",
    var duration        :String = "",
    var origin          :String = "",
    var originName      :String = "",
    var destination     :String = "",
    var destinationName :String = "",
    var durationView    :String = "",
    var id              :String = "",
    var flightId        :String = "",
    var selectedClassId :String = "",
    var nameClass       :String = "",
    var classCode       :String = "",
    var classId         :String = "",
    var code            :String = "",
    var dateDeparture   :Date = Date(),

    var arriveDateTimeView          :String= "",
    var durationIncludeTransit      :String= "",
    var durationIncludeTransitView  :String= "",

    var price           :Double = 0.0,
    var isAvailable     :Boolean = false,
    var isComply        :Boolean = false,
    var isConnecting    :Boolean = false,
    var isMultiClass    :Boolean = false,
    var isHolderFlight  :Boolean = false,

    var num             :String = "",
    var seq             :String = "0",
    var fareBasisCode   :String = "",
    var fareRuleKeys    :String = "",
    var sequence        :Int= 0,
    var isFlightArrival :Boolean= false,

    var transiteFlight  : ArrayList<TransiteFlight> = ArrayList()
): Parcelable