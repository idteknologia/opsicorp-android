package opsigo.com.datalayer.request_model.accomodation.hotel.search

import com.google.gson.annotations.SerializedName

data class PageHotelRequest(

	@field:SerializedName("OrderBy")
	var orderBy: Any? = null,

	@field:SerializedName("Origin")
	var origin: String? = null,

	@field:SerializedName("Destination")
	var destination: String? = null,

	@field:SerializedName("Area")
	var area: Any? = null,

	@field:SerializedName("Star")
	var star: String? = null,

	@field:SerializedName("TravelAgent")
	var travelAgent: String? = null,

	@field:SerializedName("Page")
	var page: Int? = null,

	@field:SerializedName("HotelName")
	var hotelName: Any? = null,

	@field:SerializedName("CorrelationId")
	var correlationId: String? = null,

	@field:SerializedName("MaxPrice")
	var maxPrice: String? = null,

	@field:SerializedName("MinPrice")
	var minPrice: String? = null,

	@field:SerializedName("DestinationName")
	var destinationName: String? = null,

	@field:SerializedName("DestinationCountry")
	var destinationCountry: String? = null,

	@field:SerializedName("IsShowPolicy")
	var isShowPolicy: Boolean = false



)
