package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.accomodation.flight.reservation.ReservationFlightEntity
import opsigo.com.domainlayer.model.accomodation.flight.ReserveFlightModel

class ReserveFlightMapper {

    fun mapper(deserialize: ReservationFlightEntity): ReserveFlightModel {
        val reservationFlightModel = ReserveFlightModel()
        reservationFlightModel.idTrip = deserialize.tripId
        return reservationFlightModel
    }
}