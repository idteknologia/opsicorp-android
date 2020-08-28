package opsigo.com.datalayer.model.accomodation.hotel.validation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ValidationHotelEntity(

	@field:SerializedName("bookingResult")
	val bookingResult: BookingResult = BookingResult(),

	@field:SerializedName("msgDoubleBook")
	val msgDoubleBook: String? = "",

	@field:SerializedName("isViolated")
	val isViolated: Boolean = false,

	@field:SerializedName("message")
	val message: String = "",

	@field:SerializedName("isDoubleBooking")
	val isDoubleBooking: Boolean = false
)