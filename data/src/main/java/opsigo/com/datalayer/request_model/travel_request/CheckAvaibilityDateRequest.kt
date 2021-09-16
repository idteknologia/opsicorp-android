package opsigo.com.datalayer.request_model.travel_request

import com.google.gson.annotations.SerializedName

data class CheckAvaibilityDateRequest(

	@field:SerializedName("StartDate")
	var startDate: String? = null,

	@field:SerializedName("EndDate")
	var endDate: String? = null,

	@field:SerializedName("TripCodeOld")
	var tripCodeOld: String? = null

)
