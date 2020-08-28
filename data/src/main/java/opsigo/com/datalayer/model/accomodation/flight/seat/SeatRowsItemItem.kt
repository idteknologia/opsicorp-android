package opsigo.com.datalayer.model.accomodation.flight.seat

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SeatRowsItemItem(

	@field:SerializedName("Availability")
	val availability: String? = null,

	@field:SerializedName("PosX")
	val posX: Int = 0,

	@field:SerializedName("PosY")
	val posY: Int = 0,

	@field:SerializedName("SeatGroup")
	val seatGroup: Any? = null,

	@field:SerializedName("SeatRowSeat")
	val seatRowSeat: Int? = null,

	@field:SerializedName("Infant")
	val infant: Any? = null,

	@field:SerializedName("SeatClass")
	val seatClass: Any? = null,

	@field:SerializedName("SeatCode")
	val seatCode: Any? = null,

	@field:SerializedName("BRDZone")
	val bRDZone: Any? = null,

	@field:SerializedName("SeatFare")
	val seatFare: String? = null,

	@field:SerializedName("Tcc")
	val tcc: Any? = null,

	@field:SerializedName("SeatClassCode")
	val seatClassCode: String? = null,

	@field:SerializedName("Ccy")
	val ccy: String? = null,

	@field:SerializedName("FlightNumber")
	val flightNumber: String? = null,

	@field:SerializedName("SeatType")
	val seatType: String = "",

	@field:SerializedName("SeatNumber")
	val seatNumber: String? = null
)