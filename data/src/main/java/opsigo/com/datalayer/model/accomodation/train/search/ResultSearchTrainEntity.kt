package opsigo.com.datalayer.model.accomodation.train.search

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.model.accomodation.train.search.OutgoingTrainItem

@Generated("com.robohorse.robopojogenerator")
data class ResultSearchTrainEntity(

		@field:SerializedName("Origin")
	val origin: String = "",

		@field:SerializedName("ReturnDate")
	val returnDate: String = "",

		@field:SerializedName("Destination")
	val destination: String = "",

		@field:SerializedName("ReturnFlights")
	val returnFlights: List<Any> = ArrayList(),

		@field:SerializedName("RequestId")
	val requestId: String = "",

		@field:SerializedName("Airline")
	val airline: Int = 0,

		@field:SerializedName("OutgoingTrain")
	val outgoingTrain: List<OutgoingTrainItem> = ArrayList(),

		@field:SerializedName("AirlineName")
	val airlineName: String = "",

		@field:SerializedName("HaveUpdate")
	val haveUpdate: Boolean = false,

		@field:SerializedName("DepartureDate")
	val departureDate: String = "",

		@field:SerializedName("DepartureFlights")
	val departureFlights: List<Any> = ArrayList(),

		@field:SerializedName("IncomingTrain")
	val incomingTrain: List<Any> = ArrayList()
)