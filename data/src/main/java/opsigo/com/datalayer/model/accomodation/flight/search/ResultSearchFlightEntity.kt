package opsigo.com.datalayer.model.accomodation.flight.search

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.model.accomodation.flight.search.multicity.MultiFlightsItem

@Generated("com.robohorse.robopojogenerator")
data class ResultSearchFlightEntity(

		@field:SerializedName("Origin")
	val origin: String? = null,

		@field:SerializedName("ReturnDate")
	val returnDate: String? = null,

		@field:SerializedName("Destination")
	val destination: String? = null,

		@field:SerializedName("ReturnFlights")
	val returnFlights: List<ReturnFlightsItem> = ArrayList(),

		@field:SerializedName("RequestId")
	val requestId: String? = null,

		@field:SerializedName("Airline")
	val airline: Int = 0,

		@field:SerializedName("OutgoingTrain")
	val outgoingTrain: List<Any?>? = null,

		@field:SerializedName("AirlineName")
	val airlineName: String? = null,

		@field:SerializedName("HaveUpdate")
	val haveUpdate: Boolean = false,

		@field:SerializedName("DepartureDate")
	val departureDate: String? = null,

		///List<OutgoingTrainItem> = ArrayList(),
		@field:SerializedName("DepartureFlights")
	val departureFlights: List<DepartureFlightsItem> = ArrayList(),

		@field:SerializedName("MultiFlights")
		val multiCity: List<MultiFlightsItem?>? = ArrayList(),

		@field:SerializedName("IncomingTrain")
	val incomingTrain: List<Any?>? = null

)