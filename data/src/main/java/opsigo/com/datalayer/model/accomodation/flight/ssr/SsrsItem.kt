package opsigo.com.datalayer.model.accomodation.flight.ssr

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SsrsItem(

	@field:SerializedName("SsrName")
	val ssrName: String? = null,

	@field:SerializedName("SsrTypeName")
	val ssrTypeName: String? = null,

	@field:SerializedName("SegmentId")
	val segmentId: String? = null,

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
	val ssrFare: Double? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("SsrType")
	val ssrType: Int? = null
)