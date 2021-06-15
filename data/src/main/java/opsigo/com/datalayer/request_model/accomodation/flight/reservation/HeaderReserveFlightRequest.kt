package opsigo.com.datalayer.request_model.accomodation.flight.reservation

import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.request_model.create_trip_plane.TripAttachmentsItemRequest
import opsigo.com.datalayer.request_model.reservation.TripParticipantsItem

data class HeaderReserveFlightRequest(

	//@field:SerializedName("TripParticipants")
	//val tripParticipants: List<TripParticipantsItem?>? = null,
//	@field:SerializedName("Remarks")
//	val remarks: List<String?>? = null,

	@field:SerializedName("StartDate")
	var startDate: String = "",

	@field:SerializedName("ReturnDate")
	var returnDate: String = "",

	@field:SerializedName("Origin")
	var origin: String = "",

	@field:SerializedName("Destination")
	var destination: String = "",

	@field:SerializedName("Type")
	var type: Int = 0,

	@field:SerializedName("TripParticipants")
	var tripParticipants: List<TripParticipantsItem> = ArrayList(),

	@field:SerializedName("TravelAgentAccount")
	var travelAgentAccount: String = "",

	@field:SerializedName("Id")
	var idTripPlan: String = "",

	@field:SerializedName("Code")
	var codeTripPlan: String = "",


	@field:SerializedName("Purpose")
	var purpose: String = ""
)