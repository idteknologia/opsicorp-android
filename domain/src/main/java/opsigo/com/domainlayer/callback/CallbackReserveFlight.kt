package opsigo.com.domainlayer.callback

//import opsigo.com.domainlayer.model.accomodation.train.ReservationTrainModel
import opsigo.com.domainlayer.model.accomodation.flight.ReserveFlightModel

interface CallbackReserveFlight {
    fun successLoad(data: ReserveFlightModel)
    fun failedLoad(message:String)
}