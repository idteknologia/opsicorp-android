package opsigo.com.datalayer.model.accomodation.hotel.airport

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class AirportEntity(

		@field:SerializedName("Id")
	val Id: String = "",

		@field:SerializedName("Code")
		val Code: String = "",

		@field:SerializedName("CityCode")
		val CityCode: String = "",

		@field:SerializedName("CountryCode")
		val CountryCode: String = "",

		@field:SerializedName("AirportName")
		val AirportName: String = "",

		@field:SerializedName("Locale")
		val Locale: Double = 0.0,

		@field:SerializedName("CorrelationId")
		val IsNonGDS: Boolean = false,

		@field:SerializedName("Longitude")
		val Longitude: String = "",

		@field:SerializedName("Latitude")
		val Latitude: String = "",

		@field:SerializedName("CityName")
		val CityName: String = "",

		@field:SerializedName("LocalView")
	val LocalView: String = ""

)