package opsigo.com.datalayer.request_model.accomodation.flight.reservation

import com.google.gson.annotations.SerializedName

data class IssuedAllRequest(

	@field:SerializedName("ModelPhone")
	var modelPhone: String? = null,

	@field:SerializedName("DeviceId")
	var deviceId: String? = null,

	@field:SerializedName("TripId")
	var tripId: String? = null
)
