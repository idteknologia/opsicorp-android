package opsigo.com.datalayer.model

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class PurposeEntity(

	@field:SerializedName("Group")
	val group: String = "",

	@field:SerializedName("Value")
	val value: String = "",

	@field:SerializedName("Text")
	val text: String = "",

	@field:SerializedName("Selected")
	val selected: String = "",

	@field:SerializedName("Disabled")
	val disabled: String = ""
)