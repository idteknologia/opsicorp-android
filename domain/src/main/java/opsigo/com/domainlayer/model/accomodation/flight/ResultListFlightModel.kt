package opsigo.com.domainlayer.model.accomodation.flight

import opsigo.com.domainlayer.model.booking_contact.BookingContactAdapterModel
import java.util.*
import kotlin.collections.ArrayList


class ResultListFlightModel {

    var dataSSR         = SsrModel()
    var dataFareRules   = ArrayList<FareRulesModel>()
    var dataSeat        = SeatAirlineModel()
    var originAirport   = ""
    var destinationAirport = ""
    var airline         = 0
    var facility        = ArrayList<FacilityFlightModel>()
    var notComply       = false

    var titleAirline    = ""
    var totalTransit    = 0
    var numberSeat       = ""
    var terminal        = ""
    var imgAirline      = ""
    var flightNumber    = ""
    var number          = ""
    var flightType      = ""
    var flightTypeView  = ""

    var departDate      = ""
    var departureDate   = ""
    var departTime      = ""
    var dateDeparture   = Date()

    var arrivalDate     = ""
    var arriveDate      = ""
    var arriveDateTimeView  = ""
    var arriveTime      = ""

    //var actionTime      = ""

    var duration        = ""

    var origin          = ""
    var originName      = ""
    var destination     = ""
    var destinationName = ""

    var durationView    = ""
    var durationIncludeTransit      = ""
    var durationIncludeTransitView  = ""


    var id              = ""
    var flightId        = ""

    var nameClass        = ""
    var classCode       = ""
    var classId         = ""

    var code            = ""

    var price           = 0.0
    var isAvailable     = false
    var isComply        = false
    var isConnecting    = false
    var isMultiClass    = false
    var isHolderFlight  = false

    var num             = ""
    var seq             = "0"
    var sequence        = 0
    var fareBasisCode   = ""
    var fareRuleKeys    = ""
    var isFlightArrival = false

    var transiteFlight  = ArrayList<TransiteFlight>()


}