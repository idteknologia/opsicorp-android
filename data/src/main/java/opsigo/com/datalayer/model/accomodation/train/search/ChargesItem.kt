package opsigo.com.datalayer.model.accomodation.train.search

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ChargesItem(

	@field:SerializedName("CurrencyCode")
	val currencyCode: String = "",

	@field:SerializedName("ChargeCode")
	val chargeCode: String = "",

	@field:SerializedName("ChargeTypeString")
	val chargeTypeString: String = "",

	@field:SerializedName("ForeignCurrency")
	val foreignCurrency: Any? = null,

	@field:SerializedName("Amount")
	val amount: Double = 0.0,

	@field:SerializedName("Currency")
	val currency: Any? = null,

	@field:SerializedName("Text")
	val text: Any? = null,

	@field:SerializedName("Code")
	val code: Any? = null,

	@field:SerializedName("ForeignCurrencyCode")
	val foreignCurrencyCode: Any? = null,

	@field:SerializedName("ForeignAmount")
	val foreignAmount: Double = 0.0
)