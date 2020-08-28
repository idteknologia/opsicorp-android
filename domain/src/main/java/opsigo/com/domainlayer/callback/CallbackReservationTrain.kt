package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.accomodation.train.ReservationTrainModel

interface CallbackReservationTrain {
    fun successLoad(data: ReservationTrainModel)
    fun failedLoad(message:String)
}