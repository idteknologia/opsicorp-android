package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.myboking.*
import opsigo.com.datalayer.model.myboking.PassengersItem
import opsigo.com.domainlayer.model.my_booking.*
import opsigo.com.datalayer.model.myboking.SegmentsItem
import opsigo.com.domainlayer.model.accomodation.hotel.FacilityHotelModel

class DetailMyBookingMapper {
    fun mapping(deserialize: DetailMyBookingEntity): DetailMyBookingModel {
        deserialize.apply {
            val mData = DetailMyBookingModel(
                mappingPrice(data.priceDetails),
                data.paymentStatusText,
                data.itemType,
                bookingContactMapper(this.data),
                data.code,
                if (data.itemType==0) mappingFlight(data.flights) else ArrayList(),
                if (data.itemType==1) mappingHotel(data.hotel) else ItemHotelPurchase() ,
                if (data.itemType==2) mappingTrain(data.trains) else ArrayList(),
                data.purchasedDate,
                data.totalPaid,
                data.paymentMethod,
                data.id,
                data.paymentStatus
            )
            return mData
        }
    }

    private fun mappingTrain(trains: ArrayList<TrainItemMyBookingEntity>): ArrayList<ItemPurchaseTrainModel> {
        val data = ArrayList<ItemPurchaseTrainModel>()
        trains.forEach {
            val mData = ItemPurchaseTrainModel()
            mData.status            = it.status.toString()
            mData.pnrCode           = it.pnrCode.toString()
            mData.nameTrain         = it.trainName.toString()
            mData.trainCode         = it.trainNumber.toString()
            mData.className         = "${it.classCategory.toString()} (${it.classCode.toString()})"
            mData.dateDeparture     = it.departureDate.toString()
            mData.dateArrival       = it.arrivalDate.toString()
            mData.originCity        = it.originCity.toString()
            mData.originStation     = it.originStation.toString()
            mData.durationTime      = it.destination.toString()
            mData.destinationCity   = it.destinationCity.toString()
            mData.destinationStation  = it.destinationStation.toString()
            mData.passager          = mappingPassangerTrain(it.passengers)
            data.add(mData)
        }
        return data
    }

    private fun mappingPassangerTrain(passengers: List<PassengersItem?>?): ArrayList<PassengersTrainModel> {
        val mData = ArrayList<PassengersTrainModel>()
        passengers?.forEach {
            val data = PassengersTrainModel()
            data.typeAge     = it?.idType.toString()
            data.firstName   = it?.firstName.toString()
            data.lastName    = it?.lastName.toString()
            data.idNumber    = it?.idNumber.toString()
            data.title       = it?.title.toString()
            data.index       = 0
            data.seatNumber  = it?.seatNumber.toString()
            data.seatName    = it?.seatName.toString()
            data.idType      = it?.idType.toString()
            data.birthDate   = it?.birthDate.toString()
            mData.add(data)
        }
        return mData
    }

    private fun mappingFlight(flights: ArrayList<FlightItemMyBookingEntity>?): ArrayList<DetailFlightMyBookingModel> {
        val mData = ArrayList<DetailFlightMyBookingModel>()
        flights?.forEach {
            mData.add(DetailFlightMyBookingModel(
                it.status.toString(),
                it.destinationCity.toString(),
                it.originCity.toString(),
                it.pnrCode.toString(),
                segmentMapping(it.segments),
                passangerMapping(it.passengers)))
        }

        return mData
    }

    private fun passangerMapping(passengers: List<PassengersFlightItem?>?):ArrayList<PassangerPurchaseModel> {
        val data = ArrayList<PassangerPurchaseModel>()
        passengers?.forEach {
            data.add(PassangerPurchaseModel(
                it?.birthDate.toString(),
                "kosong",
                "${it?.firstName} ${it?.lastName}",
                "${it?.seatName } ${it?.seatNumber}"))
        }
        return data
    }

    private fun segmentMapping(segments: List<SegmentsItem?>?): ArrayList<PurchaseDetailTripFlightAndTrainModel> {
        val mData = ArrayList<PurchaseDetailTripFlightAndTrainModel>()
        segments?.forEach {
            val data = PurchaseDetailTripFlightAndTrainModel()
            data.status                   = "null"
            data.totalHourDuration                = "null"
            data.terminalDeparture        = "terminal null"
            data.nameFlight               = it?.airlineName.toString()
            data.numberSeat               = "number seat null"
            data.classFlight              = "${it?.classCategory} Class"
            data.codeFlight               = it?.flightNumber.toString()
            data.timeDeparture            = it?.departureTimeDisplay.toString()
            data.dateDepartute            = it?.departureDate.toString()
            data.nameAirportDepature      = it?.originAirport.toString()
            data.origin                   = "${it?.originCity} (${it?.origin})"
            data.timeArrival              = "arrival time null"
            data.dateArrival              = "date arrival null"
            data.nameStasiunArrival       = it?.destinationAirport.toString()
            data.destinantion             = "${it?.destinationCity} (${it?.destination})"
            data.layover                  = "null"
            data.nameAirportLayover       = "null"
            data.imageFlight              = it?.airlineImageUrl.toString()
            mData.add(data)
        }
        return mData
    }


    private fun mappingPrice(priceDetails: ArrayList<PriceMyBookingEntity>): ArrayList<PriceListModel> {
        val data = ArrayList<PriceListModel>()
        priceDetails.forEach {
            data.add(PriceListModel(it.amount,it.title,it.index))
        }
        return data
    }

    private fun bookingContactMapper(data: Data): PurchaseBookingContactModel {
        data.bookingContact.apply {
            return PurchaseBookingContactModel(
                this?.email.toString(),
                this?.firstName.toString(),
                this?.fullName.toString(),
                this?.title.toString(),
                this?.lastName.toString(),
                this?.mobilePhone.toString()
            )
        }
    }

    /*private fun itemMapper(data: Data?): Any {
        when(data?.itemType){
            0 -> {
                //flight
                return data.flights as List<ItemFlightModel>
            }
            1 -> {
                //hotel
                return mappingHotel(data.hotel)
            }
            else -> {
                //train
                return data?.trains as List<ItemTrainModel>
            }
        }
    }*/

    private fun mappingHotel(hotel: HotelItemMyBookingEntity): ItemHotelPurchase {
        hotel.apply {
            val data = ItemHotelPurchase()
            data.voucerCode      = this.pnrCode
            data.hotelName       = this.hotelName
            data.status          = this.status
            data.ratingStar      = this.hotelRating
            data.address         = this.address
            data.checkInDate     = this.checkInDate
            data.checkOutDate    = this.checkInDate
            data.totalNight      = "null"
            data.timeCheckIn     = this.checkInDate.toString().split(" ")[1]
            data.timeCheckOut    = this.checkInDate.toString().split(" ")[1]
            data.nameBooker      = this.bookingContact
            data.latitude        = "null"
            data.longitude       = "null"
            data.classRoom       = this.roomType
            data.roomsTotal      = this.roomsTotal
            data.facility        = mappingFacility(this.roomService.toString())
            data.guests          = mappingGuest(this.guests)
            data.cancellationPolicy = mappingCancellationPolicy(this.cancellationInfo)
            data.dataRemark         = mappingRemarkHotel(this.remarkHotel.toString())
            return data
        }
    }

    private fun mappingRemarkHotel(remarkHotel: String): ArrayList<String> {
        val data = java.util.ArrayList<String>()
        if (remarkHotel!="null"&&remarkHotel.isNotEmpty()){
            if (remarkHotel.contains(",")){
                remarkHotel.split(",").forEach {
                    data.add(it)
                }
            }
        }
        return data
    }

    private fun mappingCancellationPolicy(cancellationInfo: String?): ArrayList<String> {
        val data = ArrayList<String>()
        data.add(cancellationInfo.toString())
        return data
    }

    private fun mappingGuest(guests: List<GuestsItem?>?): List<GuestsItems> {
        val data = ArrayList<GuestsItems>()
        guests?.forEach {
            val mData = GuestsItems(
                it?.firstName.toString(),
                it?.type.toString(),
                it?.type)
            data.add(mData)
        }
        return data
    }


    private fun mappingFacility(facility: String): ArrayList<FacilityHotelModel> {
        val data = ArrayList<FacilityHotelModel>()
        data.add(FacilityHotelModel("",facility))
        return data
    }

}