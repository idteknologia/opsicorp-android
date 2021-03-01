package opsigo.com.datalayer.model.accomodation.flight

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.model.accomodation.train.TripTrainsItem
import opsigo.com.datalayer.model.cart.TripFlightsItem

@Generated("com.robohorse.robopojogenerator")
data class ProgressFlightEnitity(

		@field:SerializedName("JobType")
	val jobType: String = "",

		@field:SerializedName("PnrId")
	val pnrId: String = "",

		@field:SerializedName("FlightId")
	val flightId: String = "",

		@field:SerializedName("Num")
	val num: Double = 0.0,

		@field:SerializedName("Text")
	val text: String = "",

		@field:SerializedName("TripFlight")
	val tripFlight: TripFlightsItem = TripFlightsItem(),

		@field:SerializedName("TrainId")
	val trainId: String = "",

		@field:SerializedName("TripTrain")
	val tripTrain: TripTrainsItem? = null
)