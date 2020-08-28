package opsigo.com.datalayer.model.accomodation.train.seat.get_seat

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SeatRowsItemItem(

	@field:SerializedName("SeatStatus")
	val seatStatus: Int = 0,

	@field:SerializedName("SeatName")
	val seatName: String = "",

	@field:SerializedName("X")
	val X: Int = 0,

	@field:SerializedName("Y")
	val Y: Int = 0,

	@field:SerializedName("SeatNumber")
	val seatNumber: String = ""
)