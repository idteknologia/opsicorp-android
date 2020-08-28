package opsigo.com.datalayer.model.accomodation.hotel.confirmation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ConfirmationHotelEntity(

	@field:SerializedName("ErrorRoomKeys")
	val errorRoomKeys: String = "",

	@field:SerializedName("Warning")
	val warning: String = "",

	@field:SerializedName("Message")
	val message: String = "",

	@field:SerializedName("ConfimStatus")
	val confimStatus: String = "",

	@field:SerializedName("LogUrl")
	val logUrl: String = "",

	@field:SerializedName("ResponseId")
	val responseId: String = "",

	@field:SerializedName("IsError")
	val isError: Boolean = false,

	@field:SerializedName("CorrelationId")
	val correlationId: String = "",

	@field:SerializedName("ErrorCode")
	val errorCode: String = "",

	@field:SerializedName("Confirmation")
	val confirmation: DataConfirmationHotel = DataConfirmationHotel()
)