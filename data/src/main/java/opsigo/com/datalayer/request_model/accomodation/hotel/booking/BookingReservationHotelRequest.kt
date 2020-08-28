package opsigo.com.datalayer.request_model.accomodation.hotel.booking

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class BookingReservationHotelRequest(

		@field:SerializedName("Origin")
	var origin: String = "",

		@field:SerializedName("Destination")
	var destination: String = "",

		@field:SerializedName("Remarks")
	var remarks: String = "",

		@field:SerializedName("ReasonCode")
	var reasonCode: String = "",

		@field:SerializedName("Members")
	var members: List<String> = ArrayList(),

		@field:SerializedName("Hotel")
	var hotel: HotelReservationHotelRequest = HotelReservationHotelRequest()
)