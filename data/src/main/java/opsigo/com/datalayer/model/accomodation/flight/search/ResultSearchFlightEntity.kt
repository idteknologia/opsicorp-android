package opsigo.com.datalayer.model.accomodation.flight.search

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ResultSearchFlightEntity(

		@field:SerializedName("Origin")
	val origin: String = "",

		@field:SerializedName("ReturnDate")
	val returnDate: String = "",

		@field:SerializedName("Destination")
	val destination: String = "",

	@field:SerializedName("ReturnFlights")
	val returnFlights: List<ReturnFlightsItem> = ArrayList(),

		@field:SerializedName("RequestId")
	val requestId: String = "",

		@field:SerializedName("Airline")
	val airline: Int = 0,

		@field:SerializedName("OutgoingTrain")
	val outgoingTrain: List<Any?>? = null,

		@field:SerializedName("AirlineName")
	val airlineName: String = "",

		@field:SerializedName("HaveUpdate")
	val haveUpdate: Boolean = false,

		@field:SerializedName("DepartureDate")
	val departureDate: String = "",

		///List<OutgoingTrainItem> = ArrayList(),
		@field:SerializedName("DepartureFlights")
	val departureFlights: List<DepartureFlightsItem> = ArrayList(),

		@field:SerializedName("IncomingTrain")
	val incomingTrain: List<Any?>? = null


)