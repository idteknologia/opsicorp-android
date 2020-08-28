package opsigo.com.datalayer.request_model.accomodation.flight.reservation.ssr

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class BagageFlightRequest(

	@field:SerializedName("SsrName")
	val ssrName: String? = null,

	@field:SerializedName("SsrCode")
	val ssrCode: String? = null,

	@field:SerializedName("DestinationCode")
	val destinationCode: String? = null,

	@field:SerializedName("OriginCode")
	val originCode: String? = null,

	@field:SerializedName("Ccy")
	val ccy: String? = null,

	@field:SerializedName("FlightNumber")
	val flightNumber: String? = null,

	@field:SerializedName("SsrFare")
	val ssrFare: Int? = null,

	@field:SerializedName("SsrType")
	val ssrType: Int? = null
)