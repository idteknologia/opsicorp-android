package opsigo.com.datalayer.model.accomodation.train.search

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.model.accomodation.train.search.ChargesItem

@Generated("com.robohorse.robopojogenerator")
data class PaxFaresItem(

        @field:SerializedName("PaxType")
	val paxType: String? = null,

        @field:SerializedName("Charges")
	val charges: List<ChargesItem?>? = null,

        @field:SerializedName("Index")
	val index: Int? = null
)