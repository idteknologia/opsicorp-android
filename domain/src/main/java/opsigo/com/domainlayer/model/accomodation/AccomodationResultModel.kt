package opsigo.com.domainlayer.model.accomodation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel
import opsigo.com.domainlayer.model.accomodation.hotel.ResultListHotelModel
import opsigo.com.domainlayer.model.accomodation.train.ResultListTrainModel

@Parcelize
class AccomodationResultModel(
    var typeLayout      :Int = 0,
    var listFlightModel :ResultListFlightModel= ResultListFlightModel(),
    var listTrainModel  :ResultListTrainModel = ResultListTrainModel(),
    var listHotelModel  :ResultListHotelModel = ResultListHotelModel()
): Parcelable