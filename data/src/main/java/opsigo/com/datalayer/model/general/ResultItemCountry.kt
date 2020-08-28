package opsigo.com.datalayer.model.general

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ResultItemCountry(

	@field:SerializedName("code")
	val code: String = "",

	@field:SerializedName("callCode")
//	val callCode: String? = null,
	val callCode: String = "",

	@field:SerializedName("name")
	val name: String = "",

	@field:SerializedName("threeLetterCode")
	val threeLetterCode: String = ""
)