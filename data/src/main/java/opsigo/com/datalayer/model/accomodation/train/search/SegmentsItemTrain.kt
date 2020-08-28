package opsigo.com.datalayer.model.accomodation.train.search

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.model.accomodation.train.search.PaxFaresItem

@Generated("com.robohorse.robopojogenerator")
data class SegmentsItemTrain(

        @field:SerializedName("SubClass")
	val subClass: String = "",

        @field:SerializedName("Seat")
	var seat: String = "",

        @field:SerializedName("Active")
	val active: Boolean = false,

        @field:SerializedName("FareBasisCode")
	val fareBasisCode: String = "",

        @field:SerializedName("PaxFares")
	val paxFares: List<PaxFaresItem?>? = null,

        @field:SerializedName("ClassName")
	val className: String = "",

        @field:SerializedName("ClassKey")
	val classKey: String = "",

        @field:SerializedName("Class")
	val classes: String = "",

        @field:SerializedName("Fareview")
	val fareview: String = "",

		@field:SerializedName("IsComply")
		val isComply: Boolean = false,

        @field:SerializedName("Fare")
	val fare: Double = 0.0

)