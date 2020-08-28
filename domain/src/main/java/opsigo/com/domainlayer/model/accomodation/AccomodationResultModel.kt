package opsigo.com.domainlayer.model.accomodation

import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel
import opsigo.com.domainlayer.model.accomodation.hotel.ResultListHotelModel
import opsigo.com.domainlayer.model.accomodation.train.ResultListTrainModel

class AccomodationResultModel {
    var typeLayout  = 0
    var listFlightModel = ResultListFlightModel()
    var listTrainModel  = ResultListTrainModel()
    var listHotelModel  = ResultListHotelModel()
}