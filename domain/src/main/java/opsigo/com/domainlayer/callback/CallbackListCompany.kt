package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.hotel.ListCompanyModel

interface CallbackListCompany {
    fun success(data:ArrayList<ListCompanyModel>)
    fun failed(message:String)
}