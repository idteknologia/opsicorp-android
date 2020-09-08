package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.flight.airline_code.AirlineCodeCompanyModel


interface CallbackAirlinePreference {
    fun successLoad(data: AirlineCodeCompanyModel)
    fun failedLoad(message:String)
}