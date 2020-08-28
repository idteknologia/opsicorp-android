package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.flight.ProgressFlightModel


interface CallbackProgressFlight {
    fun success(progressData: ProgressFlightModel)
    fun failed(message:String)
}