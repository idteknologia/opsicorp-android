package opsigo.com.domainlayer.model.accomodation.flight

import opsigo.com.domainlayer.model.accomodation.ReasonCodeModel

class ConfirmationFlightModel {

    var date_arrival_deaprture = ""
    var title_flight  = ""
    var img_airline   = ""

    var class_type    = ""
    var number_sheet  = ""

    var timeDeparture = ""
    var dateDeparture = ""
    var originDisplay = ""
    var departureDisplay = ""
    var terminal      = ""

    var name_stationDeparture =""
    var line_total_duration =""

    var time_arrival = ""
    var date_arrival = ""

    var total_passager = ""
    var total_prize    = ""

    var notcomply      = false


    var dataReasonCode = ReasonCodeModel()

}