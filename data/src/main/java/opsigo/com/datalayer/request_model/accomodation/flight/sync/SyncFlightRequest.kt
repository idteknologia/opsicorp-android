package opsigo.com.datalayer.request_model.accomodation.flight.sync

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SyncFlightRequest(

	@field:SerializedName("tripPlanId")
	var tripPlanId: String? = null,

	@field:SerializedName("pnrId")
	var pnrId: String? = null,

	@field:SerializedName("travelAgent")
	var travelAgent: String? = null,

	@field:SerializedName("flightId")
	var flightId: String? = null
)