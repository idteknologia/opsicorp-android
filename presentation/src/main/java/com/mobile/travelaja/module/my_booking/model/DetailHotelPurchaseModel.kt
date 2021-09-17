package com.mobile.travelaja.module.my_booking.model

class DetailHotelPurchaseModel {
    var id                    = ""
    var code                  = ""
    var nameHotel             = ""
    var rating                = ""
    var locationHotel         = ""
    var dateCheckIn           = ""
    var timeCheckIn           = ""
    var totalNight            = ""
    var dateCheckOut          = ""
    var timeCheckOut          = ""
    var nameBookingContact    = ""
    var addressHotel          = ""
    var latitude              = ""
    var longitude             = ""
    var facility              = ArrayList<FacilityRoomModel>()
    var cancellationPolicy    = ArrayList<String>()
    var remark                = ArrayList<String>()
    var guest                 = ArrayList<String>()
    var totalPrice            = ""

    class FacilityRoomModel {

        var image             = ""
        var nameFacility      = ""
    }
}
