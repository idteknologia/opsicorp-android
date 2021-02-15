package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.hotel.NearbyOfficeModel

interface CallbackListCompany {
    fun success(data:ArrayList<NearbyOfficeModel>)
    fun failed(message:String)
}