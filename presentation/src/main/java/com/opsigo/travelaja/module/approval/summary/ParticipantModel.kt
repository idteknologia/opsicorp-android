package com.opsigo.travelaja.module.approval.summary

import opsigo.com.domainlayer.model.aprover.ParticipantModelDomain

class ParticipantModel {
    var isApproval     = false
    var jobtitle       = ""
    var name           = ""
    var costCenterCode = ""
    var costCenterName = ""
    var budgetCode     = ""
    var budgetName     = ""
    var status         = ""
    var numItems       = ""
    var idParticipant  = ""
    var employId       = ""
    var imgUrl         = ""
    var approval       = ArrayList<ParticipantModelDomain>()
}