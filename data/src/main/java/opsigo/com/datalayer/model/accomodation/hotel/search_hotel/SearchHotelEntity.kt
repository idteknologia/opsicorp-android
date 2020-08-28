package opsigo.com.datalayer.model.accomodation.hotel.search_hotel

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SearchHotelEntity(

		@field:SerializedName("OrderBy")
	val orderBy: String = "",

		@field:SerializedName("ErrorRoomKeys")
	val errorRoomKeys: String = "",

		@field:SerializedName("Radius")
	val radius: String = "",

		@field:SerializedName("Warning")
	val warning: String = "",

		@field:SerializedName("AllowIssued")
	val allowIssued: Boolean = false,

		@field:SerializedName("Message")
	val message: String = "",

		@field:SerializedName("Stars")
	val stars: List<StarsItem?>? = null,

		@field:SerializedName("ResponseId")
	val responseId: String = "",

		@field:SerializedName("Latitude")
	val latitude: String = "",

		@field:SerializedName("Count")
	val count: Int = 0,

		@field:SerializedName("Unit")
	val unit: String = "",

		@field:SerializedName("SecondaryLongitude")
	val secondaryLongitude: String = "",

		@field:SerializedName("MinPrice")
	val minPrice: Double = 0.0,

		@field:SerializedName("Longitude")
	val longitude: String = "",

		@field:SerializedName("Areas")
	val areas: List<AreasItem> = ArrayList(),

		@field:SerializedName("SecondaryLatitude")
	val secondaryLatitude: String = "",

		@field:SerializedName("Hotels")
	val hotels: List<HotelsItem> = ArrayList(),

		@field:SerializedName("MaxPage")
	val maxPage: Int = 0,

		@field:SerializedName("IsError")
	val isError: Boolean = false,

		@field:SerializedName("CorrelationId")
	val correlationId: String = "",

		@field:SerializedName("ErrorCode")
	val errorCode: String = "",

		@field:SerializedName("MaxPrice")
	val maxPrice: Double = 0.0
)