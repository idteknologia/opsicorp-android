package opsigo.com.datalayer.model.accomodation.flight.seat

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class RsFlightSeatsItem(

		@field:SerializedName("Origin")
	val origin: Any? = null,

		@field:SerializedName("Destination")
	val destination: Any? = null,

		@field:SerializedName("Group")
	val group: Int? = null,

		@field:SerializedName("SegmentKey")
	val segmentKey: Any? = null,

		@field:SerializedName("Std")
	val std: Any? = null,

		@field:SerializedName("PosX")
	val posX: Int? = null,

		@field:SerializedName("PosY")
	val posY: Int? = null,

		@field:SerializedName("Airline")
	val airline: String? = "",

		@field:SerializedName("IsPremium")
	val isPremium: Boolean? = null,

		@field:SerializedName("DestinationCode")
	val destinationCode: Any? = null,

		@field:SerializedName("SeatGroup")
	val seatGroup: Int? = null,

		@field:SerializedName("SsrFare")
	val ssrFare: Double? = null,

		@field:SerializedName("SeatClass")
	val seatClass: Any? = null,

		@field:SerializedName("JourneyKey")
	val journeyKey: Any? = null,

		@field:SerializedName("Properties")
	val properties: Any? = null,

		@field:SerializedName("SsrsAllowed")
	val ssrsAllowed: List<Any?>? = null,

		@field:SerializedName("SsrName")
	val ssrName: Any? = null,

		@field:SerializedName("SeatFare")
	val seatFare: Int? = null,

		@field:SerializedName("Currency")
	val currency: Any? = null,

		@field:SerializedName("SeatType")
	val seatType: Any? = null,

		@field:SerializedName("SeatRowSet")
	val seatRowSet: Any? = null,

		@field:SerializedName("SsrType")
	val ssrType: Int? = null,

		@field:SerializedName("Availability")
	val availability: Any? = null,

		@field:SerializedName("Status")
	val status: Int? = null,

		@field:SerializedName("SegmentId")
	val segmentId: String? = null,

		@field:SerializedName("ArrivalStation")
	val arrivalStation: Any? = null,

		@field:SerializedName("Num")
	val num: Int? = null,

		@field:SerializedName("OriginCode")
	val originCode: Any? = null,

		@field:SerializedName("LegReference")
	val legReference: Any? = null,

		@field:SerializedName("SeatCode")
	val seatCode: Any? = null,

		@field:SerializedName("Seats")
	val seats: List<SeatsFlightItem> = ArrayList(),

		@field:SerializedName("SeatKey")
	val seatKey: Any? = null,

		@field:SerializedName("IsAisle")
	val isAisle: Boolean? = null,

		@field:SerializedName("Price")
	val price: Double? = null,

		@field:SerializedName("SsrCode")
	val ssrCode: Any? = null,

		@field:SerializedName("SeatSet")
	val seatSet: Any? = null,

		@field:SerializedName("SeatClassCode")
	val seatClassCode: Any? = null,

		@field:SerializedName("FlightNumber")
	val flightNumber: Any? = null,

		@field:SerializedName("SeatRows")
	val seatRows: List<ArrayList<SeatRowsItemItem>> = ArrayList(),

		@field:SerializedName("Ccy")
	val ccy: Any? = null,

		@field:SerializedName("Id")
	val id: String? = null,

		@field:SerializedName("TravelClassCode")
	val travelClassCode: Any? = null,

		@field:SerializedName("PriceDisplay")
	val priceDisplay: Any? = null,

		@field:SerializedName("SeatNumber")
	val seatNumber: Any? = null,

		@field:SerializedName("AirCraftTypeName")
	val airCraftTypeName: String? = "",

		@field:SerializedName("Seq")
	val seq: Int? = null,

		@field:SerializedName("DepartureStation")
	val departureStation: Any? = null
)