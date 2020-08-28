package opsigo.com.datalayer.model.accomodation.train.seat.set_seat

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class TrainSeatMapRequest(

	@field:SerializedName("SeatName")
	val seatName: String = "",

	@field:SerializedName("X")
	val X: String = "",

	@field:SerializedName("Y")
	val Y: String = "",

	@field:SerializedName("SeatNumber")
	val seatNumber: String = ""
)