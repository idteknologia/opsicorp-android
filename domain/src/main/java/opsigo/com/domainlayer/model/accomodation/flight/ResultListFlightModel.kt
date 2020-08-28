package opsigo.com.domainlayer.model.accomodation.flight


class ResultListFlightModel {

    var dataSSR         = SsrModel()
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
    var isFlightArrival = false

    var transiteFlight  = ArrayList<TransiteFlight>()


}