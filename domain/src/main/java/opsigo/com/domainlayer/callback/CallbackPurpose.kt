package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.PurposeModel

interface CallbackPurpose {
    fun successLoad(data: ArrayList<PurposeModel>)
    fun failedLoad(message:String)
}