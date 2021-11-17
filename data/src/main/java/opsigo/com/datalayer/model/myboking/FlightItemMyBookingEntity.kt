package opsigo.com.datalayer.model.myboking

import com.google.gson.annotations.SerializedName

data class FlightItemMyBookingEntity(

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("Passengers")
	val passengers: List<PassengersFlightItem?>? = null,

	@field:SerializedName("DestinationCity")
	val destinationCity: Any? = null,

	@field:SerializedName("Num")
	val num: Int? = null,

	@field:SerializedName("Segments")
	val segments: List<SegmentsItem?>? = null,

	@field:SerializedName("OriginCity")
	val originCity: Any? = null,

	@field:SerializedName("PnrCode")
	val pnrCode: String? = null
)

data class PassengersFlightItem(

	@field:SerializedName("Type")
	val type: String? = null,

	@field:SerializedName("SeatName")
	val seatName: Any? = null,

	@field:SerializedName("FirstName")
	val firstName: String? = null,

	@field:SerializedName("IdNumber")
	val idNumber: Any? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("Index")
	val index: Int? = null,

	@field:SerializedName("LastName")
	val lastName: String? = null,

	@field:SerializedName("SeatNumber")
	val seatNumber: Any? = null,

	@field:SerializedName("IdType")
	val idType: Any? = null,

	@field:SerializedName("BirthDate")
	val birthDate: String? = null
)

data class SegmentsItem(

	@field:SerializedName("Origin")
	val origin: String? = null,

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("Destination")
	val destination: String? = null,

	@field:SerializedName("AirlineImageUrl")
	val airlineImageUrl: String? = null,

	@field:SerializedName("DestinationCity")
	val destinationCity: Any? = null,

	@field:SerializedName("Num")
	val num: Int? = null,

	@field:SerializedName("AirlineName")
	val airlineName: String? = null,

	@field:SerializedName("OriginCity")
	val originCity: Any? = null,

	@field:SerializedName("Duration")
	val duration: String? = null,

	@field:SerializedName("ClassCategory")
	val classCategory: String? = null,

	@field:SerializedName("DestinationAirport")
	val destinationAirport: String? = null,

	@field:SerializedName("PnrCode")
	val pnrCode: String? = null,

	@field:SerializedName("OriginAirport")
	val originAirport: String? = null,

	@field:SerializedName("ClassCode")
	val classCode: String? = null,

	@field:SerializedName("DepartureTimeDisplay")
	val departureTimeDisplay: String? = null,

	@field:SerializedName("FlightNumber")
	val flightNumber: String? = null,

	@field:SerializedName("DepartureDate")
	val departureDate: String? = null,

	@field:SerializedName("DepartureDateDisplay")
	val departureDateDisplay: String? = null,

	@field:SerializedName("Seq")
	val seq: Int? = null
)
