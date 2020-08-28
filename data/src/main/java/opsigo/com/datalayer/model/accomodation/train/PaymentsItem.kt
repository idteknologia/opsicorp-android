package opsigo.com.datalayer.model.accomodation.train

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class PaymentsItem(

	@field:SerializedName("Amount")
	val amount: Int = 0,

	@field:SerializedName("Currency")
	val currency: String = "",

	@field:SerializedName("Title")
	val title: String = "",

	@field:SerializedName("Id")
	val id: String = "",

	@field:SerializedName("Code")
	val code: String = "",

	@field:SerializedName("TripTrainId")
	val tripTrainId: String = "",

	@field:SerializedName("Seq")
	val seq: Int = 0
)