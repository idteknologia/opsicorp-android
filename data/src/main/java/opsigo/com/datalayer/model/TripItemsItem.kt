package opsigo.com.datalayer.model

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.model.accomodation.train.TripTrainsItem
import opsigo.com.datalayer.model.listtripplan.TripHotelsItem

@Generated("com.robohorse.robopojogenerator")
data class TripItemsItem(

		@field:SerializedName("Status")
	val status: Int = 0,

		@field:SerializedName("IsSecondaryLevel")
	val isSecondaryLevel: Boolean = false,

		@field:SerializedName("IsRemoved")
	val isRemoved: Boolean = false,

		@field:SerializedName("IsDownloadPnr")
	val isDownloadPnr: Boolean = false,

		@field:SerializedName("TripPlanId")
	val tripPlanId: String = "",

		@field:SerializedName("IsManual")
	val isManual: Boolean = false,

		@field:SerializedName("TripTrains")
	val tripTrains: List<TripTrainsItem> = ArrayList(),

		@field:SerializedName("TravelAgentAccount")
	val travelAgentAccount: String = "",

		@field:SerializedName("HasConfirmed")
	val hasConfirmed: Boolean = false,

		@field:SerializedName("ItemType")
	val itemType: String = "",

		@field:SerializedName("Amount")
	val amount: Double? = null,

		@field:SerializedName("TripMemberId")
	val tripMemberId: String = "",

		@field:SerializedName("ReasonCode")
	val reasonCode: Any? = null,

		@field:SerializedName("HasCostCenterUpdated")
	val hasCostCenterUpdated: Boolean = false,

		@field:SerializedName("TripHotels")
	val tripHotels: List<TripHotelsItem> = ArrayList(),

		@field:SerializedName("TripFlights")
	val tripFlights: List<TripFlightsItem>,//? = null,
	//val tripFlights: List<TripFlightsItem?>? = null,

		@field:SerializedName("IsViolatedHotelRules")
	val isViolatedHotelRules: Boolean = false,

		@field:SerializedName("FlightType")
	val flightType: String = "",

		@field:SerializedName("Id")
	val id: String = "",

		@field:SerializedName("EmployeeId")
	val employeeId: String = "",

		@field:SerializedName("EmployeeName")
	val employeeName: String = ""
)