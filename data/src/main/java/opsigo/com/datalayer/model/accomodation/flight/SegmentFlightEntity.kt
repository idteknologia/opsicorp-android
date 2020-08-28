package opsigo.com.datalayer.model.accomodation.flight

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SegmentFlightEntity(

	@field:SerializedName("Origin")
	val origin: String = "",

	@field:SerializedName("IsSecondaryLevel")
	val isSecondaryLevel: Boolean = false,

	@field:SerializedName("IsAdvanceBooking")
	val isAdvanceBooking: Boolean = false,

	@field:SerializedName("IsComply")
	val isComply: Boolean = false,

	@field:SerializedName("Destination")
	val destination: String = "",

	@field:SerializedName("ArriveTime")
	val arriveTime: String = "",

	@field:SerializedName("Category")
	val category: String = "",

	@field:SerializedName("Airline")
	val airline: Int = 0,

	@field:SerializedName("AirlineName")
	val airlineName: String = "",

	@field:SerializedName("IsLowestFare")
	val isLowestFare: Boolean = false,

	@field:SerializedName("IsSecuritySensivity")
	val isSecuritySensivity: Boolean = false,

	@field:SerializedName("ArriveDateTime")
	val arriveDateTime: String = "",

	@field:SerializedName("DepartTime")
	val departTime: String = "",

	@field:SerializedName("CategoryCabin")
	val categoryCabin: String = "",

	@field:SerializedName("DescAdvanceBooking")
	val descAdvanceBooking: String = "",

	@field:SerializedName("AirportOrigin")
	val airportOrigin: String = "",

	@field:SerializedName("DestinationName")
	val destinationName: String = "",

	@field:SerializedName("ArriveDate")
	val arriveDate: String = "",

	@field:SerializedName("LowestFare")
	val lowestFare: Double = 0.0,

	@field:SerializedName("TransitDuration")
	val transitDuration: String = "",

	@field:SerializedName("AirlineImageUrl")
	val airlineImageUrl: String = "",

	@field:SerializedName("Num")
	val num: Int = 0,

	@field:SerializedName("DepartDateTime")
	val departDateTime: String = "",

	@field:SerializedName("CityDestination")
	val cityDestination: String = "",

	@field:SerializedName("IsAirlineCompliance")
	val isAirlineCompliance: Boolean = false,

	@field:SerializedName("Duration")
	val duration: String = "",

	@field:SerializedName("DepartDate")
	val departDate: String = "",

	@field:SerializedName("OriginName")
	val originName: String = "",

	@field:SerializedName("ClassCode")
	val classCode: String = "",

	@field:SerializedName("FlightAvailability")
	val flightAvailability: String = "",

	@field:SerializedName("FlightNumber")
	val flightNumber: String = "",

	@field:SerializedName("AirportDestination")
	val airportDestination: String = "",

	@field:SerializedName("CityOrigin")
	val cityOrigin: String = "",

	@field:SerializedName("CountryOrigin")
	val countryOrigin: String = "",

	@field:SerializedName("Id")
	val id: String = "",

	@field:SerializedName("IsRestrictedDest")
	val isRestrictedDest: Boolean = false,

	@field:SerializedName("DescSecuritySensitivity")
	val descSecuritySensitivity: String = "",

	@field:SerializedName("TripFlightId")
	val tripFlightId: String = "",

	@field:SerializedName("CountryDestination")
	val countryDestination: String = "",

	@field:SerializedName("PrgNum")
	val progressFlight: String = "",

	@field:SerializedName("Seq")
	val seq: Int = 0

)