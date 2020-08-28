package opsigo.com.datalayer.model.accomodation.flight.search

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.model.accomodation.flight.search.transit.TransitFlightsEntity

@Generated("com.robohorse.robopojogenerator")
data class DepartureFlightsItem(

		@field:SerializedName("Origin")
	val origin: String? = null,

		@field:SerializedName("IsComply")
	val isComply: Boolean = false,

		@field:SerializedName("ArriveTime")
	val arriveTime: String = "",

		@field:SerializedName("Airline")
	val airline: Int = 0,

		@field:SerializedName("FareBreakdowns")
	val fareBreakdowns: Any? = null,

		@field:SerializedName("DurationIncludeTransit")
	val durationIncludeTransit: String = "",

		@field:SerializedName("IsMultiClass")
	val isMultiClass: Boolean = false,

		@field:SerializedName("ClassId")
	val classId: String = "",

		@field:SerializedName("SelectedSeatLeft")
	val selectedSeatLeft: Int = 0,

		@field:SerializedName("DurationView")
	val durationView: String = "",

		@field:SerializedName("SelectedClassCode")
	val selectedClassCode: String = "",

		@field:SerializedName("DepartDateTimeView")
	val departDateTimeView: String = "",

		@field:SerializedName("FlightType")
	val flightType: String = "",

		@field:SerializedName("ArriveDate")
	val arriveDate: String = "",

		@field:SerializedName("DepartureDate")
	val departureDate: String = "",

		@field:SerializedName("SelectedClass")
	val selectedClass: SelectedClass = SelectedClass(),

		@field:SerializedName("FlightTypeView")
	val flightTypeView: String = "",

		@field:SerializedName("AirlineImageUrl")
	val airlineImageUrl: String = "",

		@field:SerializedName("FareType")
	val fareType: Any? = null,

		@field:SerializedName("Duration")
	val duration: String = "",

		@field:SerializedName("Sequence")
	val sequence: Int = 0,

		@field:SerializedName("ConnectingFlights")
	val connectingFlights: List<TransitFlightsEntity> = ArrayList(),

		@field:SerializedName("DepartDate")
	val departDate: String = "",

		@field:SerializedName("ClassCode")
	val classCode: String = "",

		@field:SerializedName("FlightNumber")
	val flightNumber: String = "",

		@field:SerializedName("Id")
	val id: String = "",

		@field:SerializedName("ArriveDateTimeView")
	val arriveDateTimeView: String = "",

		@field:SerializedName("PriceFare")
	val priceFare: String = "",

		@field:SerializedName("Destination")
	val destination: String? = null,

		@field:SerializedName("Modified")
	val modified: String = "",

		@field:SerializedName("IsHolderFlight")
	val isHolderFlight: Boolean = false,

		@field:SerializedName("TotalTransit")
	val totalTransit: Int = 0,

		@field:SerializedName("AirlineName")
	val airlineName: String = "",

		@field:SerializedName("ConnectingUsable")
	val connectingUsable: Boolean = false,

		@field:SerializedName("InlineView")
	val inlineView: String = "",

		@field:SerializedName("GroupingId")
	val groupingId: Any? = null,

		@field:SerializedName("Number")
	val number: String = "",

		@field:SerializedName("Selectable")
	val selectable: Boolean = false,

		@field:SerializedName("DepartTime")
	val departTime: String = "",

		@field:SerializedName("IsConnecting")
	val isConnecting: Boolean = false,

		@field:SerializedName("DurationIncludeTransitView")
	val durationIncludeTransitView: String = "",

		@field:SerializedName("ClassObjects")
	val classObjects: List<ClassObjectsItem> = ArrayList(),

		@field:SerializedName("ClassesView")
	val classesView: List<ClassesViewItem> = ArrayList(),

		@field:SerializedName("SelectedClassKey")
	val selectedClassKey: String = "",

		@field:SerializedName("Fareview")
	val fareview: String = "",

		@field:SerializedName("Fare")
	val fare: String = "",

		@field:SerializedName("Facilities")
	val facilities: ArrayList<FacilityEntity> = ArrayList(),

		@field:SerializedName("ArrivalDate")
	val arrivalDate: String = "",

		@field:SerializedName("IsAvailable")
	val isAvailable: Boolean = false,

		@field:SerializedName("FlightKey")
	val flightKey: String = "",


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