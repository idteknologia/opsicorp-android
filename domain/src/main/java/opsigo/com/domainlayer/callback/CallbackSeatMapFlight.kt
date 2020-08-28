package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.flight.SeatAirlineModel
import opsigo.com.domainlayer.model.accomodation.train.CabinModel

interface CallbackSeatMapFlight {
    fun success(data:ArrayList<SeatAirlineModel>)
    fun failed(errorMessage: String)
}