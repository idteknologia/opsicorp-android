package opsigo.com.datalayer.request_model.accomodation.flight.reservation.seat

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SeatFlightRequest(

        @field:SerializedName("Availability")
	var availability: String? = null,

        @field:SerializedName("PosX")
	var posX: Int? = null,

        @field:SerializedName("PosY")
	var posY: Int? = null,

        @field:SerializedName("SeatGroup")
	var seatGroup: Int? = null,

        @field:SerializedName("SeatClass")
	var seatClass: String? = null,

        @field:SerializedName("Properties")
	var properties: PropertiesSeatFlight? = null,

        @field:SerializedName("SeatCode")
	var seatCode: String? = null,

        @field:SerializedName("SeatFare")
	var seatFare: Int? = null,

        @field:SerializedName("SeatClassCode")
	var seatClassCode: String? = null,

        @field:SerializedName("Ccy")
	var ccy: String? = null,

        @field:SerializedName("FlightNumber")
	var flightNumber: String? = null,

        @field:SerializedName("SeatType")
	var seatType: String? = null,

        @field:SerializedName("SeatRowSet")
	var seatRowSet: String? = null,

        @field:SerializedName("SeatNumber")
	var seatNumber: String? = null
)