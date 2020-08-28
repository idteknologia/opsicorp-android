package opsigo.com.datalayer.request_model.accomodation.flight.seat

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SegmenFlightRequest(

	@field:SerializedName("Origin")
	val origin: String? = null,

	@field:SerializedName("Destination")
	val destination: String? = null,

	@field:SerializedName("ArriveTime")
	val arriveTime: String? = null,

	@field:SerializedName("Category")
	val category: String? = null,

	@field:SerializedName("AirlineImageUrl")
	val airlineImageUrl: String? = null,

	@field:SerializedName("Airline")
	val airline: Int? = null,

	@field:SerializedName("DestinationCity")
	val destinationCity: String? = null,

	@field:SerializedName("AirlineView")
	val airlineView: String? = null,

	@field:SerializedName("ClassId")
	val classId: String? = null,

	@field:SerializedName("OriginCity")
	val originCity: String? = null,

	@field:SerializedName("DestinationAirport")
	val destinationAirport: String? = null,

	@field:SerializedName("DestinationTerminal")
	val destinationTerminal: String? = null,

	@field:SerializedName("DepartDate")
	val departDate: String? = null,

	@field:SerializedName("ClassCode")
	val classCode: String? = null,

	@field:SerializedName("DepartTime")
	val departTime: String? = null,

	@field:SerializedName("FlightId")
	val flightId: String? = null,

	@field:SerializedName("FlightNumber")
	val flightNumber: String? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("ArriveDate")
	val arriveDate: String? = null
)