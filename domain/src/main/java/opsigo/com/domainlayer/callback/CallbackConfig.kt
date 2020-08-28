package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.ConfigModel

interface CallbackConfig {
    fun successLoad(data: ConfigModel)
    fun failedLoad(message:String)
}