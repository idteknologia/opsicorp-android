package com.mobile.travelaja.module.item_custom.galery

import opsigo.com.domainlayer.model.accomodation.hotel.GaleryModel

interface CallbackGalery {
    fun result(data:GaleryModel)
    fun cancelled()
}