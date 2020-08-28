package opsigo.com.datalayer.model.accomodation.flight.seat

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SeatMapFlightEntity(

		@field:SerializedName("result")
	val result: ResultSeat = ResultSeat(),

		@field:SerializedName("errorMessage")
	val errorMessage: String? = ""
)