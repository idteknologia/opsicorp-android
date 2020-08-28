package opsigo.com.datalayer.model.calendar

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class CalendarHolidayEntity(

		@field:SerializedName("data")
	val data: List<DataItemHolidayEntity> = ArrayList(),

		@field:SerializedName("message")
	val message: String = ""
)