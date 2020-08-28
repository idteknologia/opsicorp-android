package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.SetSeatMapModelTrain

interface CallbackSetSeatMapTrain {
    fun successLoad(data:SetSeatMapModelTrain)
    fun failedLoad(message:String)
}