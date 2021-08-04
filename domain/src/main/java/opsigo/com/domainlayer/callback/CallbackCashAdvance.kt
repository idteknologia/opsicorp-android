package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.travel_request.CashAdvanceModel

interface CallbackCashAdvance {
    fun successLoad(data: CashAdvanceModel)
    fun failedLoad(message:String)
}