package opsigo.com.datalayer.request_model.create_trip_plane

import com.google.gson.annotations.SerializedName

data class CashAdvanceRequest(

	@field:SerializedName("IsDomestic")
	var isDomestic: Boolean = false,

	@field:SerializedName("StartDate")
	var startDate: String? = "",

	@field:SerializedName("ActivityType")
	var activityType: String? = "",

	@field:SerializedName("EndDate")
	var endDate: String? = ""
)
