package opsigo.com.datalayer.model.calendar

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class DataItemHolidayEntity(

	@field:SerializedName("country")
	val country: String = "",

	@field:SerializedName("code")
	val code: String = "",

	@field:SerializedName("year")
	val year: Int = 0,

	@field:SerializedName("nameholiday")
	val nameholiday: String = "",

	@field:SerializedName("id")
	val id: String = "",

	@field:SerializedName("dateholiday")
	val dateholiday: String = ""
)