package opsigo.com.datalayer.model.accomodation.flight.seat

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ResultSeat(

		@field:SerializedName("RsFlightSeats")
	val rsFlightSeats: List<RsFlightSeatsItem> = ArrayList(),

		@field:SerializedName("IsError")
	val isError: Boolean = false,

		@field:SerializedName("ErrorMessage")
	val errorMessage: Any? = null
)