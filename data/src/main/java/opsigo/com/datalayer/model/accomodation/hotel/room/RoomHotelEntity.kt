package opsigo.com.datalayer.model.accomodation.hotel.room

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class RoomHotelEntity(

		@field:SerializedName("ErrorRoomKeys")
	val errorRoomKeys: String = "",

		@field:SerializedName("Warning")
	val warning: String = "",

		@field:SerializedName("Message")
	val message: String = "",

		@field:SerializedName("ResponseId")
	val responseId: String = "",

		@field:SerializedName("IsError")
	val isError: Boolean = false,

		@field:SerializedName("CancelPolicy")
	val cancelPolicy: CancelPolicyHotel = CancelPolicyHotel(),

		@field:SerializedName("CorrelationId")
	val correlationId: String = "",

		@field:SerializedName("ErrorCode")
	val errorCode: String = "",

		@field:SerializedName("Display")
	val display: Boolean = false
)