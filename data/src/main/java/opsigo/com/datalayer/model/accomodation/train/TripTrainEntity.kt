package opsigo.com.datalayer.model.accomodation.train

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class TripTrainEntity(

		@field:SerializedName("Status")
	val status: Int? = null,

		@field:SerializedName("IsSecondaryLevel")
	val isSecondaryLevel: Boolean? = null,

		@field:SerializedName("IsRemoved")
	val isRemoved: Boolean? = null,

		@field:SerializedName("IsDownloadPnr")
	val isDownloadPnr: Boolean? = null,

		@field:SerializedName("TripPlanId")
	val tripPlanId: String = "",

		@field:SerializedName("IsManual")
	val isManual: Boolean? = null,

		@field:SerializedName("TripTrains")
	val tripTrains: List<TripTrainsItem> = ArrayList(),

		@field:SerializedName("TravelAgentAccount")
	val travelAgentAccount: String = "",

		@field:SerializedName("HasConfirmed")
	val hasConfirmed: Boolean? = null,

		@field:SerializedName("ItemType")
	val itemType: Int? = null,

		@field:SerializedName("Amount")
	val amount: Double? = null,

		@field:SerializedName("TripMemberId")
	val tripMemberId: String = "",

		@field:SerializedName("ReasonCode")
	val reasonCode: String = "",

		@field:SerializedName("HasCostCenterUpdated")
	val hasCostCenterUpdated: Boolean? = null,

		@field:SerializedName("TripHotels")
	val tripHotels: List<Any?>? = null,

		@field:SerializedName("TripFlights")
	val tripFlights: List<Any?>? = null,

		@field:SerializedName("IsViolatedHotelRules")
	val isViolatedHotelRules: Boolean? = null,

		@field:SerializedName("FlightType")
	val flightType: Int? = null,

		@field:SerializedName("Id")
	val id: String = "",

		@field:SerializedName("EmployeeId")
	val employeeId: String = "",

		@field:SerializedName("EmployeeName")
	val employeeName: String = ""
)