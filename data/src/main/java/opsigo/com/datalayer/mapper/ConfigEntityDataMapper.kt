package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.profile.ConfigEntity
import opsigo.com.domainlayer.model.ConfigModel

class ConfigEntityDataMapper{

    fun transform(configEntity: ConfigEntity): ConfigModel {
        val data = ConfigModel()

        data.defaultTravelAgent         = configEntity.defaultTravelAgent
        data.travelingPurposeFormType   = configEntity.travelingPurposeFormType
        data.maxParticipant             = configEntity.maxParticipant
        data.imageFrontUrl              = configEntity.imageFrontUrl
        data.imageUrl                   = configEntity.imageUrl
        data.showAllClass               = configEntity.showAllClass
        data.isBookAfterApprove         = configEntity.isBookAfterApprove
        data.showPayment                = configEntity.showPayment
        data.defaultOrigin              = configEntity.defaultOrigin
        data.defaultDestination         = configEntity.defaultDestination
        data.setCurrency                = configEntity.setCurrency
        data.isShowCreateTripOnMobile   = configEntity.isShowCreateTripOnMobile
        data.showFamilyTime             = configEntity.showFamilyTime
        data.showBusinessTrip           = configEntity.showBusinessTrip
        data.codeCompany                = configEntity.client

        data.mobileBackgroundImage    = configEntity.mobileBackgroundImage
        data.mobileBackgroundColor    = configEntity.mobileBackgroundColor
        data.mobileLogo               = configEntity.mobileLogo
        data.mobileTextLogo           = configEntity.mobileTextLogo
        data.mobileTextColorLogo      = configEntity.mobileTextColorLogo
        data.isPersonalTrip           = configEntity.isPersonalTrip
        return data
    }
}