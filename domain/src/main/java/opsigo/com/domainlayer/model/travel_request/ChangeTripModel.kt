package opsigo.com.domainlayer.model.travel_request

import opsigo.com.domainlayer.model.accomodation.flight.RoutesItemPertamina
import opsigo.com.domainlayer.model.create_trip_plane.UploadModel

class ChangeTripModel(
        var tripId : String = "",
        var tripCode: String = "",
        var trnNumber :String = "",
        var startDate: String = "",
        var returnDate: String = "",
        var purpose: String = "",
        var businessTripType: String = "",
        var remark: String? = null,
        var isCbt :Boolean = false,
        var isDomestic :Boolean = false,
        var isBookAfterApprove :Boolean = false,
        var isApproval :Boolean = false,
        var isPrivateTrip :Boolean = false,
        var isTripPartner :Boolean = false,
        var parterName :String = "",
        var wbsNo : String = "",
        var golper : Int = 0,
        var isChangeTrip :Boolean = false,
        var isDisableCbt :Boolean = false,
        var tripCodeOld: String = "",
        var tripIdOld: String = "",


        var routes :ArrayList<RoutesItemPertamina> = ArrayList(),
        var attactment :ArrayList<UploadModel> = ArrayList(),
)