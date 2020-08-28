package opsigo.com.domainlayer.callback

interface CallbackCancelTripplan {
    fun successLoad(boolean: Boolean)
    fun failedLoad(message:String)
}