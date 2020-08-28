package opsigo.com.datalayer.model.test

import com.google.gson.annotations.SerializedName

data class RoutesItem(

	@field:SerializedName("Origin")
	val origin: String? = null,

	@field:SerializedName("Destination")
	val destination: String? = null,

	@field:SerializedName("DepartureDate")
	val departureDate: String? = null,

	@field:SerializedName("ArriveDate")
	val arriveDate: String? = null,

	@field:SerializedName("DepartDate")
	val departDate: String? = null
)