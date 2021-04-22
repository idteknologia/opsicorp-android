package opsigo.com.datalayer.model.accomodation.hotel.search_hotel

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class RoomsItem(

		@field:SerializedName("ProviderId")
	val providerId:String? = null,

		@field:SerializedName("PriceDetails")
	val priceDetails: List<PriceDetailsItem> = ArrayList(),

		@field:SerializedName("OrderPriority")
	val orderPriority: Int = 0,

		@field:SerializedName("CancelPolicySummaries")
	val cancelPolicySummaries:String? = null,

		@field:SerializedName("MessageNotComply")
	val messageNotComply: String? = null,

		@field:SerializedName("MealTypeName")
	val mealTypeName: String? = null,

		@field:SerializedName("IsNotComply")
	val isNotComply: Boolean = false,

		@field:SerializedName("IsDisplay")
	val isDisplay: Boolean = false,

		@field:SerializedName("LowestPricePerNight")
	val lowestPricePerNight: Double = 0.0,

		@field:SerializedName("IsFullCharge")
	val isFullCharge: Boolean = false,

		@field:SerializedName("MealType")
	val mealType: String? = null,

		@field:SerializedName("RoomKey")
	val roomKey: String? = null,

		@field:SerializedName("RoomSelector")
	val roomSelector:String? = null,

		@field:SerializedName("ProviderCode")
	val providerCode: String? = null,

		@field:SerializedName("Currency")
	val currency: String? = null,

		@field:SerializedName("IsGuaranteedBooking")
	val isGuaranteedBooking: Boolean = false,

		@field:SerializedName("Status")
	val status: Int = 0,

		@field:SerializedName("ProviderRoomCode")
	val providerRoomCode:String? = null,

		@field:SerializedName("RoomName")
	val roomName: String? = null,

		@field:SerializedName("RateName")
	val rateName:String? = null,

		@field:SerializedName("ProviderHotelCode")
	val providerHotelCode:String? = null,

		@field:SerializedName("RoomHash")
	val roomHash: String? = null,

		@field:SerializedName("RoomSubText")
	val roomSubText: String? = null,

		@field:SerializedName("AveragePrice")
	val averagePrice: Double = 0.0,

		@field:SerializedName("AveragePerNight")
	val averagePerNight: Double = 0.0,

		@field:SerializedName("RateCode")
	val rateCode:String? = null,

		@field:SerializedName("MaxPricePerNight")
	val maxPricePerNight: Double = 0.0,

		@field:SerializedName("AdditionalPrice")
	val additionalPrice: AdditionalPrice = AdditionalPrice(),

		@field:SerializedName("IncludeBreakfast")
	val includeBreakfast: Boolean = false,

		@field:SerializedName("TotalPrice")
	val totalPrice: Double = 0.0
)