package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.UpcomingFlightModel

interface CallbackUpcomingFlight {
    fun successLoad(data: UpcomingFlightModel)
    fun failedLoad(message:String)
}