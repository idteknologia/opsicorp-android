package opsigo.com.datalayer.model.test

import com.google.gson.annotations.SerializedName

data class SegmentsItem(

	@field:SerializedName("Origin")
	val origin: String? = null,

	@field:SerializedName("IsSecondaryLevel")
	val isSecondaryLevel: Boolean? = null,

	@field:SerializedName("IsAdvanceBooking")
	val isAdvanceBooking: Boolean? = null,

	@field:SerializedName("IsComply")
	val isComply: Boolean? = null,

	@field:SerializedName("Destination")
	val destination: String? = null,

	@field:SerializedName("ArriveTime")
	val arriveTime: String? = null,

	@field:SerializedName("Category")
	val category: String? = null,

	@field:SerializedName("Airline")
	val airline: Int? = null,

	@field:SerializedName("AirlineName")
	val airlineName: String? = null,

	@field:SerializedName("IsLowestFare")
	val isLowestFare: Boolean? = null,

	@field:SerializedName("IsSecuritySensivity")
	val isSecuritySensivity: Boolean? = null,

	@field:SerializedName("ArriveDateTime")
	val arriveDateTime: String? = null,

	@field:SerializedName("DepartTime")
	val departTime: String? = null,

	@field:SerializedName("CategoryCabin")
	val categoryCabin: String? = null,

	@field:SerializedName("DescAdvanceBooking")
	val descAdvanceBooking: String? = null,

	@field:SerializedName("AirportOrigin")
	val airportOrigin: String? = null,

	@field:SerializedName("DestinationName")
	val destinationName: String? = null,

	@field:SerializedName("OperatingAirline")
	val operatingAirline: Any? = null,

	@field:SerializedName("ArriveDate")
	val arriveDate: String? = null,

	@field:SerializedName("DepartureDate")
	val departureDate: Any? = null,

	@field:SerializedName("LowestFare")
	val lowestFare: Double? = null,

	@field:SerializedName("TransitDuration")
	val transitDuration: Any? = null,

	@field:SerializedName("AirlineImageUrl")
	val airlineImageUrl: String? = null,

	@field:SerializedName("Num")
	val num: Int? = null,

	@field:SerializedName("DepartDateTime")
	val departDateTime: String? = null,

	@field:SerializedName("CityDestination")
	val cityDestination: String? = null,

	@field:SerializedName("IsAirlineCompliance")
	val isAirlineCompliance: Boolean? = null,

	@field:SerializedName("Duration")
	val duration: String? = null,

	@field:SerializedName("DepartDate")
	val departDate: String? = null,

	@field:SerializedName("OperatingFlightNumber")
	val operatingFlightNumber: Any? = null,

	@field:SerializedName("OriginName")
	val originName: String? = null,

	@field:SerializedName("ClassCode")
	val classCode: String? = null,

	@field:SerializedName("ArrivalDate")
	val arrivalDate: Any? = null,

	@field:SerializedName("FlightAvailability")
	val flightAvailability: Any? = null,

	@field:SerializedName("FlightNumber")
	val flightNumber: String? = null,

	@field:SerializedName("AirportDestination")
	val airportDestination: String? = null,

	@field:SerializedName("CarrierCode")
	val carrierCode: Any? = null,

	@field:SerializedName("CityOrigin")
	val cityOrigin: String? = null,

	@field:SerializedName("CountryOrigin")
	val countryOrigin: String? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("IsRestrictedDest")
	val isRestrictedDest: Boolean? = null,

	@field:SerializedName("DescSecuritySensitivity")
	val descSecuritySensitivity: String? = null,

	@field:SerializedName("TripFlightId")
	val tripFlightId: String? = null,

	@field:SerializedName("CountryDestination")
	val countryDestination: String? = null,

	@field:SerializedName("Seq")
	val seq: Int? = null
)