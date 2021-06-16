package com.mobile.travelaja.module.cart.model

import opsigo.com.domainlayer.model.summary.FlightSegmentItem
import opsigo.com.domainlayer.model.summary.PaymentsItemModel

class ItemCardFlightModel{

    var stationOrigin = ""
    var imageFlight = ""
    var status      = ""
    var pnrCode     = ""

    var flightNumber    = ""
    var subClass        = ""
    var typeFlight      = 0
    var duration        = ""
    var terminal        = ""

    var titleFlight = ""
    var numberSheet = ""

    //departure
    var departure              = ""
    var airportDeparture    = ""
    var dateDeparture       = ""
    var timeDeparture       = ""

    //arrival
    var arrival         = ""
    var airportArrival  = ""
    var dateArrival         = ""
    var timeArrival         = ""

    var price       = ""
    var priceItem : ArrayList<PaymentsItemModel> = ArrayList()
    var flightSegmentItem : ArrayList<FlightSegmentItem> = ArrayList()

    var isComply    = false

    var classFlight     = ""
    var codeFlight      = ""
    var departureFlight = ""
    var arrivalFlight  = ""
    var stationDestination  = ""

    var idFlight        = ""
    var tripId          = ""
    var pnrId           = ""
    var progressFlight  = ""


}