package opsigo.com.datalayer.model.accomodation.flight.search

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ClassObjectsItem(

	@field:SerializedName("IsComply")
	val isComply: Boolean = false,

	@field:SerializedName("PaxFare")
	val paxFare: String? = "",

	@field:SerializedName("Seat")
	val seat: String? = "",

	@field:SerializedName("Category")
	val category: String? = "",

	@field:SerializedName("FareBasisCode")
	val fareBasisCode: String? = "",

	@field:SerializedName("Tax")
	val tax: Int = 0,

	@field:SerializedName("Code")
	val code: String? = "",

	@field:SerializedName("Fareview")
	val fareview: String? = "",

	@field:SerializedName("Fare")
	val fare: Double = 0.0,

	@field:SerializedName("Active")
	val active: Boolean = false,

	@field:SerializedName("CategoryCabin")
	val categoryCabin: Any? = null,

	@field:SerializedName("FlightId")
	val flightId: String? = "",

	@field:SerializedName("FareRuleKeys")
	val fareRuleKeys: String? = "",

	@field:SerializedName("ClassKey")
	val classKey: String? = "",

	@field:SerializedName("Id")
	val id: String? = ""
)