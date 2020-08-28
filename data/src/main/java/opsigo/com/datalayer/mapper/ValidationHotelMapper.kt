package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.accomodation.hotel.validation.ValidationHotelEntity
import opsigo.com.domainlayer.model.accomodation.hotel.ValidationHotelModel

class ValidationHotelMapper {
    fun mapping(responseString: String?): ValidationHotelModel {

        val response = Serializer.deserialize(responseString!!,ValidationHotelEntity::class.java)
        val data = ValidationHotelModel()
        data.notcomply         = response.isViolated
        data.correlationId     = response.bookingResult.hotel.correlationId.toString()
        data.confirmationId    = response.bookingResult.hotel.confirmationId.toString()
        data.destinationCity   = response.bookingResult.hotel.city.toString()
        data.roomKey           = response.bookingResult.hotel.roomSelector.toString()
        data.breakfast         = response.bookingResult.hotel.roomService.toString()
        data.typeBed           = response.bookingResult.hotel.roomType.toString()
        data.remark            = response.bookingResult.hotel.remarkHotel.toString()
        data.isViolatedRules   = response.bookingResult.hotel.isViolatedHotelRules
        data.causeViolatedRules = response.bookingResult.hotel.causeViolatedRules.toString()
        data.travelProfileId   = ""

        return data
    }
}