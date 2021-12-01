package opsigo.com.domainlayer.model.accomodation.hotel

class ConfirmationHotelModel {
    var idConfirmation = ""
    var roomSelector   = ""
    var mapUri         = ""
    var isGuarantedBooking = false
    var isFullCharge   = false
    var correlationId  = ""
    var area           = ""
    var totalPrice     = ""
    var available      = false
    var cancellPolicyHotel = ArrayList<String>()
}