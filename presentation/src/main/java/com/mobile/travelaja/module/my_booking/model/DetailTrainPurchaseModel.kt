package com.mobile.travelaja.module.my_booking.model

import opsigo.com.domainlayer.model.my_booking.ImportanPreProductInfoModel
import opsigo.com.domainlayer.model.my_booking.PassangerPurchaseModel

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