package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.accomodation.flight.validation.ValidationFlightEntity
import opsigo.com.domainlayer.model.accomodation.flight.ValidationFlightModel

class ValidationFlightMapper {

    fun mapper(data: ValidationFlightEntity): ValidationFlightModel {
        val mData = ValidationFlightModel()
        mData.isSecurity               = data.isSecurity
        mData.isSecondary              = data.isSecondary
        mData.isResticted              = data.restrictedDestination
        mData.advanceBooking           = data.isAdvanceBooking
        mData.isLowerFare              = data.isLowestFare
        mData.isAirlinePolicy          = data.isAirlinePolicy
        mData.reasonCode               = if (data.bookingResult.reasonCode==null) "" else data.bookingResult.reasonCode
        return mData
    }
}