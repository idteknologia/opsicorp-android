package opsigo.com.datalayer.model.accomodation.flight.ailine_code

import com.google.gson.annotations.SerializedName

data class RoutesItem(

	@field:SerializedName("Origin")
	val origin: String? = null,

	@field:SerializedName("Destination")
	val destination: String? = null,

	@field:SerializedName("DepartDate")
	val departDate: String? = null
)