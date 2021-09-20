package opsigo.com.domainlayer.callback

interface CallbackGetUrlFile {
    fun success(url:String)
    fun failed(string: String)
}