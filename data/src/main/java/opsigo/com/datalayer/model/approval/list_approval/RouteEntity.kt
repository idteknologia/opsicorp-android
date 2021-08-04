package opsigo.com.datalayer.model.approval.list_approval

import com.google.gson.annotations.SerializedName

data class RouteEntity(

	@field:SerializedName("Origin")
	val origin: String? = null,

	@field:SerializedName("Destination")
	val destination: String? = null,

	@field:SerializedName("DestinationCountry")
	val destinationCountry: Any? = null,

	@field:SerializedName("TripPlanId")
	val tripPlanId: String? = null,

	@field:SerializedName("Transportation")
	val transportation: Int? = null,

	@field:SerializedName("DepartureDateShort")
	val departureDateShort: String? = null,

	@field:SerializedName("Region")
	val region: String? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("DepartureDate")
	val departureDate: String? = null,

	@field:SerializedName("DepartureDateView")
	val departureDateView: String? = null,

	@field:SerializedName("TransportationView")
	val transportationView: String? = null,

	@field:SerializedName("Seq")
	val seq: Int? = null,

	@field:SerializedName("ValidateRange")
	val validateRange: Boolean? = null
)
