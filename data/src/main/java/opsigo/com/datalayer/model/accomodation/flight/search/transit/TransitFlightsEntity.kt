package opsigo.com.datalayer.model.accomodation.flight.search.transit

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class TransitFlightsEntity(

		@field:SerializedName("Origin")
	val origin: String? = null,

		@field:SerializedName("IsComply")
	val isComply: Boolean? = null,

		@field:SerializedName("ArriveTime")
	val arriveTime: String? = null,

		@field:SerializedName("ConnectingFlightsAny")
	val connectingFlightsAny: Boolean? = null,

		@field:SerializedName("Airline")
	val airline: Int? = null,

		@field:SerializedName("DestinationCity")
	val destinationCity: String? = null,

		@field:SerializedName("FareBreakdowns")
	val fareBreakdowns: Any? = null,

		@field:SerializedName("DurationIncludeTransit")
	val durationIncludeTransit: String? = null,

		@field:SerializedName("IsMultiClass")
	val isMultiClass: Boolean? = null,

		@field:SerializedName("ClassId")
	val classId: String? = null,

		@field:SerializedName("SelectedSeatLeft")
	val selectedSeatLeft: Int? = null,

		@field:SerializedName("TransitDurationView")
	val transitDurationView: String? = null,

		@field:SerializedName("DurationView")
	val durationView: String? = null,

		@field:SerializedName("OperatingAirlineName")
	val operatingAirlineName: String? = null,

		@field:SerializedName("SelectedClassCode")
	val selectedClassCode: Any? = null,

		@field:SerializedName("DepartDateTimeView")
	val departDateTimeView: String? = null,

		@field:SerializedName("OperatingAirlineImageUrl")
	val operatingAirlineImageUrl: String? = null,

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

		@field:SerializedName("OriginCity")
	val originCity: String? = null,

		@field:SerializedName("Sequence")
	val sequence: Int? = null,

		@field:SerializedName("DestinationTerminal")
	val destinationTerminal: String? = null,

		@field:SerializedName("ConnectingFlights")
	val connectingFlights: List<Any?>? = null,

		@field:SerializedName("DepartDate")
	val departDate: String? = null,

		@field:SerializedName("ClassCode")
	val classCode: String? = null,

		@field:SerializedName("CrossDay")
	val crossDay: Double? = null,

		@field:SerializedName("Note")
	val note: Any? = null,

		@field:SerializedName("FlightNumber")
	val flightNumber: String? = null,

		@field:SerializedName("IsMultiClassFirstSegmentOnly")
	val isMultiClassFirstSegmentOnly: Boolean? = null,

		@field:SerializedName("Id")
	val id: String? = null,

		@field:SerializedName("ArriveDateTimeView")
	val arriveDateTimeView: String? = null,

		@field:SerializedName("PriceFare")
	val priceFare: Any? = null,

		@field:SerializedName("Destination")
	val destination: String? = null,

		@field:SerializedName("OriginTerminal")
	val originTerminal: String? = null,

		@field:SerializedName("Modified")
	val modified: Any? = null,

		@field:SerializedName("IsHolderFlight")
	val isHolderFlight: Boolean? = null,

		@field:SerializedName("TotalTransit")
	val totalTransit: Int? = null,

		@field:SerializedName("AirlineName")
	val airlineName: String? = null,

		@field:SerializedName("ConnectingUsable")
	val connectingUsable: Boolean? = null,

		@field:SerializedName("InlineView")
	val inlineView: String? = null,

		@field:SerializedName("GroupingId")
	val groupingId: Any? = null,

		@field:SerializedName("Number")
	val number: String? = null,

		@field:SerializedName("Selectable")
	val selectable: Boolean? = null,

		@field:SerializedName("DepartTime")
	val departTime: String? = null,

		@field:SerializedName("IsConnecting")
	val isConnecting: Boolean? = null,

		@field:SerializedName("IsOpenJawTransit")
	val isOpenJawTransit: Boolean? = null,

		@field:SerializedName("TransitDuration")
	val transitDuration: String? = null,

		@field:SerializedName("DurationIncludeTransitView")
	val durationIncludeTransitView: String? = null,

		@field:SerializedName("ClassObjects")
	val classObjects: List<ClassObjectsItem?>? = null,

		@field:SerializedName("ClassesView")
	val classesView: List<ClassesViewItem> = ArrayList(),

		@field:SerializedName("SelectedClassKey")
	val selectedClassKey: String? = null,

		@field:SerializedName("OperatingNumber")
	val operatingNumber: String? = null,

		@field:SerializedName("DestinationAirport")
	val destinationAirport: String? = null,

		@field:SerializedName("Fareview")
	val fareview: String? = null,

		@field:SerializedName("Fare")
	val fare: Double? = null,

		@field:SerializedName("Facilities")
	val facilities: List<FacilitiesItem?>? = null,

		@field:SerializedName("OriginAirport")
	val originAirport: String? = null,

		@field:SerializedName("ArrivalDate")
	val arrivalDate: String? = null,

		@field:SerializedName("IsAvailable")
	val isAvailable: Boolean? = null,

		@field:SerializedName("FlightKey")
	val flightKey: Any? = null,

		@field:SerializedName("Aircraft")
	val aircraft: Aircraft? = null
)