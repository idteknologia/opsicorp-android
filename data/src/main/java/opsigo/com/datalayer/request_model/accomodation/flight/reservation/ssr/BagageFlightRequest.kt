package opsigo.com.datalayer.request_model.accomodation.flight.reservation.ssr

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class BagageFlightRequest(

	@field:SerializedName("SsrName")
	var ssrName: String? = null,

	@field:SerializedName("SsrCode")
	var ssrCode: String? = null,

	@field:SerializedName("DestinationCode")
	var destinationCode: String? = null,

	@field:SerializedName("OriginCode")
	var originCode: String? = null,

	@field:SerializedName("Ccy")
	var ccy: String? = null,

	@field:SerializedName("FlightNumber")
	var flightNumber: String? = null,

	@field:SerializedName("SsrFare")
	var ssrFare: Int? = null,

	@field:SerializedName("SsrType")
	var ssrType: Int? = null
)