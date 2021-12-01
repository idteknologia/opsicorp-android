package opsigo.com.datalayer.model.accomodation.hotel.confirmation

import com.google.gson.annotations.SerializedName

data class ConfirmationHotelEntity(

	@field:SerializedName("Message")
	val message: Any? = null,

	@field:SerializedName("IsError")
	val isError: Boolean? = null,

	@field:SerializedName("CorrelationId")
	val correlationId: Any? = null,

	@field:SerializedName("Confirmation")
	val confirmation: Confirmation = Confirmation()
)

data class Confirmation(

	@field:SerializedName("IsFullCharge")
	val isFullCharge: Boolean = false,

	@field:SerializedName("ConfirmationId")
	val confirmationId: String? = null,

	@field:SerializedName("Address")
	val address: String? = null,

	@field:SerializedName("CancelPolicySummaries")
	val cancelPolicySummaries: List<String> = ArrayList(),

	@field:SerializedName("RoomSelector")
	val roomSelector: Any? = null,

	@field:SerializedName("TotalPrice")
	val totalPrice: Double? = null,

	@field:SerializedName("IsGuaranteedBooking")
	val isGuaranteedBooking: Boolean = false,

	@field:SerializedName("Available")
	val available: Boolean = false,

	@field:SerializedName("CityName")
	val cityName: String? = null,

	@field:SerializedName("HotelName")
	val hotelName: String? = null,

	@field:SerializedName("AmountPerNight")
	val amountPerNight: Double? = null,

	@field:SerializedName("CityKey")
	val cityKey: String? = null
)
