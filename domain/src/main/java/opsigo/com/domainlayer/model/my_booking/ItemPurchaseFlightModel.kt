package opsigo.com.domainlayer.model.my_booking


data class ItemFlightModel(
	val status: String? = null,
	val passengers: List<PassengersItem?>? = null,
	val destinationCity: String? = null,
	val num: Int? = null,
	val segments: List<SegmentsItem?>? = null,
	val originCity: String? = null,
	val pnrCode: String? = null
)

data class SegmentsItem(
	val origin: String? = null,
	val status: String? = null,
	val destination: String? = null,
	val airlineImageUrl: String? = null,
	val destinationCity: String? = null,
	val num: Int? = null,
	val airlineName: String? = null,
	val originCity: String? = null,
	val duration: String? = null,
	val classCategory: String? = null,
	val destinationAirport: String? = null,
	val pnrCode: String? = null,
	val originAirport: String? = null,
	val classCode: String? = null,
	val departureTimeDisplay: String? = null,
	val flightNumber: String? = null,
	val departureDate: String? = null,
	val departureDateDisplay: String? = null,
	val seq: Int? = null
)

