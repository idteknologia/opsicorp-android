package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.AirlinePreferenceModel


interface CallbackAirlinePreference {
    fun successLoad(data: AirlinePreferenceModel)
    fun failedLoad(message:String)
}