package opsigo.com.datalayer.request_model.accomodation.flight.reservation

import com.google.gson.annotations.SerializedName

data class RoutestRequest(

	@field:SerializedName("Origin")
	var origin: String? = null,

	@field:SerializedName("Destination")
	var destination: String? = null,

	@field:SerializedName("Transportation")
	var transportation: Int? = null,

	@field:SerializedName("DepartureDate")
	var departureDate: String? = null,

	@field:SerializedName("DepartureDateView")
	var DepartureDateView: String? = null

)
