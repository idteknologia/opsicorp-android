package opsigo.com.domainlayer.model.accomodation.hotel

class ResultListHotelModel {

    var imageHotelSorcut = ""
    var imageHotel   = ""
    var typeHotel    = ""
    var starRating  = ""
    var rating       = ""
    var prize        = ""
    var correlationId   = ""
    var city            = ""
    var totalAvailable  = ""
    var nameHotel    = ""
    var addressHotel = ""
    var lat          = ""
    var long         = ""
    var hotelKey     = ""
    var room         = ArrayList<SelectRoomModel>()
    var galery       = ArrayList<GaleryModel>()
    var reviews      = ArrayList<RiviewHotelModel>()
    var faciltyHotel = ArrayList<FacilityHotelModel>()
    var descriptioHotel = ""
}