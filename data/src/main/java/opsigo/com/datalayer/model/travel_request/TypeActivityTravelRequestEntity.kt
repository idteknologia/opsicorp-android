package opsigo.com.datalayer.model.travel_request

import com.google.gson.annotations.SerializedName

data class TypeActivityTravelRequestEntity(

	@field:SerializedName("TypeActivityTravelRequestEntity")
	val typeActivityTravelRequestEntity: List<TypeActivityTravelRequestEntityItem?>? = null
)

data class TypeActivityTravelRequestEntityItem(

	@field:SerializedName("Group")
	val group: Any? = null,

	@field:SerializedName("Value")
	val value: String? = null,

	@field:SerializedName("Text")
	val text: String? = null,

	@field:SerializedName("Selected")
	val selected: Boolean? = null,

	@field:SerializedName("Disabled")
	val disabled: Boolean? = null
)
