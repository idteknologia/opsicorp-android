package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.accomodation.train.search.SearchTrainResultEntity
import opsigo.com.domainlayer.model.accomodation.AccomodationResultModel
import opsigo.com.domainlayer.model.accomodation.train.ResultListTrainModel
import java.text.DateFormat
import java.text.SimpleDateFormat

class AccomodationResultTrainMapper {
    fun mapping(model:SearchTrainResultEntity):ArrayList<AccomodationResultModel> {
        val data  = ArrayList<AccomodationResultModel>()
//        val model = Serializer.deserialize(resultData, SearchTrainResultEntity::class.java)
        val dateFormatter: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")

        model.result.outgoingTrain.forEachIndexed { index, outgoingTrainItem ->
            outgoingTrainItem.segments.forEachIndexed { _ , segmentsItem ->
                val modelTrain = ResultListTrainModel()
                modelTrain.titleTrain      = outgoingTrainItem.trainName
                modelTrain.className       = segmentsItem.className
                modelTrain.totalSeat       = segmentsItem.seat
                modelTrain.subClass        = segmentsItem.subClass
                modelTrain.price           = segmentsItem.fare.toString().split(".")[0]
                modelTrain.nameStation     = outgoingTrainItem.destination+" "+outgoingTrainItem.origin
                modelTrain.timeArrifal     = outgoingTrainItem.arrivalTime
                modelTrain.timeDeparture   = outgoingTrainItem.departureTime
                modelTrain.dateArrivalString   = outgoingTrainItem.arrivalDate
                modelTrain.dateDepartureString = outgoingTrainItem.departureDate
                modelTrain.duration        = outgoingTrainItem.duration
                modelTrain.fareBaseCode    = segmentsItem.fareBasisCode
                modelTrain.dateArrival     = dateFormatter.parse(outgoingTrainItem.arrivalDate+" "+outgoingTrainItem.arrivalTime)
                modelTrain.dateDeparture   = dateFormatter.parse(outgoingTrainItem.departureDate+" "+outgoingTrainItem.departureTime)

                modelTrain.notComply       = segmentsItem.isComply
                modelTrain.classKey        = segmentsItem.classKey
                modelTrain.carrierNumber   = outgoingTrainItem.carrierNumber
                modelTrain.origin          = outgoingTrainItem.origin
                modelTrain.destination     = outgoingTrainItem.destination
                modelTrain.journeyCode     = outgoingTrainItem.journeyCode
                modelTrain.trainName       = outgoingTrainItem.trainName
                modelTrain.durationTime    = outgoingTrainItem.durationTime

                val mData = AccomodationResultModel()
                mData.typeLayout = 1
                mData.listTrainModel   = modelTrain
                data.add(mData)
            }
        }
        return data
    }
}