package opsigo.com.datalayer.datanetwork.dummy.bisni_strip

import opsigo.com.domainlayer.model.create_trip_plane.ParticipantPertamina
import opsigo.com.domainlayer.model.create_trip_plane.RoutesItinerary
import opsigo.com.domainlayer.model.create_trip_plane.UploadModel

class DataBisnisTripModel {

    var idCountry   = ""
    var idPurphose  = ""
    var id = ""
    var namePusrpose = ""
    var wbsNumber    = ""
    var nameActivity = ""
    var tripPartnerName = ""
    var nameDestination = ""
    var origin = ""
    var destination = ""
    var startDate   = ""
    var endDate     = ""
    var notes       = ""
    var golper      = 0
    var routes      = ArrayList<RoutesItinerary>()
    var image       = ArrayList<UploadModel>()
    var participant = ArrayList<ParticipantPertamina>()
    var statusCreateTrip = ""
    var event       = ""
    var tripcode    = ""
    var budget      = ""
    var costCenter  = ""
    var dateCreated = ""
    var isCbt = false
    var isInternational = false
    var isWbs = false
    var isTripPartner = false

}