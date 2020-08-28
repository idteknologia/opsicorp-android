package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.flight.SsrModel

interface CallbackGetSsr {
    fun success(data:SsrModel)
    fun failed(string: String)
}