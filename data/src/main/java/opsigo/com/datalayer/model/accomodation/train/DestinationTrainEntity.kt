package opsigo.com.datalayer.model.accomodation.train

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class DestinationTrainEntity(

	@field:SerializedName("Group")
	val group: Any? = null,

	@field:SerializedName("Value")
	val value: String = "",

	@field:SerializedName("Text")
	val text: String = "",

	@field:SerializedName("Selected")
	val selected: Boolean = false,

	@field:SerializedName("Disabled")
	val disabled: Boolean = false
)