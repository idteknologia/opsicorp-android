package opsigo.com.datalayer.request_model.accomodation.hotel.confirmation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ConfirmHotelRequest(

	@field:SerializedName("HotelKey")
	var hotelKey: String = "",

	@field:SerializedName("RoomKey")
	var roomKey: String = "",

	@field:SerializedName("TravelAgent")
	var travelAgent: String = "",

	@field:SerializedName("CorrelationId")
	var correlationId: String = ""
)