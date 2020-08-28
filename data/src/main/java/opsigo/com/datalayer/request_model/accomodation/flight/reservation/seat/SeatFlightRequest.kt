package opsigo.com.datalayer.request_model.accomodation.flight.reservation.seat

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SeatFlightRequest(

        @field:SerializedName("Availability")
	val availability: String? = null,

        @field:SerializedName("PosX")
	val posX: Int? = null,

        @field:SerializedName("PosY")
	val posY: Int? = null,

        @field:SerializedName("SeatGroup")
	val seatGroup: Int? = null,

        @field:SerializedName("SeatClass")
	val seatClass: String? = null,

        @field:SerializedName("Properties")
	val properties: PropertiesSeatFlight? = null,

        @field:SerializedName("SeatCode")
	val seatCode: String? = null,

        @field:SerializedName("SeatFare")
	val seatFare: Int? = null,

        @field:SerializedName("SeatClassCode")
	val seatClassCode: String? = null,

        @field:SerializedName("Ccy")
	val ccy: String? = null,

        @field:SerializedName("FlightNumber")
	val flightNumber: String? = null,

        @field:SerializedName("SeatType")
	val seatType: String? = null,

        @field:SerializedName("SeatRowSet")
	val seatRowSet: String? = null,

        @field:SerializedName("SeatNumber")
	val seatNumber: String? = null
)