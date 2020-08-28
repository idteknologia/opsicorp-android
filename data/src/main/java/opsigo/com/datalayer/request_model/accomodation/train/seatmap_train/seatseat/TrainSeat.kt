package opsigo.com.datalayer.request_model.accomodation.train.seatmap_train.seatseat

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class TrainSeat(

	@field:SerializedName("SeatName")
	var seatName: String = "",

	@field:SerializedName("X")
	var X: String = "",

	@field:SerializedName("Y")
	var Y: String = "",

	@field:SerializedName("SeatNumber")
	var seatNumber: String = ""
)