package opsigo.com.domainlayer.model.accomodation.flight

import opsigo.com.domainlayer.model.accomodation.ReasonCodeModel

class ConfirmationFlightModel {

    var date_arrival_deaprture = ""
    var title_flight  = ""
    var img_airline   = ""
    var pnr_code      = ""
    var status        = ""

    var flight_type   = ""
    var class_type    = ""
    var number_sheet  = ""
    var airlineNumber = ""

    var timeDeparture = ""
    var dateDeparture = ""
    var originDisplay = ""
    var departureDisplay = ""
    var terminal      = ""

    var name_stationDeparture =""
    var name_stationArrival = ""
    var line_total_duration   =""

    var depatureAirportName = ""
    var arrivalAirportName  = ""

    var time_arrival = ""
    var date_arrival = ""

    var total_passager = ""
    var total_prize    = ""

    var notcomply      = false


    var dataReasonCode = ReasonCodeModel()

}