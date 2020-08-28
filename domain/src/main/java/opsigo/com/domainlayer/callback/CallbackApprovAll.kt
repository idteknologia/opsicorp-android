package opsigo.com.domainlayer.callback

interface CallbackApprovAll {
    fun successLoad(data: String)
    fun failedLoad(message:String)
}