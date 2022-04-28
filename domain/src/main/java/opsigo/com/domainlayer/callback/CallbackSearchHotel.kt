package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.AccomodationResultModel


interface CallbackSearchHotel {
    fun success(mData:ArrayList<AccomodationResultModel>,areas:ArrayList<String>,maxpage:Int,totalPage:Int)
    fun failed(errorMessage: String)
}