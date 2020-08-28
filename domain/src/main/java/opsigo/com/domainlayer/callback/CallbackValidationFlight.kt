package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.flight.ValidationFlightModel

interface CallbackValidationFlight {
    fun successLoad(data: ValidationFlightModel)
    fun failedLoad(message:String)
}