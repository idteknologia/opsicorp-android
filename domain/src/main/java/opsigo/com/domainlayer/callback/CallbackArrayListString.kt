package opsigo.com.domainlayer.callback

interface CallbackArrayListString {
    fun successLoad(data: ArrayList<String>)
    fun failedLoad(message:String)
}