package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.AirlinePreferenceModel


interface CallbackString {
    fun successLoad(data: String)
    fun failedLoad(message:String)
}