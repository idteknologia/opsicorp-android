package com.opsigo.travelaja.module.my_booking.model

class DetailTrainPurchaseModel {
    var timeArrival             = ""
    var dateArrival             = ""
    var nameTrain               = ""
    var numberSeat              = ""
    var classTrain              = ""
    var timeDeparture           = ""
    var dateDepartute           = ""
    var nameStasiunDepature     = ""
    var addressStationDeparture = ""
    var nameStasiunArrival      = ""
    var addressStationArrival   = ""
    var totalPrize              = ""

    var importan  = ArrayList<ImportanPreProductInfoModel>()
    var passanger = ArrayList<PassangerPurchaseModel>()

}