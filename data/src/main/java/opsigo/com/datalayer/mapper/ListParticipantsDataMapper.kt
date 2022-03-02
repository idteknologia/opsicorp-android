package opsigo.com.datalayer.mapper

import android.util.Log
import opsigo.com.datalayer.model.cart.PaymentsItem
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
                    mData.flights.filter { it?.employeeId == data?.employeeId }.forEachIndexed { index, flightsItem ->
                        flightsItem?.tripFlights?.forEachIndexed { index, tripFlightsItem ->
                            var num = 0
                            val dataFlight  = ItemFlightModel()

                            dataFlight.typeView         = tripFlightsItem!!.flightTypeView.toString()
                            dataFlight.type             = tripFlightsItem.flightType

                            dataFlight.airlineName      = tripFlightsItem.airlineView.toString()//"Sriwijaya"

                            dataFlight.status           = if (tripFlightsItem.status==null) "" else tripFlightsItem.status

                            dataFlight.pnrCode          = if (tripFlightsItem.pnrCode==null) "" else tripFlightsItem.pnrCode
                            dataFlight.pnrId            = tripFlightsItem.pnrId.toString()


                            dataFlight.price            = if (tripFlightsItem.amount==null) "0" else tripFlightsItem.amount.toString()
                            dataFlight.bookingDate      = tripFlightsItem.bookingDate.toString()


                            tripFlightsItem.payments?.forEachIndexed { index, paymentsItem ->
                                dataFlight.priceItem.add(priceMapperData(paymentsItem))
                            }

                            tripFlightsItem.segments?.forEachIndexed { index, segmentsItem ->
                                dataFlight.imageFlight      = segmentsItem!!.airlineImageUrl.toString()//"https://i.ibb.co/C0XzT6K/sriwijaya.png"
                                dataFlight.originDestination = segmentsItem.originName+" - "+segmentsItem.destinationName //"Jakarta (CGK) - Yogyakarta (JOG)"
                                dataFlight.nextDestination = segmentsItem.destinationName+" - "+segmentsItem.originName
                                dataFlight.idFlight         = segmentsItem.tripFlightId.toString()//"Sriwijaya"
                                dataFlight.flightNumber     = segmentsItem.flightNumber.toString()//"SJ-0412"
                                dataFlight.seatNumber       = segmentsItem.flightNumber.toString()

                                dataFlight.classFlight      = segmentsItem.category + " Class"
                                dataFlight.subClass         = "Subclass-" + segmentsItem.classCode.toString()

                                dataFlight.num              = segmentsItem.num
                                dataFlight.seq              = segmentsItem.seq

                                //departure
                                dataFlight.origin           = segmentsItem.origin.toString()
                                dataFlight.originName       = segmentsItem.cityOrigin.toString()
                                dataFlight.airportDeparture = segmentsItem.airportOrigin.toString()
                                dataFlight.dateDeparture    = segmentsItem.departDate.toString()

                                dataFlight.timeDeparture    = segmentsItem.departTime.toString()

                                //arrival
                                dataFlight.destination      = segmentsItem.destination.toString()
                                dataFlight.destinationName  = segmentsItem.cityDestination.toString()
                                dataFlight.airportArrival   = segmentsItem.airportDestination.toString()
                                dataFlight.dateArrival      = segmentsItem.arriveDate.toString()
                                dataFlight.timeArrival      = segmentsItem.arriveTime.toString()

                                dataFlight.isComply         = segmentsItem.isComply
                                dataFlight.isRefund         = segmentsItem.isRefund
                                dataFlight.isReschedule     = segmentsItem.isReschedule

                                dataFlight.duration         = segmentsItem.duration.toString()

                                dataFlight.flightSegmentItem.add(segmentMapperData(segmentsItem, tripFlightsItem.pnrCode.toString(),
                                        tripFlightsItem.status.toString(), tripFlightsItem.amount.toString(),
                                        tripFlightsItem.passengers?.size.toString(), tripFlightsItem.airlineView.toString(), ))
                            }
                            dataFlight.isRefunded       = tripFlightsItem.isRefunded
                            dataFlight.progressFLight    = if (tripFlightsItem.jobProgress?.progress==null) "" else tripFlightsItem.jobProgress.progress


                            dataFlightModel.add(dataFlight)
                            num++
                        }
                    }
                }

                if (!mData.trains.isNullOrEmpty()){
                    if (mData.trains.filter { it?.employeeId == data?.employeeId }.isNotEmpty()){
                        mData.trains.filter { it?.employeeId == data?.employeeId }.forEachIndexed { positionTrainEntity, tripTrainEntity ->
                            tripTrainEntity?.tripTrains?.forEachIndexed { positionTripTrain, tripTrainsItem ->
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
                                    dataTrain.price            = tripTrainsItem.amount
                                    dataTrain.idTrain          = if (tripTrainsItem.id==null) "" else tripTrainsItem.id
                                    dataTrain.tripID           = if (mData.id==null) "" else mData.id
                                    dataTrain.tripItemID       = tripTrainsItem.tripItemId ?: ""
                                    dataTrain.referenceCode    = if (tripTrainsItem.referenceCode==null) "" else tripTrainsItem.referenceCode
                                    dataTrain.progressTrain    = tripTrainsItem.jobProgress?.progress.toString()

                                    dataTrain.typeTrip         = tripTrainEntity.flightType
                                    dataTrain.isBackTrain      = positionTripTrain==tripTrainEntity.tripTrains.size-1

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
                                dataHotel.ticketedDate = tripHotelsItem?.ticketedDate.toString()
                                dataHotel.starRating  = tripHotelsItem?.rating.toString()
                                dataHotel.price       = tripHotelsItem?.payments?.first()?.amount.toString()
                                dataHotel.reasonCode  = it.reasonCode.toString()
                                dataHotel.employId    = data.employeeId.toString()
                                dataHotel.tripItemId  = tripHotelsItem?.tripItemId.toString()
                                dataHotel.hotelId     = tripHotelsItem?.payments?.first()?.tripHotelId.toString()
                                dataHotel.typeHotel   = tripHotelsItem?.roomType.toString()
                                dataHotel.pnrCode     = tripHotelsItem?.bookingCode.toString()
                                dataHotel.pnrId       = tripHotelsItem?.pnrId.toString()
                                dataHotel.isRefund    = tripHotelsItem?.isRefund == true
                                dataHotel.isRefunded  = tripHotelsItem?.isRefunded == true
                                dataHotel.isReschedule = tripHotelsItem?.isReschedule == true
                                dataHotel.description = if (tripHotelsItem?.address==null) "${tripHotelsItem?.cityName}" else tripHotelsItem.address

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
                        data?.positionName.toString(),
                        data?.employeeId.toString(),
                        ListApprovalDataMapper().mapFrom(data?.tripParticipantCustomApprovals),
                        dataFlightModel,
                        dataTrainModel,
                        dataHotelModel,
                        data?.itineraryUrl.toString())
            }
            if (!tpModelList.isNullOrEmpty()){
                return tpModelList
            }else{
                return ArrayList()
            }
        }

    }

    private fun priceMapperData(payments: PaymentsItem?): PaymentsItemModel {
        val mData = PaymentsItemModel()

        mData.id = payments?.id.toString()
        mData.code = payments?.code.toString()
        mData.tripFlightId = payments?.tripFlightId.toString()
        mData.title = payments?.title.toString()
        mData.amount = payments?.amount
        mData.currency = payments?.currency.toString()
        mData.seq = payments?.seq

        return mData
    }

    private fun segmentMapperData(segmentFlightEntity: SegmentsItem?, pnrCode: String, status: String,
             price: String, totalPassenger: String, airlineName: String): FlightSegmentItem {
        val mData = FlightSegmentItem()
        Log.d("xixxx","on mapper 1 :" + segmentFlightEntity?.id + " - " + segmentFlightEntity?.airlineName)

        mData.nameAirline = airlineName
        mData.pnrCode    = pnrCode
        mData.status       = status
        mData.airlineNumber = segmentFlightEntity?.flightNumber.toString()
        mData.price = price
        mData.totalPassenger = totalPassenger
        mData.idAirline   =   segmentFlightEntity?.id.toString()
        mData.imageAirline  = segmentFlightEntity?.airlineImageUrl.toString()
        mData.classAirline  = segmentFlightEntity?.classCode.toString()
        mData.timeDeparture = segmentFlightEntity?.departTime.toString()
        mData.timeArrival   = segmentFlightEntity?.arriveTime.toString()
        mData.cityDeparture = segmentFlightEntity?.cityOrigin.toString()
        mData.cityCodeDeparture = segmentFlightEntity?.origin.toString()
        mData.cityArrival   = segmentFlightEntity?.cityDestination.toString()
        mData.cityCodeArrival = segmentFlightEntity?.destination.toString()
        mData.terminal       = ""
        mData.estimatiTime  = segmentFlightEntity?.duration.toString()
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

