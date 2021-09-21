package opsigo.com.domainlayer.usecase

import opsigo.com.domainlayer.callback.CallbackApprovAll
import opsigo.com.domainlayer.callback.CallbackString
import opsigo.com.domainlayer.callback.CallbackTotalApproval

interface ApprovalRepository {
    fun getDataTotalApproval(token:String,
                             size:String, index:String, orderBy:String,
                             direction:String, tripDateFrom:String, tripDateTo:String, callback: CallbackTotalApproval)

    fun approveAll(token: String,data:HashMap<Any,Any>,callback:CallbackApprovAll)
    fun approveItem(token: String,data:HashMap<Any,Any>,callback:CallbackApprovAll)
    fun approvePerPax(token: String,data:HashMap<Any,Any>,callback:CallbackApprovAll)
    fun refund(token: String,data:HashMap<Any,Any>,callback: CallbackString)
    fun reschedule(token: String,data:HashMap<Any,Any>,callback:CallbackString)
}