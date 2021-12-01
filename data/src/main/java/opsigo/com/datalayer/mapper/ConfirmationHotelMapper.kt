package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.accomodation.hotel.confirmation.ConfirmationHotelEntity
import opsigo.com.domainlayer.model.accomodation.hotel.ConfirmationHotelModel

class ConfirmationHotelMapper {
    fun mapping(confirmation: ConfirmationHotelEntity):ConfirmationHotelModel{
        val data = ConfirmationHotelModel()

        data.idConfirmation = confirmation.confirmation.confirmationId.toString()
        data.correlationId  = confirmation.correlationId.toString()
        if (confirmation.confirmation.cancelPolicySummaries==null)
            data.cancellPolicyHotel = ArrayList()
        else
            data.cancellPolicyHotel.addAll(confirmation.confirmation.cancelPolicySummaries)
        data.isFullCharge   = confirmation.confirmation.isFullCharge
        data.isGuarantedBooking = confirmation.confirmation.isGuaranteedBooking
        data.available      = confirmation.confirmation.available
        data.area           = confirmation.confirmation.cityName.toString()
        data.roomSelector   = confirmation.confirmation.roomSelector.toString()
        data.totalPrice     = confirmation.confirmation.totalPrice.toString()
        data.mapUri         = "https://www.google.com/maps/search/?api=1&query=${confirmation.confirmation.hotelName},${confirmation.confirmation.address}"
        return data
    }
}