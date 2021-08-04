package opsigo.com.datalayer.model.travel_request

import com.google.gson.annotations.SerializedName

data class CashAdvanceEntity(

	@field:SerializedName("Currency")
	val currency: String? = null,

	@field:SerializedName("MaxAmount")
	val maxAmount: Double = 0.0,

	@field:SerializedName("IsAllowed")
	val isAllowed: Boolean? = null
)
