package opsigo.com.datalayer.request_model.create_trip_plane

import com.google.gson.annotations.SerializedName

data class SubmitRequest(

	@field:SerializedName("ReturnDate")
	var returnDate: String? = null,

	@field:SerializedName("Origin")
	var origin: String? = null,

	@field:SerializedName("Destination")
	var destination: String? = null,

	@field:SerializedName("TripParticipants")
	var tripParticipants: List<TripParticipantsPertaminaItem?>? = null,

	@field:SerializedName("TravelAgentAccount")
	var travelAgentAccount: String? = null,

	@field:SerializedName("Purpose")
	var purpose: String? = null,

	@field:SerializedName("BusinessTripType")
	var businessTripType: String? = null,

	@field:SerializedName("Routes")
	var routes: List<RoutesItem?>? = null,

	@field:SerializedName("Golper")
	var golper: Int? = null,

	@field:SerializedName("Remark")
	var remark: String? = null,

	@field:SerializedName("StartDate")
	var startDate: String? = null,

	@field:SerializedName("IsDomestic")
	var isDomestic: Boolean? = null,

	@field:SerializedName("WbsNo")
	var wbsNo: String? = null,

	@field:SerializedName("Type")
	var type: Int? = null,

	@field:SerializedName("TripAttachments")
	var tripAttachments: List<TripAttachmentsItemRequest?>? = null
)

