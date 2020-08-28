package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.aprover.TotalApprovalModel

interface CallbackTotalApproval {
    fun successLoad(data: ArrayList<TotalApprovalModel>)
    fun failedLoad(message:String)
}