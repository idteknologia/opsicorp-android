package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.AccomodationResultModel

interface CallbackResultSearchFlight {
    fun success(mData: ArrayList<AccomodationResultModel>)
    fun failed(error:String)
}