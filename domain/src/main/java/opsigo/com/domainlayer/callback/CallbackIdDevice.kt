package opsigo.com.domainlayer.callback

interface CallbackIdDevice {
    fun success(boolean: Boolean)
    fun failed(string:String)
}