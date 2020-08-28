package opsigo.com.datalayer.request_model.reservation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class HeaderReservationRequest(

        @field:SerializedName("Origin")
	var origin: String = "",

        @field:SerializedName("ReturnDate")
	var returnDate: String = "",

        @field:SerializedName("StartDate")
	var startDate: String = "",

        @field:SerializedName("Destination")
	var destination: String = "",

        @field:SerializedName("TripParticipants")
	var tripParticipants: List<TripParticipantsItem?>? = null,

        @field:SerializedName("Type")
	var type: String ="",

        @field:SerializedName("TravelAgentAccount")
	var travelAgentAccount: String = "",

        @field:SerializedName("Purpose")
	var purpose: String = ""
)