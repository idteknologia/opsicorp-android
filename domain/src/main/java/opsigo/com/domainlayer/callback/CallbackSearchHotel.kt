package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.AccomodationResultModel


interface CallbackSearchHotel {
    fun success(mData:ArrayList<AccomodationResultModel>)
    fun failed(errorMessage: String)
}