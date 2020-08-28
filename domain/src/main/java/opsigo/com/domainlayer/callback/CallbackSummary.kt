package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.summary.SummaryModel

interface CallbackSummary {
    fun successLoad(summaryModel: SummaryModel)
    fun failedLoad(message:String)
}