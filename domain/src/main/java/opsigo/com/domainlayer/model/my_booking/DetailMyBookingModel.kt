package opsigo.com.domainlayer.model.my_booking

import java.util.ArrayList
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DetailMyBookingModel(
	var priceDetails: ArrayList<PriceListModel> = ArrayList(),
	var paymentStatusText: String? = null,
	var itemType: Int? = null,
	var bookingContact: PurchaseBookingContactModel? = null,
	var code: String? = null,
	var dataFlight: ArrayList<DetailFlightMyBookingModel> = ArrayList(),
	var dataHotel : ItemHotelPurchase = ItemHotelPurchase(),
	var dataTrain : ArrayList<ItemPurchaseTrainModel> = ArrayList(),
	var purchasedDate: String? = null,
	var totalPaid: Double = 0.0,
	var paymentMethod: String? = null,
	var id: String? = null,
	var paymentStatus: Int? = null
):Parcelable

