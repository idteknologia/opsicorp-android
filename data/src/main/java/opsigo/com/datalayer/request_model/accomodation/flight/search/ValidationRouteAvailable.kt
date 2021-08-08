package opsigo.com.datalayer.request_model.accomodation.flight.search

import com.google.gson.annotations.SerializedName
import opsigo.com.domainlayer.model.accomodation.flight.airline_code.ListScheduleItem

data class ValidationRouteAvailable(

	@field:SerializedName("Schedule")
	var schedule: ArrayList<ListScheduleItem> = ArrayList(),

	@field:SerializedName("tripId")
	var tripId: String? = null
)
