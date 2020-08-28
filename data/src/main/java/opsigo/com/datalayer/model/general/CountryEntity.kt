package opsigo.com.datalayer.model.general

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class CountryEntity(

	@field:SerializedName("message")
	val message: String = "",

	@field:SerializedName("data")
	val data: List<ResultItemCountry> = ArrayList()
	//val data: List<ResultItemCountry?>? = null,


)