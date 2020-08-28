package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.CostCenterModel

interface CallbackCostCenter {
    fun successLoad(approvalModel: ArrayList<CostCenterModel>)
    fun failedLoad(message:String)
}