package opsigo.com.datalayer.request_model.accomodation.flight.reservation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SegmentFlightsRequest(

	@field:SerializedName("Origin")
	var origin: String? = null,

	@field:SerializedName("IsComply")
	var isComply: Boolean? = null,

	@field:SerializedName("ArriveTime")
	var arriveTime: String? = null,

	@field:SerializedName("Seat")
	var seat: Int? = null,

	@field:SerializedName("Airline")
	var airline: Int? = null,

	@field:SerializedName("FareBreakdowns")
	var fareBreakdowns: Any? = null,

	@field:SerializedName("DurationIncludeTransit")
	var durationIncludeTransit: String? = null,

	@field:SerializedName("IsMultiClass")
	var isMultiClass: Boolean? = null,

	@field:SerializedName("ClassId")
	var classId: String? = null,

	@field:SerializedName("Tax")
	var tax: Double? = null,

	@field:SerializedName("SelectedSeatLeft")
	var selectedSeatLeft: Int? = null,

	@field:SerializedName("DurationView")
	var durationView: String? = null,

	@field:SerializedName("SelectedClassCode")
	var selectedClassCode: Any? = null,

	@field:SerializedName("DepartDateTimeView")
	var departDateTimeView: String? = null,

	@field:SerializedName("FlightType")
	var flightType: String? = null,

	@field:SerializedName("ArriveDate")
	var arriveDate: String? = null,

	@field:SerializedName("DepartureDate")
	var departureDate: String? = null,

	@field:SerializedName("FlightTypeView")
	var flightTypeView: String? = null,

	@field:SerializedName("AirlineImageUrl")
	var airlineImageUrl: String? = null,

	@field:SerializedName("FareType")
	var fareType: Any? = null,

	@field:SerializedName("Duration")
	var duration: String? = null,

	@field:SerializedName("Sequence")
	var sequence: Int? = null,

	@field:SerializedName("ConnectingFlights")
	var connectingFlights: List<Any?>? = null,

	@field:SerializedName("DepartDate")
	var departDate: String? = null,

	@field:SerializedName("Active")
	var active: Boolean? = null,

	@field:SerializedName("ClassCode")
	var classCode: String? = null,

	@field:SerializedName("FlightNumber")
	var flightNumber: String? = null,

	@field:SerializedName("FareRuleKeys")
	var fareRuleKeys: Any? = null,


	@field:SerializedName("Seq")
	var seq: String? = null,

	@field:SerializedName("ArriveDateTimeView")
	var arriveDateTimeView: String? = null,

	@field:SerializedName("PriceFare")
	var priceFare: Any? = null,

	@field:SerializedName("Destination")
	var destination: String? = null,

	@field:SerializedName("Modified")
	var modified: Any? = null,

	@field:SerializedName("IsHolderFlight")
	var isHolderFlight: Boolean? = null,

	@field:SerializedName("Category")
	var category: String? = null,

	@field:SerializedName("IsHaveSport")
	var isHaveSport: Boolean? = null,

	@field:SerializedName("TotalTransit")
	var totalTransit: Int? = null,

	@field:SerializedName("AirlineName")
	var airlineName: String? = null,

	@field:SerializedName("ConnectingUsable")
	var connectingUsable: Boolean? = null,

	@field:SerializedName("InlineView")
	var inlineView: String? = null,

	@field:SerializedName("GroupingId")
	var groupingId: Any? = null,

	@field:SerializedName("Number")
	var number: String? = null,

	@field:SerializedName("IsHaveDrink")
	var isHaveDrink: Boolean? = null,

	@field:SerializedName("Selectable")
	var selectable: Boolean? = null,

	@field:SerializedName("DepartTime")
	var departTime: String? = null,

	@field:SerializedName("CategoryCabin")
	var categoryCabin: Any? = null,

	@field:SerializedName("IsConnecting")
	var isConnecting: Boolean? = null,

	@field:SerializedName("PaxFare")
	var paxFare: Any? = null,

	@field:SerializedName("DurationIncludeTransitView")
	var durationIncludeTransitView: String? = null,

	@field:SerializedName("FareBasisCode")
	var fareBasisCode: Any? = null,

	@field:SerializedName("SelectedClassKey")
	var selectedClassKey: String? = null,

	@field:SerializedName("Ssrs")
	var ssrs: List<Any?>? = null,

	@field:SerializedName("Num")
	var num: String? = null,

	@field:SerializedName("Fareview")
	var fareview: String? = null,

	@field:SerializedName("Fare")
	var fare: Double? = null,

	@field:SerializedName("Facilities")
	var facilities: Any? = null,

	@field:SerializedName("ArrivalDate")
	var arrivalDate: String? = null,

	@field:SerializedName("IsHaveMeals")
	var isHaveMeals: Boolean? = null,

	@field:SerializedName("IsAvailable")
	var isAvailable: Boolean? = null,

	@field:SerializedName("FlightId")
	var flightId: String? = null,

	@field:SerializedName("ClassKey")
	var classKey: Any? = null
)