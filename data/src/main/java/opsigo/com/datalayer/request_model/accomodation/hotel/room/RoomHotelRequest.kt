package opsigo.com.datalayer.request_model.accomodation.hotel.room

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class RoomHotelRequest(

	@field:SerializedName("HotelKey")
	var hotelKey: String? = null,

	@field:SerializedName("TravelAgent")
	var travelAgent: String? = null,

	@field:SerializedName("CorrelationId")
	var correlationId: String? = null
)