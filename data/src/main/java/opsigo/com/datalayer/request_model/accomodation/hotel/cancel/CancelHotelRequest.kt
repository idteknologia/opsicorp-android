package opsigo.com.datalayer.request_model.accomodation.hotel.cancel

import com.google.gson.annotations.SerializedName

data class CancelHotelRequest(

	@field:SerializedName("HotelId")
    var hotelId: String? = null,

	@field:SerializedName("PnrId")
	var pnrId: String? = null,

	@field:SerializedName("TravelAgent")
	var travelAgent: String? = null,

	@field:SerializedName("TripItemId")
	var tripItemId: String? = null,

	@field:SerializedName("TripId")
	var tripId: String? = null
)
