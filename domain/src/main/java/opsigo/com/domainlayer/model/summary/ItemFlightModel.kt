package opsigo.com.domainlayer.model.summary

class ItemFlightModel{

    var type        = 0
    var typeView    = ""
    var imageFlight = ""

    var originDestination = ""
    var nextDestination = ""
    var bookingDate = ""

    var num = 0
    var seq = 0

    var status      = ""
    var pnrCode     = ""
    var pnrId       = ""
    var classFlight = ""
    var subClass    = ""
    var flightNumber   = ""
    var seatNumber  = ""
    var duration    = ""
    var terminal    = ""


    var airlineName = ""
    //var number      = ""

    //depart
    var origin          = ""
    var originName      = ""
    var airportDeparture = ""
    var dateDeparture   = ""
    var timeDeparture   = ""

    //arrive
    var destination     = ""
    var destinationName = ""
    var airportArrival  = ""
    var dateArrival     = ""
    var timeArrival     = ""

    var price       = ""
    var priceItem : ArrayList<PaymentsItemModel> = ArrayList()
    var employId    = ""
    var idFlight    = ""
    var progressFLight = ""
    var tripItemId  = ""
    var flightSegmentItem : ArrayList<FlightSegmentItem> = ArrayList()

    var isComply = false

}