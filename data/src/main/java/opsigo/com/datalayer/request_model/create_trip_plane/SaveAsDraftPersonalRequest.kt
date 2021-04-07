package opsigo.com.datalayer.request_model.create_trip_plane

import com.google.gson.annotations.SerializedName

data class SaveAsDraftPersonalRequest(

	@field:SerializedName("Origin")
	var origin: String? = null,

	@field:SerializedName("Destination")
    var destination: String? = null,

	@field:SerializedName("TravelAgentAccount")
	var travelAgentAccount: String? = null,

	@field:SerializedName("Purpose")
	var purpose: String? = null
)
