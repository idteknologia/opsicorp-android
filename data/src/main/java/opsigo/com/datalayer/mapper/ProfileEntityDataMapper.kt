package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.profile.*
import opsigo.com.domainlayer.model.accomodation.flight.TravelRequestApprovalModel
import opsigo.com.domainlayer.model.accomodation.flight.TravelRequestApprovalInternationalModel
import opsigo.com.domainlayer.model.signin.ApprovalModel
import opsigo.com.domainlayer.model.signin.ProfileApproverModel
import opsigo.com.domainlayer.model.signin.ProfileModel
import java.util.ArrayList

class ProfileEntityDataMapper{

    fun transform(profileEntity: ProfileNewEntity): ProfileModel {
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
        data.isApproval     = profileEntity.isApprover
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

        data.approval = mappingApproval(profileEntity.approval)

        return data
    }

    private fun mappingApproval(approval: Approval?): ApprovalModel {
        val mData = ApprovalModel()
        mData.reqEmail = approval?.requestorEmail.toString()
        mData.reqName = approval?.requestorName.toString()
        mData.reqPosName = approval?.requestorPositionName.toString()
        mData.travelRequestApproval = mappingApprovalDomestic(approval)
        return mData
    }

    private fun mappingApprovalInternational(travelRequestApprovalInternational: List<TravelRequestApprovalInternationalItem?>?): ArrayList<TravelRequestApprovalInternationalModel> {
        val mData = ArrayList<TravelRequestApprovalInternationalModel>()
        travelRequestApprovalInternational?.forEachIndexed { index, travelRequestApprovalDomesticItem ->
            val model = TravelRequestApprovalInternationalModel()
            model.employeeId = travelRequestApprovalDomesticItem?.employeeId.toString()
            model.isPjs = travelRequestApprovalDomesticItem?.isPjs == true
            model.profile = mappingProfile(travelRequestApprovalDomesticItem?.profile)
            mData.add(model)
        }
        return mData
    }

    private fun mappingApprovalDomestic(approve: Approval?): ArrayList<TravelRequestApprovalModel> {
        val mData = ArrayList<TravelRequestApprovalModel>()
        approve?.travelRequestApprovalDomestic?.forEachIndexed { index, travelRequestApprovalDomesticItem ->
            val model = TravelRequestApprovalModel()
            model.employeeId = travelRequestApprovalDomesticItem?.employeeId.toString()
            model.isPjs = travelRequestApprovalDomesticItem?.isPjs == true
            model.isDomestic = true
            model.profile = mappingProfile(travelRequestApprovalDomesticItem?.profile)
            mData.add(model)
        }
        approve?.travelRequestApprovalInternational?.forEachIndexed { index, travelRequestApprovalInternationalItem ->
            val model = TravelRequestApprovalModel()
            model.employeeId = travelRequestApprovalInternationalItem?.employeeId.toString()
            model.isPjs = travelRequestApprovalInternationalItem?.isPjs == true
            model.isDomestic = false
            model.profile = mappingProfile(travelRequestApprovalInternationalItem?.profile)
            mData.add(model)
        }
        return mData
    }

    private fun mappingProfile(profile: Profile?): ProfileApproverModel {
        val mData = ProfileApproverModel()
        mData.email = profile?.email.toString()
        mData.name = profile?.name.toString()
        mData.positionText = profile?.positionText.toString()
        mData.personId = profile?.personID.toString()
        return mData
    }
}
