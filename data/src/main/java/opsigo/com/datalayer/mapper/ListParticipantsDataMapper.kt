package opsigo.com.datalayer.mapper

import android.util.Log
import opsigo.com.datalayer.model.SummaryEntity
import opsigo.com.datalayer.model.accomodation.flight.SegmentFlightEntity
import opsigo.com.domainlayer.model.summary.*

class ListParticipantsDataMapper {

    fun mapFrom(mData: SummaryEntity): List<TripParticipantsItemModel> {

        val from = mData.tripParticipants

        from.let {

            val tpModelList = it.map { data ->

                val dataFlightModel = ArrayList<ItemFlightModel>()
                val dataTrainModel  = ArrayList<ItemTrainModel>()
                val dataHotelModel  = ArrayList<ItemHotelModel>()

                if (mData.flights.isNotEmpty()){
                    mData.flights.filter { it.employeeId == data.employeeId }.first().tripFlights.forEachIndexed { index, tripFlightsItem ->

                        var num = 0
                        tripFlightsItem.segments.forEachIndexed { _, segmentFlightEntity ->

                            if (num==segmentFlightEntity.num){
                                val dataFlight  = ItemFlightModel()

                                dataFlight.typeView         = tripFlightsItem.flightTypeView
                                dataFlight.type             = tripFlightsItem.flightType

                                dataFlight.imageFlight      = segmentFlightEntity.airlineImageUrl//"https://i.ibb.co/C0XzT6K/sriwijaya.png"
                                //dataFlight.carrier          = tripFlightsItem.carrier

                                dataFlight.originDeatination = segmentFlightEntity.originName+" - "+segmentFlightEntity.destinationName //"Jakarta (CGK) - Yogyakarta (JOG)"

                                //dataFlight.idFlight         = segmentFlightEntity.id//"Sriwijaya"
                                dataFlight.idFlight         = segmentFlightEntity.tripFlightId//"Sriwijaya"
                                dataFlight.airlineName      = tripFlightsItem.airlineView//"Sriwijaya"
                                dataFlight.flightNumber     = segmentFlightEntity.flightNumber//"SJ-0412"
                                dataFlight.seatNumber       = segmentFlightEntity.flightNumber

                                //if (segmentsItem.classCode==null) "" else segmentsItem.classCode
                                dataFlight.status           = if (tripFlightsItem.status==null) "" else tripFlightsItem.status
                                dataFlight.classFlight      = segmentFlightEntity.category + " Class"
                                dataFlight.subClass         = "Subclass-" + segmentFlightEntity.classCode

                                dataFlight.num              = segmentFlightEntity.num
                                dataFlight.seq              = segmentFlightEntity.seq

                                //departure
                                dataFlight.origin           = segmentFlightEntity.origin
                                dataFlight.originName       = segmentFlightEntity.cityOrigin
                                dataFlight.airportDeparture = segmentFlightEntity.airportOrigin
                                dataFlight.dateDeparture    = segmentFlightEntity.departDate

                                dataFlight.timeDeparture    = segmentFlightEntity.departTime

                                //arrival
                                dataFlight.destination      = segmentFlightEntity.destination
                                dataFlight.destinationName  = segmentFlightEntity.cityDestination
                                dataFlight.airportArrival   = segmentFlightEntity.airportDestination
                                dataFlight.dateArrival      = segmentFlightEntity.arriveDate
                                dataFlight.timeArrival      = segmentFlightEntity.arriveTime

                                dataFlight.pnrCode          = if (tripFlightsItem.pnrCode==null) "" else tripFlightsItem.pnrCode
                                dataFlight.pnrId            = tripFlightsItem.pnrId.toString()

                                dataFlight.isComply         = segmentFlightEntity.isComply

//                                dataFlight.price          = "IDR 400.000/pax"
                                dataFlight.price            = if (tripFlightsItem.amount==null) "0" else tripFlightsItem.amount

                                dataFlight.flightSegmentItem.add(segmentMapperData(segmentFlightEntity))

                                dataFlight.progressFLight    = if (tripFlightsItem.jobProgress.progress==null) "" else tripFlightsItem.jobProgress.progress

                                dataFlightModel.add(dataFlight)
                                num++

                            }
                            else{
                                dataFlightModel[num-1].flightSegmentItem.add(segmentMapperData(segmentFlightEntity))
                            }

                        }

                    }
                }

                if (mData.trains.isNotEmpty()){
                    if (mData.trains.filter { it.employeeId == data.employeeId }.isNotEmpty()){
                        mData.trains.filter { it.employeeId == data.employeeId }.forEachIndexed { index, tripTrainEntity ->
                            tripTrainEntity.tripTrains.forEachIndexed { index, tripTrainsItem ->
                                tripTrainsItem.segments.forEachIndexed { index, segmentsItem ->
                                    val dataTrain  = ItemTrainModel()

                                    dataTrain.typeView      = tripTrainsItem.flightTypeView

                                    dataTrain.imageTrain    = if(segmentsItem.kaiImageUrl==null) "https://i.ibb.co/5Wv9ksW/Screen-Shot-2019-08-27-at-13-34-15.png" else segmentsItem.kaiImageUrl
                                    dataTrain.titleTrain    = segmentsItem.trainName//"Argo Parahyangan"
                                    dataTrain.carrierNumber = if (segmentsItem.currierNumber==null) "" else segmentsItem.currierNumber//"K02"
                                    dataTrain.classTrain    = segmentsItem.category + " (" + segmentsItem.classCode + ")"//"Executive (J)"

                                    dataTrain.seatNumber    = if (tripTrainsItem.passengers.get(0).seatNumber==null) "" else tripTrainsItem.passengers.get(0).seatNumber
                                    dataTrain.seatName      = if (tripTrainsItem.passengers.get(0).seatName==null) "" else tripTrainsItem.passengers.get(0).seatName
                                    dataTrain.seatText      = if (tripTrainsItem.passengers.get(0).seatText==null) "" else tripTrainsItem.passengers.get(0).seatText
                                    dataTrain.classCode     = if (segmentsItem.classCode==null) "" else segmentsItem.classCode
                                    dataTrain.fareBasisCode = if (segmentsItem.fareBasisCode==null) "" else segmentsItem.fareBasisCode

                                    dataTrain.oriDest       = segmentsItem.originName+" - "+segmentsItem.destinationName

                                    //depart
                                    dataTrain.origin        = segmentsItem.origin
                                    dataTrain.originName    = segmentsItem.cityOrigin
                                    dataTrain.stationDeparture  = segmentsItem.airportOrigin
                                    dataTrain.dateDeparture = segmentsItem.departDate
                                    dataTrain.timeDeparture = segmentsItem.departTime

                                    //arrival
                                    dataTrain.destination   = segmentsItem.destination
                                    dataTrain.destinationName   = segmentsItem.cityDestination
                                    dataTrain.stationArrival    = segmentsItem.airportDestination
                                    dataTrain.dateArrival   = segmentsItem.arriveDate
                                    dataTrain.timeArrival   = segmentsItem.arriveTime
                                    dataTrain.isComply      = segmentsItem.isComply

                                    dataTrain.status           = if (tripTrainsItem.status==null) "" else tripTrainsItem.status//"Reserved"

                                    dataTrain.pnrCode          = if (tripTrainsItem.pnrCode==null) "" else tripTrainsItem.pnrCode //"AW5CRP"
                                    dataTrain.pnrID            = if (tripTrainsItem.pnrId==null) "" else tripTrainsItem.pnrId
                                    dataTrain.price            = tripTrainsItem.amount.toString()
                                    dataTrain.idTrain          = if (tripTrainsItem.idTrain==null) "" else tripTrainsItem.idTrain
                                    dataTrain.tripID           = if (mData.id==null) "" else mData.id
                                    dataTrain.tripItemID       = tripTrainsItem.tripItemId ?: ""
                                    dataTrain.referenceCode    = if (tripTrainsItem.referenceCode==null) "" else tripTrainsItem.referenceCode

                                    dataTrain.progressTrain    = tripTrainsItem.jobProgress.progress

                                    dataTrainModel.add(dataTrain)
                                }
                            }
                        }
                    }
                }

                if (data.tripItemTypes.filter { it.name == "Hotel" }.isNotEmpty()){
                    data.tripItemTypes.filter { it.name == "Hotel" }.forEachIndexed { index, tripItemTypesItem ->
                        tripItemTypesItem.tripItems.
                                filter {it.employeeId == data.employeeId }.forEach {
                            it.tripHotels.forEachIndexed { index, tripHotelsItem ->
                                val dataHotel =  ItemHotelModel()
                                dataHotel.image       = tripHotelsItem.image.toString()
                                dataHotel.status      = tripHotelsItem.statusName.toString()
                                dataHotel.nameHotel   = tripHotelsItem.name.toString()
                                dataHotel.address     = if (tripHotelsItem.address==null) "${tripHotelsItem.cityName}" else tripHotelsItem.address

                                dataHotel.dateBooking = tripHotelsItem.bookedDate.toString()
                                dataHotel.starRating  = tripHotelsItem.rating.toString()
                                dataHotel.price       = tripHotelsItem.amount.toString()
                                dataHotel.employId    = data.employeeId
                                dataHotel.tripItemId  = tripHotelsItem.tripItemId.toString()

                                dataHotel.typeHotel   = tripHotelsItem.roomType.toString()
                                dataHotel.pnrHotel    = tripHotelsItem.pnrId.toString()
                                dataHotel.description = if (tripHotelsItem.address==null) "${tripHotelsItem.cityName}" else tripHotelsItem.address +" ,"+ tripHotelsItem.city

                                dataHotelModel.add(dataHotel)
                            }
                        }
                    }
                }

                return@map TripParticipantsItemModel(
                        data.id,
                        data.totalTripPaidAirline,
                        data.totalTripPaidTrain,
                        data.totalTripPaidHotel,
                        data.costCenterCode,
                        data.costCenterName,
                        data.budgetCode,
                        data.budgetName,
                        data.budgetId,
                        data.costCenterId,
                        ListTripItemTypesDataMapper().mapFrom(data.tripItemTypes),
                        data.isApproveForm,
                        data.status,
                        data.statusView,
                        data.fullName,
                        data.jobTitle,
                        data.employeeId,
                        ListApprovalDataMapper().mapFrom(data.tripParticipantCustomApprovals),
                        dataFlightModel,
                        dataTrainModel,
                        dataHotelModel)
            }

            return tpModelList
        }

    }

    private fun segmentMapperData(segmentFlightEntity: SegmentFlightEntity): FlightSegmentItem {
        val mData = FlightSegmentItem()
        Log.d("xixxx","on mapper 1 :" + segmentFlightEntity.id + " - " + segmentFlightEntity.airlineName)

        mData.nameAirline = segmentFlightEntity.airlineName
        mData.idAirline   =   segmentFlightEntity.id
        mData.imageAirline  = segmentFlightEntity.airlineImageUrl
        mData.classAirline  = segmentFlightEntity.classCode
        mData.timeDeparture = segmentFlightEntity.departTime
        mData.timeArrival   = segmentFlightEntity.arriveTime
        mData.cityDeparture = segmentFlightEntity.cityOrigin
        mData.cityArrival   = segmentFlightEntity.cityDestination
        mData.teminal       = ""
        mData.estimatiTime  = ""
        mData.seatFlight    = segmentFlightEntity.flightNumber
        mData.typeFlight    = segmentFlightEntity.category
        mData.airportDeparture = segmentFlightEntity.airportOrigin
        mData.airportArrival   = segmentFlightEntity.airportDestination
        mData.dateDeparture    = segmentFlightEntity.departDate
        mData.dateArrival      = segmentFlightEntity.arriveDate
        mData.layouver         = ""

        return mData
    }
}

