package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.signin.CheckVersionModel

interface CallbackCheckVersion {
    fun successLoad(data : CheckVersionModel)
    fun failedLoad(message:String)
}