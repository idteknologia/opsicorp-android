package opsigo.com.domainlayer.model.accomodation.flight

import opsigo.com.domainlayer.model.signin.ProfileApproverModel

class TravelRequestApprovalDomesticModel {
    var layer = Int
    var employeeId = ""
    var isPjs = false
    var isManualInput = false

    var profile = ProfileApproverModel()
}