package opsigo.com.datalayer.request_model.accomodation.flight.ssr

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SegmentListItemRequest(

	@field:SerializedName("Origin")
	val origin: String? = null,

	@field:SerializedName("Destination")
	val destination: String? = null,

	@field:SerializedName("ArriveTime")
	val arriveTime: String? = null,

	@field:SerializedName("Airline")
	val airline: Int? = null,

	@field:SerializedName("Num")
	val num: String? = null,

	@field:SerializedName("ClassId")
	val classId: String? = null,

	@field:SerializedName("DepartDate")
	val departDate: String? = null,

	@field:SerializedName("ClassCode")
	val classCode: String? = null,

	@field:SerializedName("DepartTime")
	val departTime: String? = null,

	@field:SerializedName("FlightId")
	val flightId: String? = null,

	@field:SerializedName("FlightNumber")
	val flightNumber: String? = null,

	@field:SerializedName("ArriveDate")
	val arriveDate: String? = null,

	@field:SerializedName("Seq")
	val seq: String? = null
)