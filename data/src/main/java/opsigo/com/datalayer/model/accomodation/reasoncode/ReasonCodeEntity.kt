package opsigo.com.datalayer.model.accomodation.reasoncode

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ReasonCodeEntity(

	@field:SerializedName("IsAdvanceBooking")
	val isAdvanceBooking: Boolean = false,

	@field:SerializedName("IsDeleted")
	val isDeleted: Boolean = false,

	@field:SerializedName("Description")
	val description: String = "",

	@field:SerializedName("CodeBrief")
	val codeBrief: String = "",

	@field:SerializedName("IsViolatedRules")
	val isViolatedRules: Boolean = false,

	@field:SerializedName("IsAirlineCompliance")
	val isAirlineCompliance: Boolean = false,

	@field:SerializedName("IsLowestFare")
	val isLowestFare: Boolean = false,

	@field:SerializedName("Code")
	val code: String = "",

	@field:SerializedName("CalculationDescription")
	val calculationDescription: Any? = null,

	@field:SerializedName("Type")
	val type: Int = 0,

	@field:SerializedName("TypeView")
	val typeView: String = "",

	@field:SerializedName("Id")
	val id: String = "",

	@field:SerializedName("IsRestrictedDest")
	val isRestrictedDest: Boolean = false,

	@field:SerializedName("CompanyCode")
	val companyCode: String = ""
)