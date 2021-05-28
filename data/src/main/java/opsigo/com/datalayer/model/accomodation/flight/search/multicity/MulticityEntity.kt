package opsigo.com.datalayer.model.accomodation.flight.search.multicity

import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.model.accomodation.flight.search.transit.TransitFlightsEntity

data class OutgoingFlightItem(

		@field:SerializedName("Origin")
		val origin: String? = null,

		@field:SerializedName("IsComply")
		val isComply: Boolean = false,

		@field:SerializedName("ArriveTime")
		val arriveTime: String? = null,

		@field:SerializedName("ConnectingFlightsAny")
		val connectingFlightsAny: Boolean = false,

		@field:SerializedName("Airline")
		val airline: Int = 0,

		@field:SerializedName("DestinationCity")
		val destinationCity: String? = null,

		@field:SerializedName("FareBreakdowns")
		val fareBreakdowns: Any? = null,

		@field:SerializedName("DurationIncludeTransit")
		val durationIncludeTransit: String? = null,

		@field:SerializedName("IsMultiClass")
		val isMultiClass: Boolean = false,

		@field:SerializedName("ClassId")
		val classId: String? = null,

		@field:SerializedName("MultipleAirlineName")
		val multipleAirlineName: List<String?>? = null,

		@field:SerializedName("SelectedSeatLeft")
		val selectedSeatLeft: Int = 0,

		@field:SerializedName("IsSeparatePerClass")
		val isSeparatePerClass: Boolean = false,

		@field:SerializedName("TransitDurationView")
		val transitDurationView: String? = null,

		@field:SerializedName("DurationView")
		val durationView: String? = null,

		@field:SerializedName("OperatingAirlineName")
		val operatingAirlineName: Any? = null,

		@field:SerializedName("SelectedClassCode")
		val selectedClassCode: Any? = null,

		@field:SerializedName("DepartDateTimeView")
		val departDateTimeView: String? = null,

		@field:SerializedName("OperatingAirlineImageUrl")
		val operatingAirlineImageUrl: Any? = null,

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
		val sequence: Int = 0,

		@field:SerializedName("DestinationTerminal")
		val destinationTerminal: Any? = null,

		@field:SerializedName("ConnectingFlights")
		val connectingFlights: ArrayList<TransitFlightsEntity> = ArrayList(),

		@field:SerializedName("DepartDate")
		val departDate: String? = null,

		@field:SerializedName("ClassCode")
		val classCode: String? = null,

		@field:SerializedName("CrossDay")
		val crossDay: Double = 0.0,

		@field:SerializedName("Note")
		val note: Any? = null,

		@field:SerializedName("FlightNumber")
		val flightNumber: String? = null,

		@field:SerializedName("IsMultiClassFirstSegmentOnly")
		val isMultiClassFirstSegmentOnly: Boolean = false,

		@field:SerializedName("Id")
		val id: String? = null,

		@field:SerializedName("ArriveDateTimeView")
		val arriveDateTimeView: String? = null,

		@field:SerializedName("PriceFare")
		val priceFare: Any? = null,

		@field:SerializedName("Destination")
		val destination: String? = null,

		@field:SerializedName("OriginTerminal")
		val originTerminal: Any? = null,

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

		@field:SerializedName("IsOpenJawTransit")
		val isOpenJawTransit: Boolean = false,

		@field:SerializedName("TransitDuration")
		val transitDuration: Any? = null,

		@field:SerializedName("DurationIncludeTransitView")
		val durationIncludeTransitView: String? = null,

		@field:SerializedName("ClassObjects")
		val classObjects: List<ClassObjectsItem?>? = null,

		@field:SerializedName("ClassesView")
		val classesView: List<ClassesViewItem> = ArrayList(),

		@field:SerializedName("SelectedClassKey")
		val selectedClassKey: String? = null,

		@field:SerializedName("OperatingNumber")
		val operatingNumber: Any? = null,

		@field:SerializedName("DestinationAirport")
		val destinationAirport: String? = null,

		@field:SerializedName("Fareview")
		val fareview: String? = null,

		@field:SerializedName("Fare")
		val fare: Double = 0.0,

		@field:SerializedName("Facilities")
		val facilities: Any? = null,

		@field:SerializedName("OriginAirport")
		val originAirport: String? = null,

		@field:SerializedName("ArrivalDate")
		val arrivalDate: String? = null,

		@field:SerializedName("IsAvailable")
		val isAvailable: Boolean = false,

		@field:SerializedName("FlightKey")
		val flightKey: Any? = null,

		@field:SerializedName("Aircraft")
		val aircraft: Any? = null
)

data class MultiFlightsItem(
		@field:SerializedName("OutgoingFlight")
		val outgoingFlight: List<OutgoingFlightItem?>? = null
)

data class SelectedClass(

		@field:SerializedName("IsComply")
		val isComply: Boolean = false,

		@field:SerializedName("PaxFare")
		val paxFare: Any? = null,

		@field:SerializedName("Seat")
		val seat: Int = 0,

		@field:SerializedName("Category")
		val category: String? = null,

		@field:SerializedName("FareBasisCode")
		val fareBasisCode: Any? = null,

		@field:SerializedName("Tax")
		val tax: Double = 0.0,

		@field:SerializedName("Code")
		val code: String? = null,

		@field:SerializedName("Fare")
		val fare: Double = 0.0,

		@field:SerializedName("TotalFareView")
		val totalFareView: String? = null,

		@field:SerializedName("TotalFare")
		val totalFare: Double = 0.0,

		@field:SerializedName("Active")
		val active: Boolean = false,

		@field:SerializedName("CategoryCabin")
		val categoryCabin: Any? = null,

		@field:SerializedName("FlightId")
		val flightId: String? = null,

		@field:SerializedName("FareRuleKeys")
		val fareRuleKeys: Any? = null,

		@field:SerializedName("ClassKey")
		val classKey: Any? = null,

		@field:SerializedName("Id")
		val id: String? = null
)

data class ClassObjectsItem(

		@field:SerializedName("IsComply")
		val isComply: Boolean = false,

		@field:SerializedName("PaxFare")
		val paxFare: Any? = null,

		@field:SerializedName("Seat")
		val seat: Int = 0,

		@field:SerializedName("Category")
		val category: String? = null,

		@field:SerializedName("FareBasisCode")
		val fareBasisCode: Any? = null,

		@field:SerializedName("Tax")
		val tax: Double = 0.0,

		@field:SerializedName("Code")
		val code: String? = null,

		@field:SerializedName("Fare")
		val fare: Double = 0.0,

		@field:SerializedName("TotalFareView")
		val totalFareView: String? = null,

		@field:SerializedName("TotalFare")
		val totalFare: Double = 0.0,

		@field:SerializedName("Active")
		val active: Boolean = false,

		@field:SerializedName("CategoryCabin")
		val categoryCabin: Any? = null,

		@field:SerializedName("FlightId")
		val flightId: String? = null,

		@field:SerializedName("FareRuleKeys")
		val fareRuleKeys: Any? = null,

		@field:SerializedName("ClassKey")
		val classKey: Any? = null,

		@field:SerializedName("Id")
		val id: String? = null
)

data class ClassesViewItem(

		@field:SerializedName("IsComply")
		val isComply: Boolean = false,

		@field:SerializedName("PaxFare")
		val paxFare: Any? = null,

		@field:SerializedName("Seat")
		val seat: Int = 0,

		@field:SerializedName("Category")
		val category: String? = null,

		@field:SerializedName("FareBasisCode")
		val fareBasisCode: Any? = null,

		@field:SerializedName("Tax")
		val tax: Double = 0.0,

		@field:SerializedName("Code")
		val code: String? = null,

		@field:SerializedName("Fare")
		val fare: Double = 0.0,

		@field:SerializedName("TotalFareView")
		val totalFareView: String? = null,

		@field:SerializedName("TotalFare")
		val totalFare: Double = 0.0,

		@field:SerializedName("Active")
		val active: Boolean = false,

		@field:SerializedName("CategoryCabin")
		val categoryCabin: Any? = null,

		@field:SerializedName("FlightId")
		val flightId: String? = null,

		@field:SerializedName("FareRuleKeys")
		val fareRuleKeys: Any? = null,

		@field:SerializedName("ClassKey")
		val classKey: Any? = null,

		@field:SerializedName("Id")
		val id: String? = null
)
