package opsigo.com.datalayer.model.signin.country

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class CountryEntity(

		@field:SerializedName("result")
	val result: List<ResultItemCountry> = ArrayList(),

		@field:SerializedName("total")
	val total: String = ""
)