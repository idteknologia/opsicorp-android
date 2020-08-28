package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.accomodation.train.reservation.BookingTrainEntity
import opsigo.com.domainlayer.model.accomodation.train.ReservationTrainModel

class ReservationMapper {

    fun mapper(deserialize: BookingTrainEntity): ReservationTrainModel {
        val reservationTrainModel = ReservationTrainModel()
        reservationTrainModel.idTrip = deserialize.tripId
        return reservationTrainModel
    }
}