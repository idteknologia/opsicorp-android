package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.profile.ProfileEntity
import opsigo.com.domainlayer.model.signin.ProfileModel

class ProfileEntityDataMapper{

    fun transform(profileEntity: ProfileEntity): ProfileModel {
        val data = ProfileModel()

        data.name        = profileEntity.fullName.toString()
        data.firstName   = profileEntity.firstName.toString()
        data.lastName    = profileEntity.lastName.toString()
        data.nameAgent   = profileEntity.companyName.toString()
        data.position    = profileEntity.jobTitleName.toString()
        data.address     = ""
        data.email       = profileEntity.email.toString()
        data.employId           = profileEntity.employeeId.toString()
        data.phone              = profileEntity.mobilePhone.toString()
        data.nationality        = profileEntity.nationality.toString()
        data.nationalityName    = profileEntity.nationalityName.toString()
        data.isApproval     = profileEntity.IsApprover
        data.idPosition     = profileEntity.jobTitleId.toString()
        data.homePhone      = profileEntity.homePhone.toString()
        data.title          = profileEntity.title.toString()


        data.idNumber     = profileEntity.idNumber.toString()
        data.employeeNik  = profileEntity.nikEmployee.toString()
        data.index        = 1
        data.mobilePhone  = profileEntity.mobilePhone.toString()
        data.jobTitleId   = profileEntity.jobTitleId.toString()
        data.type         = profileEntity.companyType.toString()
        data.companyCode  = profileEntity.companyCode.toString()
        data.birthDate    = profileEntity.birthDate.toString()
        data.identityType = if (profileEntity.identityType==null) "" else profileEntity.identityType
        data.ktp          = profileEntity.idNumber.toString()
        data.passport     = if (profileEntity.passportNumber==null) "" else profileEntity.passportNumber
        data.sim          = if (profileEntity.simNumber==null) "" else profileEntity.simNumber
        data.costCenter   = profileEntity.costCenterDefault.toString()

        return data
    }
}
