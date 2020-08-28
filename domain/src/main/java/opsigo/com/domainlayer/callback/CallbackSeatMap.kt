package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.train.CabinModel

interface CallbackSeatMap {
    fun success(data:ArrayList<CabinModel>)
    fun failed(errorMessage: String)
}