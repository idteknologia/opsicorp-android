package opsigo.com.domainlayer.model.accomodation.train

import opsigo.com.domainlayer.model.accomodation.ReasonCodeModel

class ConfirmationTrainModel {

    var pnr_code      = ""
    var status        = ""
    var date_arrival_depart = ""
    var title_train   = ""

    var class_type    = ""
    var number_sheet  = ""

    var timeDeparture = ""
    var dateDeparture = ""
    var name_departure     = ""
    var name_station_departure =""
    var line_total_duration =""

    var time_arrival = ""
    var date_arrival = ""

    var name_arrival = ""
    var name_station_arrival = ""

    var total_passager = ""
    var total_prize    = ""

    var notcomply      = false


    var dataReasonCode = ReasonCodeModel()

}