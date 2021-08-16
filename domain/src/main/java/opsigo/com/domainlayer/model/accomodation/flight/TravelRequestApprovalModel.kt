package opsigo.com.domainlayer.model.accomodation.flight

import opsigo.com.domainlayer.model.signin.ProfileApproverModel

class TravelRequestApprovalModel {
    var layer = Int
    var employeeId = ""
    var isPjs = false
    var isManualInput = false
    var isDomestic = false

    var profile = ProfileApproverModel()
}