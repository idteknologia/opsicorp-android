package opsigo.com.datalayer.model.accomodation.hotel.search_hotel

import com.google.gson.annotations.SerializedName

data class CountryByRouteEntity(

	@field:SerializedName("CountryByRouteEntity")
	val countryByRouteEntity: List<CountryByRouteEntityItem> = ArrayList()
)

data class CityItem(

	@field:SerializedName("Latitude")
	val latitude: Double = 0.0,

	@field:SerializedName("CityName")
	val cityName: String? = null,

	@field:SerializedName("CityKey")
	val cityKey: String? = null,

	@field:SerializedName("Longitude")
	val longitude: Double = 0.0,

	@field:SerializedName("SearchByCityKey")
	val searchByCityKey: Boolean = false
)

data class CountryByRouteEntityItem(

	@field:SerializedName("CountryName")
	val countryName: String? = null,

	@field:SerializedName("City")
	val city: List<CityItem> = ArrayList(),

	@field:SerializedName("IsoCountryCode")
	val isoCountryCode: String? = null
)
