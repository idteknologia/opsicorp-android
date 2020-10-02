package opsigo.com.datalayer.model.accomodation.flight.ailine_code

import com.google.gson.annotations.SerializedName

data class AirlineCodeEntity(

	@field:SerializedName("listSchedule")
	val listSchedule: List<ListScheduleItem?>? = null
)