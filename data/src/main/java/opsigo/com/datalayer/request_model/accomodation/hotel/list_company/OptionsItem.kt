package opsigo.com.datalayer.request_model.accomodation.hotel.list_company

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class OptionsItem(

	@field:SerializedName("Type")
	val type: String? = "",

	@field:SerializedName("Latitude")
	val latitude: Double? = 0.0,

	@field:SerializedName("CityName")
	val cityName: Any? = "",

	@field:SerializedName("Longitude")
	val longitude: Double? = 0.0,

	@field:SerializedName("Name")
	val name: String? = ""
)