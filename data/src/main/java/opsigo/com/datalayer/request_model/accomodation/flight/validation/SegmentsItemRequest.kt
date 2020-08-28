package opsigo.com.datalayer.request_model.accomodation.flight.validation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class  SegmentsItemRequest(

	@field:SerializedName("Origin")
	var origin: String? = null,

	@field:SerializedName("Destination")
	var destination: String? = null,

	@field:SerializedName("ArriveTime")
	var arriveTime: String? = null,

	@field:SerializedName("AirlineImageUrl")
	var airlineImageUrl: String? = null,

	@field:SerializedName("Category")
	var category: String? = null,

	@field:SerializedName("Airline")
	var airline: Int? = null,

	@field:SerializedName("AirlineView")
	var airlineView: String? = null,

	@field:SerializedName("Num")
	var num: Int? = null,

	@field:SerializedName("Amount")
	var amount: Int? = null,

	@field:SerializedName("AirlineName")
	var airlineName: String? = null,

	@field:SerializedName("ClassId")
	var classId: String? = null,

	@field:SerializedName("Duration")
	var duration: String? = null,

	@field:SerializedName("DepartDate")
	var departDate: String? = null,

	@field:SerializedName("OriginView")
	var originView: String? = null,

	@field:SerializedName("ClassCode")
	var classCode: String? = null,

	@field:SerializedName("DepartTime")
	var departTime: String? = null,

	@field:SerializedName("FlightId")
	var flightId: String? = null,

	@field:SerializedName("DestinationView")
	var destinationView: String? = null,

	@field:SerializedName("FlightNumber")
	var flightNumber: String? = null,

	@field:SerializedName("ArriveDate")
	var arriveDate: String? = null,

	@field:SerializedName("Seq")
	var seq: Int? = null
)