package opsigo.com.datalayer.request_model.travel_request

import com.google.gson.annotations.SerializedName

data class EstimatedRequest(

	@field:SerializedName("StartDate")
	val startDate: String? = null,

	@field:SerializedName("IsDomestic")
	val isDomestic: Boolean? = null,

	@field:SerializedName("TripType")
	val tripType: String? = null,

	@field:SerializedName("WithPartner")
	val withPartner: Boolean? = null,

	@field:SerializedName("Purpose")
	val purpose: String? = null,

	@field:SerializedName("Routes")
	val routes: List<RoutesItem?>? = null,

	@field:SerializedName("EndDate")
	val endDate: String? = null,

	@field:SerializedName("Golper")
	val golper: Int? = null
)

data class RoutesItem(

	@field:SerializedName("Origin")
	val origin: String? = null,

	@field:SerializedName("Destination")
	val destination: String? = null,

	@field:SerializedName("Transportation")
	val transportation: String? = null,

	@field:SerializedName("DepartureDateView")
	val departureDateView: String? = null
)
