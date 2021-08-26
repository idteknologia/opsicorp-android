package opsigo.com.datalayer.model.myboking

import com.google.gson.annotations.SerializedName

data class ListMyBookingEntity(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItem> = ArrayList(),

	@field:SerializedName("errorMessage")
	val errorMessage: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataItem(

	@field:SerializedName("Origin")
	val origin: String? = null,

	@field:SerializedName("Destination")
	val destination: String? = null,

	@field:SerializedName("IsRoundtrip")
	val isRoundtrip: Boolean = false,

	@field:SerializedName("PaymentStatusText")
	val paymentStatusText: String? = null,

	@field:SerializedName("DestinationCity")
	val destinationCity: String? = null,

	@field:SerializedName("ItemType")
	val itemType: Int = 0,

	@field:SerializedName("ItemTypeText")
	val itemTypeText: String? = null,

	@field:SerializedName("OriginCity")
	val originCity: String? = null,

	@field:SerializedName("Code")
	val code: String? = null,

	@field:SerializedName("Created")
	val created: String? = null,

	@field:SerializedName("TotalPaid")
	val totalPaid: Double? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("HotelName")
	val hotelName: Any? = null,

	@field:SerializedName("PaymentStatus")
	val paymentStatus: Int? = null
)
