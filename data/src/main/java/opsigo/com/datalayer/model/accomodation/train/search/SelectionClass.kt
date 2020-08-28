package opsigo.com.datalayer.model.accomodation.train.search

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.model.accomodation.train.search.PaxFaresItem

@Generated("com.robohorse.robopojogenerator")
data class SelectionClass(

		@field:SerializedName("SubClass")
	val subClass: String = "",

		@field:SerializedName("Seat")
	val seat: Int? = null,

		@field:SerializedName("Active")
	val active: Boolean? = null,

		@field:SerializedName("FareBasisCode")
	val fareBasisCode: String = "",

		@field:SerializedName("PaxFares")
	val paxFares: List<PaxFaresItem> = ArrayList(),

		@field:SerializedName("ClassName")
	val className: String = "",

		@field:SerializedName("ClassKey")
	val classKey: String = "",

		@field:SerializedName("Class")
	val classes: String = "",

		@field:SerializedName("Fareview")
	val fareview: String = "",

		@field:SerializedName("Fare")
	val fare: Double? = null
)