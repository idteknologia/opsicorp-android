package opsigo.com.domainlayer.callback

interface CallbackSetProfile {
    fun successLoad(isSuccess:Boolean)
    fun failedLoad(message:String)
}