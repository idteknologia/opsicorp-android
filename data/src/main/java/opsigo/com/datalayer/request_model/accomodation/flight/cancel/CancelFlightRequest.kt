package opsigo.com.datalayer.request_model.accomodation.flight.cancel

import com.google.gson.annotations.SerializedName

data class CancelFlightRequest(

	@field:SerializedName("tripPlanId")
	var tripPlanId: String? = null,

	@field:SerializedName("pnrId")
	var pnrId: String? = null,

	@field:SerializedName("travelAgent")
	var travelAgent: String? = null,

	@field:SerializedName("flightId")
	var flightId: String? = null
)
