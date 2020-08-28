package opsigo.com.datalayer.model.accomodation.train.reservation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.model.TripItemsItem

@Generated("com.robohorse.robopojogenerator")
data class BookingTrainEntity(

		@field:SerializedName("timeLimit")
	val timeLimit: String = "",

		@field:SerializedName("TripCode")
	val tripCode: String = "",

		@field:SerializedName("TotalExpenditure")
	val totalExpenditure: Double = 0.0,

		@field:SerializedName("errorMessage")
	val errorMessage: String = "",

		@field:SerializedName("TripItem")
	val tripItem: TripItemsItem? = null,

		@field:SerializedName("TripId")
	val tripId: String = "",

		@field:SerializedName("isSuccess")
	val isSuccess: Int = 0
)