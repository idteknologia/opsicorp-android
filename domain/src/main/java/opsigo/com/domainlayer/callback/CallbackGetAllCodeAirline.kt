package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.flight.CodeAirLineModel

interface CallbackGetAllCodeAirline {
    fun success(data:ArrayList<CodeAirLineModel>)
    fun failed(string: String)
}