package opsigo.com.domainlayer.model.create_trip_plane.save_as_draft

import opsigo.com.domainlayer.model.accomodation.flight.RouteMultiCityModel
import opsigo.com.domainlayer.model.summary.TripAttachmentItemModel

class SuccessCreateTripPlaneModel {
    var purpose     = ""
    var idTripPlane = ""
    var status      = ""
    var tripCode    = ""
    var createDate  = ""
    var createDateView    = ""
    var timeExpired       = ""
    var destinationName   = ""
    var destinationId     = ""
    var originId          = ""
    var originName        = ""
    var startDate    = ""
    var endDate      = ""
    var remark       = ""
    var buggetId     = ""
    var costCenter   = ""

    var businessTripType = ""
    var wbsNo       = ""
    var isDomestik  = false
    var isBookAfterApprove = false
    var isPrivateTrip = false
    var golper      = 0

    var route        = ArrayList<RouteMultiCityModel>()
    var attachment   = ArrayList<TripAttachmentItemModel>()
}