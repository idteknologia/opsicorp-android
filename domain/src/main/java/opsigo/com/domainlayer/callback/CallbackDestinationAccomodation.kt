package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.DestinationAccomodationModel

interface CallbackDestinationAccomodation {
    fun success(data: ArrayList<DestinationAccomodationModel>)
    fun failed(error: String)
}