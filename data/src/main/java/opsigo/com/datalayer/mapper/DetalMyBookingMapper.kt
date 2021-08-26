package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.myboking.Data
import opsigo.com.datalayer.model.myboking.DetailMyBookingEntity
import opsigo.com.domainlayer.model.my_booking.*
import opsigo.com.domainlayer.model.summary.ItemTrainModel

class DetalMyBookingMapper {
    fun mapping(deserialize: DetailMyBookingEntity): DetailMyBookingModel {
        deserialize.apply {
            val data = DetailMyBookingModel(
                this.data?.priceDetails as List<PriceListModel>,
                this.data?.paymentStatusText,
                this.data?.itemType,
                bookingContactMapper(this.data),
                this.data?.code,
                itemMapper(this.data),
                this.data?.purchasedDate,
                this.data?.totalPaid,
                this.data?.paymentMethod,
                this.data?.id,
                this.data?.paymentStatus
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
                return data.hotel as ItemPurchaseHotelModel
            }
            else -> {
                //train
                return data?.trains as List<ItemTrainModel>
            }
        }
    }
}