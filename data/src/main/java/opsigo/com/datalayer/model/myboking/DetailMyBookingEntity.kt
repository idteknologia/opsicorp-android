package opsigo.com.datalayer.model.myboking

import com.google.gson.annotations.SerializedName

data class DetailMyBookingEntity(

	@field:SerializedName("data")
	val data: Data = Data(),

	@field:SerializedName("errorMessage")
	val errorMessage: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class Data(

	@field:SerializedName("PriceDetails")
	val priceDetails: ArrayList<PriceMyBookingEntity> = ArrayList(),

	@field:SerializedName("Trains")
	val trains: ArrayList<TrainItemMyBookingEntity> = ArrayList(),

	@field:SerializedName("PaymentStatusText")
	val paymentStatusText: String? = null,

	@field:SerializedName("ItemType")
	val itemType: Int? = null,

	@field:SerializedName("ItemTypeText")
	val itemTypeText: String? = null,

	@field:SerializedName("BookingContact")
	val bookingContact: BookingContact? = null,

	@field:SerializedName("Code")
	val code: String? = null,

	@field:SerializedName("Flights")
	val flights: ArrayList<FlightItemMyBookingEntity>? = null,

	@field:SerializedName("PurchasedDate")
	val purchasedDate: String? = null,

	@field:SerializedName("TotalPaid")
	val totalPaid: Double = 0.0,

	@field:SerializedName("PaymentMethod")
	val paymentMethod: String? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("PaymentStatus")
	val paymentStatus: Int? = null,

	@field:SerializedName("Hotel")
	val hotel: HotelItemMyBookingEntity = HotelItemMyBookingEntity()
)

data class BookingContact(

	@field:SerializedName("Email")
	val email: String? = null,

	@field:SerializedName("FirstName")
	val firstName: String? = null,

	@field:SerializedName("FullName")
	val fullName: String? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("LastName")
	val lastName: String? = null,

	@field:SerializedName("MobilePhone")
	val mobilePhone: String? = null
)

data class PriceMyBookingEntity(

	@field:SerializedName("Amount")
	val amount: Double = 0.0,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("Index")
	val index: Int? = null
)
