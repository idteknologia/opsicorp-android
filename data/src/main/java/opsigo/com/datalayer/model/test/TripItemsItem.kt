package opsigo.com.datalayer.model.test

import com.google.gson.annotations.SerializedName

data class TripItemsItem(

	@field:SerializedName("Status")
	val status: Int? = null,

	@field:SerializedName("IsSecondaryLevel")
	val isSecondaryLevel: Boolean? = null,

	@field:SerializedName("IsRemoved")
	val isRemoved: Boolean? = null,

	@field:SerializedName("IsDownloadPnr")
	val isDownloadPnr: Boolean? = null,

	@field:SerializedName("TripPlanId")
	val tripPlanId: String? = null,

	@field:SerializedName("IsManual")
	val isManual: Boolean? = null,

	@field:SerializedName("TripTrains")
	val tripTrains: List<Any?>? = null,

	@field:SerializedName("TravelAgentAccount")
	val travelAgentAccount: String? = null,

	@field:SerializedName("HasConfirmed")
	val hasConfirmed: Boolean? = null,

	@field:SerializedName("ItemType")
	val itemType: Int? = null,

	@field:SerializedName("Amount")
	val amount: Double? = null,

	@field:SerializedName("TripMemberId")
	val tripMemberId: String? = null,

	@field:SerializedName("ReasonCode")
	val reasonCode: Any? = null,

	@field:SerializedName("HasCostCenterUpdated")
	val hasCostCenterUpdated: Boolean? = null,

	@field:SerializedName("TripHotels")
	val tripHotels: List<Any?>? = null,

	@field:SerializedName("TripFlights")
	val tripFlights: List<TripFlightsItem?>? = null,

	@field:SerializedName("IsViolatedHotelRules")
	val isViolatedHotelRules: Boolean? = null,

	@field:SerializedName("FlightType")
	val flightType: Int? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("EmployeeId")
	val employeeId: String? = null,

	@field:SerializedName("EmployeeName")
	val employeeName: String? = null
)