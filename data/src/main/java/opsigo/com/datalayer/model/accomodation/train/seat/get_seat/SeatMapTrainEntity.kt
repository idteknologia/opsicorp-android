package opsigo.com.datalayer.model.accomodation.train.seat.get_seat

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SeatMapTrainEntity(

		@field:SerializedName("CoachName")
	val coachName: String = "",

		@field:SerializedName("Num")
	val num: Int = 0,

		@field:SerializedName("SeatRows")
	val seatRows: ArrayList<List<SeatRowsItemItem>> = ArrayList(),

		@field:SerializedName("Code")
	val code: String = ""
)