package opsigo.com.datalayer.model.listtripplan

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class PaymentsItem(

	@field:SerializedName("Amount")
	val amount: String = "",

	@field:SerializedName("Currency")
	val currency: String = "",

	@field:SerializedName("Title")
	val title: String = "",

	@field:SerializedName("Id")
	val id: String = "",

	@field:SerializedName("Code")
	val code: String = "",

	@field:SerializedName("TripFlightId")
	val tripFlightId: String = "",

	@field:SerializedName("Seq")
	val seq: String = ""
)