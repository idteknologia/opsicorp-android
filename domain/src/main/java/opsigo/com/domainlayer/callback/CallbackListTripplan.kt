package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.aprover.ApprovalModelAdapter


interface CallbackListTripplan {
    fun successLoad(approvalModel: ArrayList<ApprovalModelAdapter>)
    fun failedLoad(message:String)
}