package opsigo.com.datalayer.model.accomodation.flight.search

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.model.accomodation.flight.search.transit.TransitFlightsEntity

@Generated("com.robohorse.robopojogenerator")
data class ReturnFlightsItem(

		@field:SerializedName("Origin")
	val origin: String? = null,

		@field:SerializedName("IsComply")
	val isComply: Boolean = false,

		@field:SerializedName("ArriveTime")
	val arriveTime: String? = null,

		@field:SerializedName("Airline")
	val airline: Int = 0,

		@field:SerializedName("FareBreakdowns")
	val fareBreakdowns: Any? = null,

		@field:SerializedName("DurationIncludeTransit")
	val durationIncludeTransit: String? = null,

		@field:SerializedName("IsMultiClass")
	val isMultiClass: Boolean = false,

		@field:SerializedName("ClassId")
	val classId: String? = null,

		@field:SerializedName("SelectedSeatLeft")
	val selectedSeatLeft: Int? = null,

		@field:SerializedName("DurationView")
	val durationView: String? = null,

		@field:SerializedName("SelectedClassCode")
	val selectedClassCode: Any? = null,

		@field:SerializedName("DepartDateTimeView")
	val departDateTimeView: String? = null,

		@field:SerializedName("FlightType")
	val flightType: String? = null,

		@field:SerializedName("ArriveDate")
	val arriveDate: String? = null,

		@field:SerializedName("DepartureDate")
	val departureDate: String? = null,

		@field:SerializedName("SelectedClass")
	val selectedClass: SelectedClass? = null,

		@field:SerializedName("FlightTypeView")
	val flightTypeView: String? = null,

		@field:SerializedName("AirlineImageUrl")
	val airlineImageUrl: String? = null,

		@field:SerializedName("FareType")
	val fareType: Any? = null,

		@field:SerializedName("Duration")
	val duration: String? = null,

		@field:SerializedName("Sequence")
	val sequence: Int = 0,

		@field:SerializedName("ConnectingFlights")
	val connectingFlights: List<TransitFlightsEntity> = ArrayList(),

		@field:SerializedName("DepartDate")
	val departDate: String? = null,

		@field:SerializedName("ClassCode")
	val classCode: String? = null,

		@field:SerializedName("FlightNumber")
	val flightNumber: String? = null,

		@field:SerializedName("Id")
	val id: String? = null,

		@field:SerializedName("ArriveDateTimeView")
	val arriveDateTimeView: String? = null,

		@field:SerializedName("PriceFare")
	val priceFare: Any? = null,

		@field:SerializedName("Destination")
	val destination: String? = null,

		@field:SerializedName("Modified")
	val modified: Any? = null,

		@field:SerializedName("IsHolderFlight")
	val isHolderFlight: Boolean = false,

		@field:SerializedName("TotalTransit")
	val totalTransit: Int = 0,

		@field:SerializedName("AirlineName")
	val airlineName: String? = null,

		@field:SerializedName("ConnectingUsable")
	val connectingUsable: Boolean = false,

		@field:SerializedName("InlineView")
	val inlineView: String? = null,

		@field:SerializedName("GroupingId")
	val groupingId: Any? = null,

		@field:SerializedName("Number")
	val number: String? = null,

		@field:SerializedName("Selectable")
	val selectable: Boolean = false,

		@field:SerializedName("DepartTime")
	val departTime: String? = null,

		@field:SerializedName("IsConnecting")
	val isConnecting: Boolean = false,

		@field:SerializedName("DurationIncludeTransitView")
	val durationIncludeTransitView: String? = null,

		@field:SerializedName("ClassObjects")
	val classObjects: List<ClassObjectsItem> = ArrayList(),

		@field:SerializedName("ClassesView")
	val classesView: List<ClassesViewItem> = ArrayList(),

		@field:SerializedName("SelectedClassKey")
	val selectedClassKey: String? = null,

		@field:SerializedName("Fareview")
	val fareview: String? = null,

		@field:SerializedName("Fare")
	val fare: Int? = null,

		@field:SerializedName("Facilities")
	val facilities: Any? = null,

		@field:SerializedName("ArrivalDate")
	val arrivalDate: String? = null,

		@field:SerializedName("IsAvailable")
	val isAvailable: Boolean = false,

		@field:SerializedName("FlightKey")
	val flightKey: Any? = null,

		@field:SerializedName("OriginCity")
		val originCity: String? = null,

		@field:SerializedName("OriginAirport")
		val originAirport: String? = null,

		@field:SerializedName("DestinationCity")
		val destinationCity: String? = null,

		@field:SerializedName("DestinationAirport")
		val destinationAirport: String? = null,

		@field:SerializedName("OriginTerminal")
		val originTerminal: String? = null,

		@field:SerializedName("DestinationTerminal")
		val destinationTerminal: String? = null
)