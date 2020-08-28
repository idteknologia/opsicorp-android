package opsigo.com.datalayer.request_model.accomodation.hotel.list_company

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ListCompanyEntity(

		@field:SerializedName("GroupName")
	val groupName: String? = "",

		@field:SerializedName("Options")
	val options: List<OptionsItem?>? = ArrayList(),

		@field:SerializedName("GroupCode")
	val groupCode: String? = "",

		@field:SerializedName("CountryCode")
	val countryCode: String? = ""
)