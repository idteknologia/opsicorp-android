package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.signin.ProfileModel

interface CallbackProfile {
    fun successLoad(data: ProfileModel)
    fun failedLoad(message:String)
}