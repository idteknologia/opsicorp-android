package opsigo.com.datalayer.model.accomodation.flight.search.transit

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ClassesViewItem(

	@field:SerializedName("IsComply")
	val isComply: Boolean? = null,

	@field:SerializedName("PaxFare")
	val paxFare: Any? = null,

	@field:SerializedName("Seat")
	val seat: Int? = null,

	@field:SerializedName("Category")
	val category: String? = null,

	@field:SerializedName("FareBasisCode")
	val fareBasisCode: String? = null,

	@field:SerializedName("Tax")
	val tax: Double? = null,

	@field:SerializedName("Code")
	val code: String? = null,

	@field:SerializedName("Fare")
	val fare: Double? = null,

	@field:SerializedName("TotalFareView")
	val totalFareView: String? = null,

	@field:SerializedName("TotalFare")
	val totalFare: Double? = null,

	@field:SerializedName("Active")
	val active: Boolean? = null,

	@field:SerializedName("CategoryCabin")
	val categoryCabin: Any? = null,

	@field:SerializedName("FlightId")
	val flightId: String? = null,

	@field:SerializedName("FareRuleKeys")
	val fareRuleKeys: String? = null,

	@field:SerializedName("ClassKey")
	val classKey: Any? = null,

	@field:SerializedName("Id")
	val id: String? = null
)