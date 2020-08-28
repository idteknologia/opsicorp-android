package opsigo.com.datalayer.model.accomodation.hotel.country

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class CountryHotelEntity(

		@field:SerializedName("ErrorRoomKeys")
	var errorRoomKeys: String = "",

		@field:SerializedName("Warning")
	var warning: String = "",

		@field:SerializedName("Message")
	var message: String? = null,

		@field:SerializedName("ResponseId")
	var responseId: String = "",

		@field:SerializedName("IsError")
	var isError: Boolean = false,

		@field:SerializedName("Data")
	var data: List<DataCountryHotelEntity> = ArrayList(),

		@field:SerializedName("CorrelationId")
	var correlationId: String = "",

		@field:SerializedName("ErrorCode")
	var errorCode: String = ""
)