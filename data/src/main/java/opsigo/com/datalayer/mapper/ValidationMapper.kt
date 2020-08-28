package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.accomodation.train.validation.ValidationTrainEntity
import opsigo.com.domainlayer.model.accomodation.train.ValidationTrainModel

class ValidationMapper {

    fun mapper(data:ValidationTrainEntity): ValidationTrainModel {
        val mData = ValidationTrainModel()
        mData.isSecuritySensitivity    = data.isSecurity.toString()
        mData.descSecuritySensitivity  = if (data.bookingResult.segments[0].descSecuritySensitivity==null) "" else data.bookingResult.segments[0].descSecuritySensitivity
        mData.isViolatedTrainRules     = data.isViolated
        mData.causeViolatedTrainRules  = data.bookingResult.causeViolatedTrainRules
        return mData
    }
}