package opsigo.com.datalayer.model.accomodation.flight.ailine_code

import com.google.gson.annotations.SerializedName

data class ListScheduleItem(

		@field:SerializedName("FlightTripType")
	val flightTripType: Int? = null,

		@field:SerializedName("PreferredCarriers")
	val preferredCarriers: ArrayList<String?>? = ArrayList(),

		@field:SerializedName("IsShowPolicy")
	val isShowPolicy: Boolean? = null,

		@field:SerializedName("RequestId")
	val requestId: String? = null,

		@field:SerializedName("CabinClassList")
	val cabinClassList: List<Int?>? = null,

		@field:SerializedName("Infant")
	val infant: Int? = null,

		@field:SerializedName("Routes")
	val routes: ArrayList<RoutesItem?>? = ArrayList(),

		@field:SerializedName("PreferredAirlines")
	val preferredAirlines: List<Int?>? = null,

		@field:SerializedName("JobTitleId")
	val jobTitleId: String? = null,

		@field:SerializedName("CompCode")
	val compCode: String? = null,

		@field:SerializedName("TravelAgent")
	val travelAgent: String? = null,

		@field:SerializedName("Adult")
	val adult: Int? = null,

		@field:SerializedName("Child")
	val child: Int? = null
)