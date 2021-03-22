package opsigo.com.datalayer.mapper

import android.util.Log
import opsigo.com.datalayer.model.cart.SegmentsItem
import opsigo.com.datalayer.model.cart.SummaryEntity
import opsigo.com.domainlayer.model.summary.*

class ListParticipantsDataMapper {

    fun mapFrom(mData: SummaryEntity): List<TripParticipantsItemModel> {

        val from = mData.tripParticipants

        from.let {

            val tpModelList = it?.map { data ->

                val dataFlightModel = ArrayList<ItemFlightModel>()
                val dataTrainModel  = ArrayList<ItemTrainModel>()
                val dataHotelModel  = ArrayList<ItemHotelModel>()

                if (!mData.flights.isNullOrEmpty()){
                    mData.flights.filter { it?.employeeId == data?.employeeId }.first()?.tripFlights?.forEachIndexed { index, tripFlightsItem ->

                        var num = 0
                        tripFlightsItem?.segments?.forEachIndexed { _, segmentFlightEntity ->

                            if (num==segmentFlightEntity?.num){
                                val dataFlight  = ItemFlightModel()

                                dataFlight.typeView         = tripFlightsItem.flightTypeView.toString()
                                dataFlight.type             = tripFlightsItem.flightType

                                dataFlight.imageFlight      = segmentFlightEntity.airlineImageUrl.toString()//"https://i.ibb.co/C0XzT6K/sriwijaya.png"
                                //dataFlight.carrier          = tripFlightsItem.carrier

                                dataFlight.originDeatination = segmentFlightEntity.originName+" - "+segmentFlightEntity.destinationName //"Jakarta (CGK) - Yogyakarta (JOG)"

                                //dataFlight.idFlight         = segmentFlightEntity.id//"Sriwijaya"
                                dataFlight.idFlight         = segmentFlightEntity.tripFlightId.toString()//"Sriwijaya"
                                dataFlight.airlineName      = tripFlightsItem.airlineView.toString()//"Sriwijaya"
                                dataFlight.flightNumber     = segmentFlightEntity.flightNumber.toString()//"SJ-0412"
                                dataFlight.seatNumber       = segmentFlightEntity.flightNumber.toString()

                                //if (segmentsItem.classCode==null) "" else segmentsItem.classCode
                                dataFlight.status           = if (tripFlightsItem.status==null) "" else tripFlightsItem.status
                                dataFlight.classFlight      = segmentFlightEntity.category + " Class"
                                dataFlight.subClass         = "Subclass-" + segmentFlightEntity.classCode.toString()

                                dataFlight.num              = segmentFlightEntity.num
                                dataFlight.seq              = segmentFlightEntity.seq

                                //departure
                                dataFlight.origin           = segmentFlightEntity.origin.toString()
                                dataFlight.originName       = segmentFlightEntity.cityOrigin.toString()
                                dataFlight.airportDeparture = segmentFlightEntity.airportOrigin.toString()
                                dataFlight.dateDeparture    = segmentFlightEntity.departDate.toString()

                                dataFlight.timeDeparture    = segmentFlightEntity.departTime.toString()

                                //arrival
                                dataFlight.destination      = segmentFlightEntity.destination.toString()
                                dataFlight.destinationName  = segmentFlightEntity.cityDestination.toString()
                                dataFlight.airportArrival   = segmentFlightEntity.airportDestination.toString()
                                dataFlight.dateArrival      = segmentFlightEntity.arriveDate.toString()
                                dataFlight.timeArrival      = segmentFlightEntity.arriveTime.toString()

                                dataFlight.pnrCode          = if (tripFlightsItem.pnrCode==null) "" else tripFlightsItem.pnrCode
                                dataFlight.pnrId            = tripFlightsItem.pnrId.toString()

                                dataFlight.isComply         = segmentFlightEntity.isComply
                                dataFlight.duration         = segmentFlightEntity.duration.toString()

//                                dataFlight.price          = "IDR 400.000/pax"
                                dataFlight.price            = if (tripFlightsItem.amount==null) "0" else tripFlightsItem.amount.toString()

                                dataFlight.flightSegmentItem.add(segmentMapperData(segmentFlightEntity))

                                dataFlight.progressFLight    = if (tripFlightsItem.jobProgress?.progress==null) "" else tripFlightsItem.jobProgress.progress

                                dataFlightModel.add(dataFlight)
                                num++

                            }
                            else{
                                dataFlightModel[num-1].flightSegmentItem.add(segmentMapperData(segmentFlightEntity))
                            }

                        }

                    }
                }

                if (!mData.trains.isNullOrEmpty()){
                    if (mData.trains.filter { it?.employeeId == data?.employeeId }.isNotEmpty()){
                        mData.trains.filter { it?.employeeId == data?.employeeId }.forEachIndexed { index, tripTrainEntity ->
                            tripTrainEntity?.tripTrains?.forEachIndexed { index, tripTrainsItem ->
                                tripTrainsItem?.segments?.forEachIndexed { index, segmentsItem ->
                                    val dataTrain  = ItemTrainModel()

                                    dataTrain.typeView      = tripTrainsItem.flightTypeView.toString()

                                    dataTrain.imageTrain    = if(segmentsItem.kaiImageUrl==null) "https://i.ibb.co/5Wv9ksW/Screen-Shot-2019-08-27-at-13-34-15.png" else segmentsItem.kaiImageUrl
                                    dataTrain.titleTrain    = segmentsItem.trainName//"Argo Parahyangan"
                                    dataTrain.carrierNumber = if (segmentsItem.currierNumber==null) "" else segmentsItem.currierNumber//"K02"
                                    dataTrain.classTrain    = segmentsItem.category + " (" + segmentsItem.classCode + ")"//"Executive (J)"

                                    dataTrain.seatNumber    = if (tripTrainsItem.passengers?.get(0)?.seatNumber==null) "" else tripTrainsItem.passengers?.get(0)?.seatNumber.toString()
                                    dataTrain.seatName      = if (tripTrainsItem.passengers?.get(0)?.seatName==null) "" else tripTrainsItem.passengers?.get(0)?.seatName.toString()
                                    dataTrain.seatText      = if (tripTrainsItem.passengers?.get(0)?.seatText==null) "" else tripTrainsItem.passengers?.get(0)?.seatText.toString()
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
                                    dataTrain.idTrain          = if (tripTrainsItem.id==null) "" else tripTrainsItem.id
                                    dataTrain.tripID           = if (mData.id==null) "" else mData.id
                                    dataTrain.tripItemID       = tripTrainsItem.tripItemId ?: ""
                                    dataTrain.referenceCode    = if (tripTrainsItem.referenceCode==null) "" else tripTrainsItem.referenceCode

                                    dataTrain.progressTrain    = tripTrainsItem.jobProgress?.progress.toString()

                                    dataTrainModel.add(dataTrain)
                                }
                            }
                        }
                    }
                }

                if (!data?.tripItemTypes?.filter { it?.name == "Hotel" }.isNullOrEmpty()){
                    data?.tripItemTypes?.filter { it?.name == "Hotel" }?.forEachIndexed { index, tripItemTypesItem ->
                        tripItemTypesItem?.tripItems?.
                                filter {it?.employeeId == data.employeeId }?.forEach {
                            it?.tripHotels?.forEachIndexed { index, tripHotelsItem ->
                                val dataHotel =  ItemHotelModel()
                                dataHotel.image       = tripHotelsItem?.image.toString()
                                dataHotel.checkIn     = tripHotelsItem?.checkin.toString()
                                dataHotel.checkOut    = tripHotelsItem?.checkout.toString()
                                dataHotel.status      = tripHotelsItem?.statusName.toString()
                                dataHotel.nameHotel   = tripHotelsItem?.name.toString()
                                dataHotel.address     = if (tripHotelsItem?.address==null) "${tripHotelsItem?.cityName}" else tripHotelsItem.address.toString()

                                dataHotel.dateBooking = tripHotelsItem?.bookedDate.toString()
                                dataHotel.starRating  = tripHotelsItem?.rating.toString()
                                dataHotel.price       = tripHotelsItem?.amount.toString()
                                dataHotel.reasonCode  = it.reasonCode.toString()
                                dataHotel.employId    = data.employeeId.toString()
                                dataHotel.tripItemId  = tripHotelsItem?.tripItemId.toString()

                                dataHotel.typeHotel   = tripHotelsItem?.roomType.toString()
                                dataHotel.pnrHotel    = tripHotelsItem?.pnrId.toString()
                                dataHotel.description = if (tripHotelsItem?.address==null) "${tripHotelsItem?.cityName}" else tripHotelsItem.address +" ,"+ tripHotelsItem.city.toString()

                                dataHotelModel.add(dataHotel)
                            }
                        }
                    }
                }

                return@map TripParticipantsItemModel(
                        data?.id.toString(),
                        data?.totalTripPaidAirline.toString(),
                        data?.totalTripPaidTrain.toString(),
                        data?.totalTripPaidHotel.toString(),
                        data?.costCenterCode.toString(),
                        data?.costCenterName.toString(),
                        data?.budgetCode.toString(),
                        data?.budgetName.toString(),
                        data?.budgetId.toString(),
                        data?.costCenterId.toString(),
                        ListTripItemTypesDataMapper().mapFrom(data?.tripItemTypes),
                        if(data?.isApproveForm==null)false else data.isApproveForm,
                        data?.status.toString(),
                        data?.statusView.toString(),
                        data?.fullName.toString(),
                        data?.jobTitle.toString(),
                        data?.employeeId.toString(),
                        ListApprovalDataMapper().mapFrom(data?.tripParticipantCustomApprovals),
                        dataFlightModel,
                        dataTrainModel,
                        dataHotelModel)
            }
            if (!tpModelList.isNullOrEmpty()){
                return tpModelList
            }else{
                return ArrayList()
            }
        }

    }

    private fun segmentMapperData(segmentFlightEntity: SegmentsItem?): FlightSegmentItem {
        val mData = FlightSegmentItem()
        Log.d("xixxx","on mapper 1 :" + segmentFlightEntity?.id + " - " + segmentFlightEntity?.airlineName)

        mData.nameAirline = segmentFlightEntity?.airlineName.toString()
        mData.idAirline   =   segmentFlightEntity?.id.toString()
        mData.imageAirline  = segmentFlightEntity?.airlineImageUrl.toString()
        mData.classAirline  = segmentFlightEntity?.classCode.toString()
        mData.timeDeparture = segmentFlightEntity?.departTime.toString()
        mData.timeArrival   = segmentFlightEntity?.arriveTime.toString()
        mData.cityDeparture = segmentFlightEntity?.cityOrigin.toString()
        mData.cityArrival   = segmentFlightEntity?.cityDestination.toString()
        mData.teminal       = ""
        mData.estimatiTime  = ""
        mData.seatFlight    = segmentFlightEntity?.flightNumber.toString()
        mData.typeFlight    = segmentFlightEntity?.category.toString()
        mData.airportDeparture = segmentFlightEntity?.airportOrigin.toString()
        mData.airportArrival   = segmentFlightEntity?.airportDestination.toString()
        mData.dateDeparture    = segmentFlightEntity?.departDate.toString()
        mData.dateArrival      = segmentFlightEntity?.arriveDate.toString()
        mData.layouver         = ""

        return mData
    }
}

