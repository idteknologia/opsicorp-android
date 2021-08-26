package opsigo.com.domainlayer.model.my_booking

import java.util.ArrayList

data class DetailMyBookingModel(
	val priceDetails: ArrayList<PriceListModel> = ArrayList(),
	val paymentStatusText: String? = null,
	val itemType: Int? = null,
	val bookingContact: PurchaseBookingContactModel? = null,
	val code: String? = null,
	val dataItem: Any? = null,
	val purchasedDate: String? = null,
	val totalPaid: Double = 0.0,
	val paymentMethod: String? = null,
	val id: String? = null,
	val paymentStatus: Int? = null
)

