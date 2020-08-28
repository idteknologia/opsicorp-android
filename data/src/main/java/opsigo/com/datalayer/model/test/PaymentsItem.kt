package opsigo.com.datalayer.model.test

import com.google.gson.annotations.SerializedName

data class PaymentsItem(

	@field:SerializedName("Amount")
	val amount: Double? = null,

	@field:SerializedName("Currency")
	val currency: String? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("Code")
	val code: String? = null,

	@field:SerializedName("TripFlightId")
	val tripFlightId: String? = null,

	@field:SerializedName("Seq")
	val seq: Int? = null
)