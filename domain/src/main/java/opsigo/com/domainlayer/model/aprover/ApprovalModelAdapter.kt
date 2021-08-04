package opsigo.com.domainlayer.model.aprover

import opsigo.com.domainlayer.model.accomodation.flight.RoutesItemPertamina

class ApprovalModelAdapter {

    var id           = ""
    var status       = ""
    var header       = ""
    var title        = ""
    var tripCode     = ""
    var start_date   = ""
    var end_date     = ""
    var time         = ""
    var timeExperied = ""
    var destination  = ""
    var selected     = false

    var isApproval  = false
    var isParticipant = false

    var participant  = ArrayList<ParticipantModelDomain>()
    var listApproval  = ArrayList<ParticipantModelDomain>()
    var routes = ArrayList<RoutesItemPertamina>()

}