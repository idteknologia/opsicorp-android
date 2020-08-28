package opsigo.com.datalayer.model.accomodation.train

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.model.TripFlightsItem

@Generated("com.robohorse.robopojogenerator")
data class ProgressTrainEnitity(

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