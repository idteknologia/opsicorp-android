package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.ReasonCodeModel

interface CallbackReasonCode {
    fun success(reasonCodeModel: ArrayList<ReasonCodeModel>)
    fun failed(string: String)
}