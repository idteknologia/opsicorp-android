package opsigo.com.domainlayer.model.signin

import opsigo.com.domainlayer.model.accomodation.flight.TravelRequestApprovalModel

class ApprovalModel {
    var reqPosId = Int
    var reqPerId = Int
    var reqPosName = ""
    var reqName = ""
    var reqEmail = ""
    var enableInpAppDom = false
    var enableInpAppInt = false

    var travelRequestApproval = ArrayList<TravelRequestApprovalModel>()

}