package opsigo.com.datalayer.model.accomodation.hotel.confirmation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class DataConfirmationHotel(

	@field:SerializedName("PriceDetails")
	val priceDetails: List<PriceDetailsItem?>? = null,

	@field:SerializedName("Email")
	val email: String= "",

	@field:SerializedName("Address")
	val address: String= "",

	@field:SerializedName("CheckInDate")
	val checkInDate: String= "",

	@field:SerializedName("CountryName")
	val countryName: String= "",

	@field:SerializedName("TotalNight")
	val totalNight: Int? = null,

	@field:SerializedName("CancelPolicySummaries")
	val cancelPolicySummaries: List<String>? = null,

	@field:SerializedName("GuestPassport")
	val guestPassport: String= "",

	@field:SerializedName("Latitude")
	val latitude: Double = 0.0,

	@field:SerializedName("StarRating")
	val starRating: Double = 0.0,

	@field:SerializedName("LowestPricePerNight")
	val lowestPricePerNight: Double = 0.0,

	@field:SerializedName("IsFullCharge")
	val isFullCharge: Boolean = false,

	@field:SerializedName("RoomSelector")
	val roomSelector: String? = "",

	@field:SerializedName("Phone")
	val phone: String= "",

	@field:SerializedName("ProviderCode")
	val providerCode: String= "",

	@field:SerializedName("Currency")
	val currency: String= "",

	@field:SerializedName("IsGuaranteedBooking")
	val isGuaranteedBooking: Boolean = false,

	@field:SerializedName("Available")
	val available: Boolean = false,

	@field:SerializedName("HotelName")
	val hotelName: String= "",

	@field:SerializedName("CityKey")
	val cityKey: String= "",

	@field:SerializedName("CancelLimitDisplay")
	val cancelLimitDisplay: String= "",

	@field:SerializedName("ConfirmationId")
	val confirmationId: String= "",

	@field:SerializedName("BreakfastName")
	val breakfastName: String= "",

	@field:SerializedName("CountGuest")
	val countGuest: Int? = null,

	@field:SerializedName("RoomName")
	val roomName: String= "",

	@field:SerializedName("CountRoom")
	val countRoom: Int? = null,

	@field:SerializedName("CancelLimit")
	val cancelLimit: String= "",

	@field:SerializedName("CityName")
	val cityName: String= "",

	@field:SerializedName("Longitude")
	val longitude: Double = 0.0,

	@field:SerializedName("HotelKey")
	val hotelKey: String= "",

	@field:SerializedName("AveragePerNight")
	val averagePerNight: Double = 0.0,

	@field:SerializedName("ServiceFee")
	val serviceFee: Double = 0.0,

	@field:SerializedName("StayDateDisplay")
	val stayDateDisplay: String= "",

	@field:SerializedName("MaxPricePerNight")
	val maxPricePerNight: Double = 0.0,

	@field:SerializedName("IncludeBreakfast")
	val includeBreakfast: Boolean = false,

	@field:SerializedName("TotalPrice")
	val totalPrice: Double = 0.0,

	@field:SerializedName("AmountPerNight")
	val amountPerNight: Double = 0.0,

	@field:SerializedName("MarkupTotal")
	val markupTotal: Double = 0.0,

	@field:SerializedName("IsoCountryCode")
	val isoCountryCode: String= "",

	@field:SerializedName("CheckOutDate")
	val checkOutDate: String= ""
)