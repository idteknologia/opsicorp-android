package opsigo.com.datalayer.model.test

import com.google.gson.annotations.SerializedName

data class FlightBookingCodesItem(

	@field:SerializedName("BookingCode")
	val bookingCode: String? = null,

	@field:SerializedName("BookingStatus")
	val bookingStatus: String? = null
)