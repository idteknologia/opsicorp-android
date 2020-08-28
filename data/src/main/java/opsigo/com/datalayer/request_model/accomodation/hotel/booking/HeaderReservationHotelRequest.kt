package opsigo.com.datalayer.request_model.accomodation.hotel.booking

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class HeaderReservationHotelRequest(

		@field:SerializedName("Origin")
	var origin: String = "",

		@field:SerializedName("ReturnDate")
	var returnDate: String = "",

		@field:SerializedName("StartDate")
	var startDate: String = "",

		@field:SerializedName("Destination")
	var destination: String = "",

		@field:SerializedName("TripParticipants")
	var tripParticipants: List<TripParticipantsReservationHotelRequest> = ArrayList(),

		@field:SerializedName("Type")
	var type: Int = 0,

		@field:SerializedName("TravelAgentAccount")
	var travelAgentAccount: String = "",

		@field:SerializedName("Purpose")
	var purpose: String = "",

		@field:SerializedName("ID")
		var ID: String = "",


		@field:SerializedName("Code")
	var code: Any? = null
)