package opsigo.com.datalayer.request_model.accomodation.flight.search.airline_pref

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class AirlinePrefByCompanyRequest(

	@field:SerializedName("PreferredCarriers")
	var preferredCarriers: List<Any?>? = null,

	@field:SerializedName("FlightTripType")
	var flightTripType: Int? = null,

	@field:SerializedName("CabinClassList")
	var cabinClassList: List<Int?>? = null,

	@field:SerializedName("TravelAgent")
	var travelAgent: String? = null,

	@field:SerializedName("IsShowPolicy")
	var isShowPolicy : Boolean = false,

	@field:SerializedName("Adult")
	var adult: Int? = null,

	@field:SerializedName("Infant")
	var infant: Int? = null,

	@field:SerializedName("Routes")
	var routes: List<RoutesItem?>? = null,

	@field:SerializedName("EmployeeId")
	var employeeId: String? = null,

	@field:SerializedName("Child")
	var child: Int? = null
)