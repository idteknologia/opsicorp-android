package opsigo.com.datalayer.request_model.create_trip_plane

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SubmitTripPlant(

		@field:SerializedName("StartDate")
		var startDate: String = "",

		@field:SerializedName("ReturnDate")
		var returnDate: String = "",

		@field:SerializedName("Origin")
		var origin: String = "",

		@field:SerializedName("Destination")
		var destination: String = "",

		@field:SerializedName("Type")
		var type: String = "",

		@field:SerializedName("TripParticipants")
		var tripParticipants: List<TripParticipantsItem> = ArrayList(),

		@field:SerializedName("TravelAgentAccount")
		var travelAgentAccount: String = "",

		@field:SerializedName("Purpose")
		var purpose: String = "",

		@field:SerializedName("Id")
		var id: String = "",

		@field:SerializedName("Code")
		var code: String = "",

		@field:SerializedName("Contact")
		var contact: ContactRequest = ContactRequest()
)