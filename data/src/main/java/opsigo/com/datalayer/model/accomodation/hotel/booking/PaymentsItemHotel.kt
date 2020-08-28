package opsigo.com.datalayer.model.accomodation.hotel.booking

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class PaymentsItemHotel(

	@field:SerializedName("Amount")
	val amount: Double = 0.0,

	@field:SerializedName("Currency")
	val currency: String = "",

	@field:SerializedName("Title")
	val title: String = "",

	@field:SerializedName("TripHotelId")
	val tripHotelId: String = "",

	@field:SerializedName("Id")
	val id: String = "",

	@field:SerializedName("Code")
	val code: String = "",

	@field:SerializedName("Seq")
	val seq: Int = 0
)