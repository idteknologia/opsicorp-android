package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.ProfileEntity
import opsigo.com.domainlayer.model.signin.ProfileModel

class ProfileEntityDataMapper{

    fun transform(profileEntity: ProfileEntity): ProfileModel {
        val data = ProfileModel()

        data.name        = profileEntity.fullName
        data.firstName   = profileEntity.firstName
        data.lastName    = profileEntity.lastName
        data.nameAgent   = profileEntity.companyName
        data.position    = profileEntity.jobTitleName
        data.address     = ""
        data.email       = profileEntity.email
        data.employId           = profileEntity.employeeId
        data.phone              = profileEntity.mobilePhone
        data.nationality        = profileEntity.nationality
        data.nationalityName    = profileEntity.nationalityName
        data.isApproval     = profileEntity.IsApprover
        data.idPosition     = profileEntity.jobTitleId
        data.homePhone      = profileEntity.homePhone
        data.title          = profileEntity.title


        data.idNumber     = profileEntity.idNumber
        data.employeeNik  = profileEntity.nikEmployee
        data.index        = 1
        data.mobilePhone  = profileEntity.mobilePhone
        data.jobTitleId   = profileEntity.jobTitleId
        data.type         = profileEntity.companyType
        data.companyCode  = profileEntity.companyCode
        data.birthDate    = profileEntity.birthDate
        data.identityType = if (profileEntity.identityType==null) "" else profileEntity.identityType
        data.ktp          = profileEntity.idNumber
        data.passport     = if (profileEntity.passportNumber==null) "" else profileEntity.passportNumber
        data.sim          = if (profileEntity.simNumber==null) "" else profileEntity.simNumber

        return data
    }
}
