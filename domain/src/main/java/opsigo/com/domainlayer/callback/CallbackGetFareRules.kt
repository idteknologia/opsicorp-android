package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel

interface CallbackGetFareRules {
    fun success(data: ResultListFlightModel)
    fun failed(string: String)
}