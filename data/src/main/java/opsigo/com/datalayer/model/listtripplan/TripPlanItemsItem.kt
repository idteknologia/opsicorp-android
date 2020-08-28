package opsigo.com.datalayer.model.listtripplan

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class TripPlanItemsItem(

		@field:SerializedName("Status")
	val status: String = "",

		@field:SerializedName("IsSecondaryLevel")
	val isSecondaryLevel: String = "",

		@field:SerializedName("IsRemoved")
	val isRemoved: String = "",

		@field:SerializedName("IsDownloadPnr")
	val isDownloadPnr: String = "",

		@field:SerializedName("TripPlanId")
	val tripPlanId: String = "",

		@field:SerializedName("IsManual")
	val isManual: String = "",

		@field:SerializedName("TripTrains")
	val tripTrains: List<String> = ArrayList<String>(),

		@field:SerializedName("TravelAgentAccount")
	val travelAgentAccount: String = "",

		@field:SerializedName("HasConfirmed")
	val hasConfirmed: String = "",

		@field:SerializedName("ItemType")
	val itemType: String = "",

		@field:SerializedName("Amount")
	val amount: String = "",

		@field:SerializedName("TripMemberId")
	val tripMemberId: String = "",

		@field:SerializedName("ReasonCode")
	val reasonCode: String = "",

		@field:SerializedName("HasCostCenterUpdated")
	val hasCostCenterUpdated: String = "",

		@field:SerializedName("TripHotels")
	val tripHotels: List<TripHotelsItem> = ArrayList<TripHotelsItem>(),

		@field:SerializedName("TripFlights")
	val tripFlights: List<TripFlightsItem> = ArrayList<TripFlightsItem>(),

		@field:SerializedName("IsViolatedHotelRules")
	val isViolatedHotelRules: String = "",

		@field:SerializedName("FlightType")
	val flightType: String = "",

		@field:SerializedName("Id")
	val id: String = "",

		@field:SerializedName("EmployeeId")
	val employeeId: String = "",

		@field:SerializedName("EmployeeName")
	val employeeName: String = ""
)