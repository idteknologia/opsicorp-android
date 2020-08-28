package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.AccomodationResultModel

interface CallbackResultSearchTrain {
    fun success(mData: ArrayList<AccomodationResultModel>)
    fun failed(error:String)
}