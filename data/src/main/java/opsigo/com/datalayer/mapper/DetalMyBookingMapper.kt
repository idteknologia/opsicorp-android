package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.myboking.Data
import opsigo.com.domainlayer.model.my_booking.*
import opsigo.com.datalayer.model.myboking.GuestsItem
import opsigo.com.domainlayer.model.summary.ItemTrainModel
import opsigo.com.datalayer.model.myboking.DetailMyBookingEntity
import opsigo.com.datalayer.model.myboking.HotelItemMyBookingEntity
import opsigo.com.domainlayer.model.accomodation.hotel.FacilityHotelModel

class DetalMyBookingMapper {
    fun mapping(deserialize: DetailMyBookingEntity): DetailMyBookingModel {
        deserialize.apply {
            val data = DetailMyBookingModel(
                this.data?.priceDetails as ArrayList<PriceListModel>,
                this.data.paymentStatusText,
                this.data.itemType,
                bookingContactMapper(this.data),
                this.data.code,
                itemMapper(this.data),
                this.data.purchasedDate,
                this.data.totalPaid,
                this.data.paymentMethod,
                this.data.id,
                this.data.paymentStatus
            )
            return data
        }
    }

    private fun bookingContactMapper(data: Data): PurchaseBookingContactModel? {
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

    private fun itemMapper(data: Data?): Any {
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
    }

    private fun mappingHotel(hotel: HotelItemMyBookingEntity): Any {
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
            return data
        }
    }

    private fun mappingGuest(guests: List<GuestsItem?>?): List<GuestsItems?>? {
        val data = ArrayList<GuestsItems>()
        guests?.forEach {
            val mData = GuestsItems(
                it?.firstName,
                it?.type,
                it?.type)
            data.add(mData)
        }
        return data
    }


    private fun mappingFacility(facility: String): java.util.ArrayList<FacilityHotelModel> {
        val data = ArrayList<FacilityHotelModel>()
        data.add(FacilityHotelModel("",facility))
        return data
    }

}