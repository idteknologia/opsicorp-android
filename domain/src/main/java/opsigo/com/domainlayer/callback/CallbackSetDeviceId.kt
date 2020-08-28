package opsigo.com.domainlayer.callback

//import khoiron.com.domainlayer.model.DeviceIdModel

interface CallbackSetDeviceId {
//    fun successLoad(data: DeviceIdModel)
    fun successLoad(isSuccess:Boolean)
    fun failedLoad(message:String)
}