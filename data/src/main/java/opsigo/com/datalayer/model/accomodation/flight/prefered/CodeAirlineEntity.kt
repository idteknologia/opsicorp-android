package opsigo.com.datalayer.model.accomodation.flight.prefered

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class CodeAirlineEntity(

	@field:SerializedName("IsInternational")
	val isInternational: Boolean? = null,

	@field:SerializedName("IsPaymentAfterBook")
	val isPaymentAfterBook: Boolean? = null,

	@field:SerializedName("IsFrequentFlyerAvailable")
	val isFrequentFlyerAvailable: Boolean? = null,

	@field:SerializedName("IsDomestik")
	val isDomestik: Boolean? = null,

	@field:SerializedName("IataCode")
	val iataCode: String? = null,

	@field:SerializedName("AirlineName")
	val airlineName: String? = null,

	@field:SerializedName("IsFareRulesAvailable")
	val isFareRulesAvailable: Boolean? = null,

	@field:SerializedName("Image")
	val image: Any? = null,

	@field:SerializedName("ThumbImage")
	val thumbImage: Any? = null,

	@field:SerializedName("CodeApi")
	val codeApi: Int = 0,

	@field:SerializedName("IsSsrAvailable")
	val isSsrAvailable: Boolean? = null,

	@field:SerializedName("IsGds")
	val isGds: Boolean? = null,

	@field:SerializedName("CodeString")
	val codeString: String? = null,

	@field:SerializedName("OriginCountry")
	val originCountry: Any? = null,

	@field:SerializedName("IsPassportRequired")
	val isPassportRequired: Boolean? = null,

	@field:SerializedName("Id")
	val id: Int? = null,

	@field:SerializedName("IsSeatAvailable")
	val isSeatAvailable: Boolean? = null,

	@field:SerializedName("IsFareDetailsAvailable")
	val isFareDetailsAvailable: Boolean? = null,

	@field:SerializedName("FlowBook")
	val flowBook: Int? = null
)