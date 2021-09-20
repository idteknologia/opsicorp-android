package opsigo.com.datalayer.mapper

import android.util.Log
import opsigo.com.domainlayer.model.accomodation.hotel.FacilityHotelModel
import opsigo.com.domainlayer.model.my_booking.*
import opsigo.com.datalayer.model.cart.*
import java.util.ArrayList

class EticketMapper {
    fun mapper(summary:SummaryEntity,idItem:String,typeItem:Int): DetailMyBookingModel {
        summary.apply {
            val data = DetailMyBookingModel()
            data.priceDetails      = ArrayList()
            data.paymentStatusText = this.paymentStatusView
            data.itemType          = typeItem
            data.bookingContact    = bookingContactMapper(this.contact)
            data.code              = this.code
            when(typeItem){
                0-> {
                    data.dataFlight        = dataFlightMapper(this.flights,idItem)
                }
                1-> {
                    data.dataHotel         = dataHotelMapper(this.tripHotels,idItem)
                }
                2->{
                    data.dataTrain         = dataTrainMapper(this.trains,idItem)
                }
            }
            data.purchasedDate     = "null"
            data.totalPaid         = totalPayMapper(this,idItem,typeItem)
            data.paymentMethod     = this.paymentTypeView.toString()
            data.id                = this.id
            data.paymentStatus     = this.paymentStatus
            return data
        }
    }

    private fun totalPayMapper(
        summaryEntity: SummaryEntity,
        idItem: String,
        typeItem: Int
    ): Double {
        var totalPay  = 0.0
        when(typeItem){
            0 -> {
                summaryEntity.flights?.forEach {
                    val flight = it?.tripFlights?.find { it?.pnrCode==idItem }
                    if (flight!=null){
                        flight.payments?.forEach {
                            totalPay = if (it?.amount!=null) totalPay+it.amount else totalPay
                        }
                    }
                }
            }
            1 -> {
                val data = summaryEntity.tripHotels?.find { it?.hotel?.id==idItem }?.hotel
                data?.payments?.forEach {
                    totalPay = totalPay+it.amount
                }
            }
            2 -> {
                summaryEntity.trains?.forEach {
                    val flight = it?.tripTrains?.find { it?.pnrCode==idItem }
                    if (flight!=null){
                        flight.payments?.forEach {
                            totalPay = if (it?.amount!=null) totalPay+it.amount else totalPay
                        }
                    }
                }
            }
        }
        return totalPay
    }

    private fun dataHotelMapper(
        tripHotels: List<TripHotelsItem?>?,
        idItem: String
    ): ItemHotelPurchase {
        val hotel = ItemHotelPurchase()
        val data = tripHotels?.find { it?.hotel?.id==idItem }?.hotel
        hotel.voucerCode       = data?.bookingCode.toString()
        hotel.hotelName        = data?.name.toString()
        hotel.status           = data?.statusName.toString()
        hotel.address          = data?.address.toString()
        hotel.checkInDate      = data?.checkin.toString()
        hotel.checkOutDate     = data?.checkout.toString()
        hotel.totalNight       = data?.duration.toString()
        hotel.timeCheckIn      = data?.checkin.toString()
        hotel.timeCheckOut     = data?.checkout.toString()
        hotel.nameBooker       = data?.contact?.fullName.toString()
        hotel.latitude         = data?.hotelDetail?.latitude.toString()
        hotel.longitude        = data?.hotelDetail?.latitude.toString()
        hotel.classRoom        = data?.roomCategory.toString()
        hotel.roomsTotal       = data?.rooms
        hotel.facility         = mappingFacility(data?.hotelDetail)
        hotel.guests           = mappingGuest(data?.guest,data?.roomType.toString())
        hotel.dataRemark       = mappingDataRemarkHotel(data?.remarkHotel.toString())
        hotel.cancellationPolicy = mappingCancellationPolicy(data?.cancellationPoliciesView)
        hotel.area             = data?.area.toString()
        hotel.ratingStar       = if (data?.rating==null)0 else data.rating
        return hotel
    }

    private fun mappingCancellationPolicy(cancellationPoliciesView: List<String?>?): ArrayList<String> {
        val data = ArrayList<String>()
        cancellationPoliciesView?.forEach {
            data.add(it.toString())
        }
        return data
    }

    private fun mappingDataRemarkHotel(remarkHotel: String): ArrayList<String> {
        val data = ArrayList<String>()
        if (remarkHotel!="null"&&remarkHotel.isNotEmpty()){
            if (remarkHotel.contains(",")){
                remarkHotel.split(",").forEach {
                    data.add(it)
                }
            }
        }
        return data
    }

    private fun mappingGuest(guest: List<GuestItem?>?, roomType: String): ArrayList<GuestsItems> {
        val data = ArrayList<GuestsItems>()
        guest?.forEach {
            val mData = GuestsItems()
            mData.maxBedType = "null"
            mData.bedType    = roomType
            mData.firstName  = it?.firstName.toString()
            mData.index      = 0
            data.add(mData)
        }
        return data
    }

    private fun mappingFacility(hotelDetail: HotelDetail?): ArrayList<FacilityHotelModel> {
        Log.e("TAG",Serializer.serialize(hotelDetail!!))
        val vacility = ArrayList<FacilityHotelModel>()
        hotelDetail?.facilities?.forEach {
            val mData = FacilityHotelModel()
            mData.code = it?.code.toString()
            mData.name = it?.description.toString()
            vacility.add(mData)
        }
        return vacility
    }

    private fun dataTrainMapper(trains: List<TrainsItem?>?,idItem: String): ArrayList<ItemPurchaseTrainModel> {
        val data = ArrayList<ItemPurchaseTrainModel>()
        trains?.forEach {
            val train = it?.tripTrains?.find { it?.pnrCode==idItem }
            val mData = ItemPurchaseTrainModel()
            mData.status                 = it?.status.toString()
            mData.pnrCode                = train?.pnrCode.toString()
            mData.nameTrain              = train?.trainName.toString()
            mData.trainCode              = train?.ticketNumber.toString()
            mData.className              = ""
            mData.dateArrival            = ""
            mData.dateDeparture          = ""
            mData.originCity             = train?.originView.toString()
            mData.originStation          = ""
            mData.durationTime           = ""
            mData.destinationCity        = train?.destinationView.toString()
            mData.destinationStation     = ""
            mData.passager               = mapperPassengeTrain(train?.passengers)
            data.add(mData)
        }
        return data
    }

    private fun mapperPassengeTrain(passengers: List<PassengersItem?>?): ArrayList<PassengersTrainModel> {
        val data = ArrayList<PassengersTrainModel>()
        passengers?.forEach {
            val mData = PassengersTrainModel()
            mData.typeAge           = it?.type.toString()
            mData.firstName         = it?.firstName.toString()
            mData.lastName          = it?.lastName.toString()
            mData.idNumber          = it?.idNumber.toString()
            mData.title             = it?.title?.toString()
            mData.index             = it?.index
            mData.seatNumber        = it?.seatNumber.toString()
            mData.seatName          = it?.seatName.toString()
            mData.idType            = it?.identityType.toString()
            mData.birthDate         = it?.birthDate.toString()
            data.add(mData)
        }
        return data
    }

    private fun dataFlightMapper(flights: List<FlightsItem?>?,idItem: String): ArrayList<DetailFlightMyBookingModel> {
        val data = ArrayList<DetailFlightMyBookingModel>()
        flights?.forEach {
            val flight = it?.tripFlights?.find { it?.pnrCode==idItem }
            val mData = DetailFlightMyBookingModel()
            mData.status          = flight?.prgText.toString()
            mData.destinationCity = flight?.destinationCity.toString()
            mData.originCity      = flight?.originCity.toString()
            mData.pnrCode         = flight?.pnrCode.toString()
            mData.Segment         = mapperSegment(flight)
            mData.passanger       = mapperPassengeFlight(flight)
            data.add(mData)
        }
        return data
    }

    private fun mapperPassengeFlight(flight: TripFlightsItem?): ArrayList<PassangerPurchaseModel> {
        val data = ArrayList<PassangerPurchaseModel>()
        flight?.passengers?.forEach {
            val mData = PassangerPurchaseModel()
            mData.age             = it?.type.toString()
            mData.totalBagage     = "null"
            mData.Name            = it?.fullName.toString()
            mData.seatPassager    = it?.seatNumber.toString()
            data.add(mData)
        }
        return data
    }

    private fun mapperSegment(flight: TripFlightsItem?): ArrayList<PurchaseDetailTripFlightAndTrainModel> {
        val data =  ArrayList<PurchaseDetailTripFlightAndTrainModel>()
        flight?.segments?.forEach {
            val mData = PurchaseDetailTripFlightAndTrainModel()
            mData.status                 = flight.prgText.toString()
            mData.totalHour              = "null"
            mData.terminalDeparture      = "terminal null"
            mData.nameFlight             = it?.airlineName.toString()
            mData.numberSeat             = "null"
            mData.classFlight            = "${it?.category} ${it?.classCode}"
            mData.typeFlight             = it?.flightNumber.toString()
            mData.timeDeparture          = it?.departTime.toString()
            mData.dateDepartute          = it?.departDate.toString()
            mData.nameAirportDepature    = it?.airportOrigin.toString()
            mData.origin                 = it?.originName.toString()
            mData.timeArrival            = it?.arriveTime.toString()
            mData.dateArrival            = it?.arriveDate.toString()
            mData.nameStasiunArrival     = it?.airportDestination.toString()
            mData.destinantion           = it?.destinationName.toString()
            mData.layover                = "null"
            mData.nameAirportLayover     = "null"
            mData.imageFlight            = it?.airlineImageUrl.toString()
            data.add(mData)
        }
        return data
    }

    private fun bookingContactMapper(contact: Contact?): PurchaseBookingContactModel? {
        contact.apply {
            val data = PurchaseBookingContactModel()
            data.email         = contact?.email.toString()
            data.firstName     = contact?.firstName.toString()
            data.fullName      = contact?.fullName.toString()
            data.title         = contact?.title.toString()
            data.lastName      = contact?.lastName.toString()
            data.mobilePhone   = contact?.mobilePhone.toString()
            return data
        }
    }
}