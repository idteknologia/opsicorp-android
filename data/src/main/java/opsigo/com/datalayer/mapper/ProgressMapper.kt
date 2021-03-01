package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.accomodation.flight.ProgressFlightEnitity
import opsigo.com.datalayer.model.accomodation.train.ProgressTrainEnitity
import opsigo.com.domainlayer.model.accomodation.flight.ProgressFlightModel
import opsigo.com.domainlayer.model.accomodation.train.ProgressTrainModel

class ProgressMapper {
    fun mapperTrain(deserialize: ProgressTrainEnitity): ProgressTrainModel {
        val model = ProgressTrainModel()
        model.idTripPlan    = deserialize.pnrId
        model.progress      = deserialize.num
        model.idTrain       = deserialize.trainId
        if (deserialize.num==100.00){
            model.referanceCode = deserialize.tripTrain?.referenceCode!!
        }

        return model
    }

//    fun mapperFlight(deserialize: ProgressTrainEnitity): ProgressFlightModel {
    fun mapperFlight(deserialize: ProgressFlightEnitity): ProgressFlightModel {
        val model = ProgressFlightModel()
        model.pnrID         = deserialize.pnrId
        model.progress      = deserialize.num
        model.idFlight      = deserialize.flightId
        if (deserialize.num==100.00){
            model.referanceCode = deserialize.tripFlight.linkReference.toString()
        }

        return model
    }
}