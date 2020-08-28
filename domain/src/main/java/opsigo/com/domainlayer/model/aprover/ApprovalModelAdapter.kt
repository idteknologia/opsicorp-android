package opsigo.com.domainlayer.model.aprover

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

}