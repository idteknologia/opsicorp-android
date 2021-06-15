package opsigo.com.datalayer.model.accomodation.flight.reservation

import com.google.gson.annotations.SerializedName

data class ReservationFlightEntity(

	@field:SerializedName("timeLimit")
	val timeLimit: String = "",

	@field:SerializedName("TripCode")
	val tripCode: String = "",

	@field:SerializedName("TotalExpenditure")
	val totalExpenditure: Int = 0,

	@field:SerializedName("errorMessage")
	val errorMessage: String = "",

	@field:SerializedName("TripItem")
	val tripItem: TripItem? = null,
//	val tripItem: TripItem? = null,
	//

	@field:SerializedName("TripId")
	val tripId: String = "",

	@field:SerializedName("isSuccess")
	val isSuccess: Boolean = false
)