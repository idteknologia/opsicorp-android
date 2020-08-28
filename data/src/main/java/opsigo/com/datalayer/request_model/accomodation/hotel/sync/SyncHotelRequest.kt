package opsigo.com.datalayer.request_model.accomodation.hotel.sync

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SyncHotelRequest(

	@field:SerializedName("HotelId")
	var hotelId: String = "",

	@field:SerializedName("PnrId")
	var pnrId: String = "",

	@field:SerializedName("TravelAgent")
	var travelAgent: String = "",

	@field:SerializedName("MaxRetry")
	var maxRetry: Int = 0
)