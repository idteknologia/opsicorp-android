package opsigo.com.datalayer.model

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class TripItemTypesItem(

	@field:SerializedName("Type")
	val type: Int? = null,

	@field:SerializedName("TripItems")
	val tripItems: List<TripItemsItem>,//? = null,
	//val tripItems: List<TripItemsItem?>? = null,

	@field:SerializedName("Items")
	val items: List<Any?>? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("Name")
	val name: String? = null
)