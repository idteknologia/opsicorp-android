package opsigo.com.datalayer.mapper

import opsigo.com.domainlayer.model.accomodation.hotel.FacilityHotelModel
import opsigo.com.domainlayer.model.my_booking.*
import opsigo.com.datalayer.model.cart.*
import java.text.SimpleDateFormat
import android.util.Log
import java.util.*

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
            data.tripMemberId      = this.tripParticipants?.first()?.id.toString()
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
                        totalPay = flight.payments?.find { it?.code.toString().toUpperCase().equals("TOTAL_PAID")}?.amount!!
                    }
                }
            }
            1 -> {
                val data = summaryEntity.tripHotels?.find { it?.hotel?.id==idItem }?.hotel
                totalPay = data?.payments?.find { it?.code.toString().toUpperCase().equals("TOTAL_PAID")}?.amount!!

            }
            2 -> {
                summaryEntity.trains?.forEach {
                    val train = it?.tripTrains?.find { it?.pnrCode==idItem }
                    if (train!=null){
                        totalPay = train.payments?.find { it?.code.toString().toUpperCase().equals("TOTAL_PAID")}?.amount!!
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
        hotel.isRefund         = if (data?.isRefund!=null) data.isRefund else false
        hotel.isReschedule     = if (data?.isReschedule!=null) data.isReschedule else false
        hotel.idHotel          = data?.id.toString()
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
        hotelDetail.facilities?.forEach {
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
            mData.isRefund               = false
            mData.isReschedule           = false
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
            if (flight!=null){
                val mData = DetailFlightMyBookingModel()
                mData.status          = flight.prgText.toString()
                mData.originCity      = flight.originView.toString()
                mData.destinationCity = flight.destinationView.toString()
                mData.pnrCode         = flight.pnrCode.toString()
                mData.Segment         = mapperSegment(flight)
                mData.idFlight        = flight.id.toString()
                mData.passanger       = mapperPassengeFlight(flight)
                data.add(mData)
            }

        }
        return data
    }

    private fun mapperPassengeFlight(flight: TripFlightsItem?): ArrayList<PassangerPurchaseModel> {
        val data = ArrayList<PassangerPurchaseModel>()
        flight?.passengers?.forEach {
            val mData = PassangerPurchaseModel()
            mData.age             = it?.type.toString()
            if (it?.ssrs?.filter { it?.ssrType==1 }!=null){
                if (it.ssrs.filter { it?.ssrType==1 }.isNotEmpty()){
                    mData.totalBagage = it.ssrs.find { it?.ssrType==1 }?.ssrName.toString()
                }
            }
            mData.Name            = "${it?.firstName.toString()} ${it?.lastName.toString()}"
            mData.seatPassager    = it?.seatNumber.toString()
            data.add(mData)
        }
        return data
    }

    private fun mapperSegment(flight: TripFlightsItem?): ArrayList<PurchaseDetailTripFlightAndTrainModel> {
        val data =  ArrayList<PurchaseDetailTripFlightAndTrainModel>()
        flight?.segments?.forEachIndexed { index, it ->
            val mData = PurchaseDetailTripFlightAndTrainModel()
            mData.status                 = flight.prgText.toString()
            mData.totalHourDuration      = it?.duration.toString()
            mData.terminalDeparture      = "terminal null"
            mData.nameFlight             = if (it?.airlineName.toString().isEmpty()) "null" else it?.airlineName.toString()
            mData.numberSeat             = "null"
            mData.classFlight            = "${it?.category} ${it?.classCode}"
            mData.codeFlight             = "null"
            mData.flightNumber           = it?.flightNumber.toString()
            mData.timeDeparture          = it?.departTime.toString()
            mData.dateDepartute          = it?.departDate.toString()
            mData.nameAirportDepature    = it?.airportOrigin.toString()
            mData.origin                 = it?.originName.toString()
            mData.timeArrival            = it?.arriveTime.toString()
            mData.dateArrival            = it?.arriveDate.toString()
            mData.nameStasiunArrival     = it?.airportDestination.toString()
            mData.destinantion           = it?.destinationName.toString()

            if (flight.segments.size>1){
                if (index!=flight.segments.size-1){
                    if (flight.segments[index]?.num==flight.segments[index+1]?.num){
                        mData.isConnecting       = true
                        val arrivalDateTime1     = it?.arriveDateTime
                        val arrivalDateTime2     = flight.segments[index+1]?.arriveDateTime
                        mData.layover            = "Layover ${calculateHour(arrivalDateTime1.toString(),arrivalDateTime2.toString()).first}h ${calculateHour(arrivalDateTime1.toString(),arrivalDateTime2.toString()).second}m"
                        mData.nameAirportLayover = it?.airportDestination.toString()
                    }
                }
            }

            mData.imageFlight            = it?.airlineImageUrl.toString()
            mData.isRefund               = if (it?.isRefund!=null) it.isRefund else false
            mData.isReschedule           = if (it?.isReschedule!=null) it.isReschedule else false
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

    fun calculateHour(t1 : String ,t2:String):Pair<Int,Int>{

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        val d1: Date = sdf.parse(t1)
        val d2: Date = sdf.parse(t2)
        val c1 = Calendar.getInstance()
        val c2 = Calendar.getInstance()
        c1.time = d1
        c2.time = d2

        if (c2[Calendar.HOUR_OF_DAY] < 12) {
            c2[Calendar.DAY_OF_YEAR] = c2[Calendar.DAY_OF_YEAR] + 1
        }
        val elapsed = c2.timeInMillis - c1.timeInMillis
        val minute  = ((elapsed /(1000*60)) % 60).toInt()
        val hour    = ((elapsed /(1000*60*60)) % 24).toInt()
        return Pair(hour,minute)
    }
}