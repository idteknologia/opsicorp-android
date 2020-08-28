package opsigo.com.datalayer.model.accomodation.hotel.search_hotel

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class HotelsItem(

		@field:SerializedName("Email")
	val email: String = "",

		@field:SerializedName("Address")
	val address: String = "",

		@field:SerializedName("CountryName")
	val countryName: String = "",

		@field:SerializedName("MapImageUri")
	val mapImageUri: String = "",

		@field:SerializedName("Website")
	val website: String = "",

		@field:SerializedName("Latitude")
	val latitude: Double = 0.0,

		@field:SerializedName("StarRating")
	val starRating: Double = 0.0,

		@field:SerializedName("Checkout")
	val checkout: String = "",

		@field:SerializedName("Phone")
	val phone: String = "",

		@field:SerializedName("ProviderCode")
	val providerCode: String = "",

		@field:SerializedName("Currency")
	val currency: String = "",

		@field:SerializedName("HaveWifi")
	val haveWifi: Boolean = false,

		@field:SerializedName("Facsimile")
	val facsimile: String = "",

		@field:SerializedName("CorrelationId")
	val correlationId: String = "",

		@field:SerializedName("HotelName")
	val hotelName: String = "",

		@field:SerializedName("CityKey")
	val cityKey: String = "",

		@field:SerializedName("MapUri")
	val mapUri: String = "",

		@field:SerializedName("Status")
	val status: Int = 0,

		@field:SerializedName("IsHsre")
	val isHsre: Boolean = false,

		@field:SerializedName("PlaceRating")
	val placeRating: Double = 0.0,

		@field:SerializedName("Mapped")
	val mapped: Boolean = false,

		@field:SerializedName("ProviderHotelCode")
	val providerHotelCode: String = "",

		@field:SerializedName("ThumbUri")
	val thumbUri: String = "",

		@field:SerializedName("Duration")
	val duration: Int = 0,

		@field:SerializedName("CityName")
	val cityName: String = "",

		@field:SerializedName("ImageUri")
	val imageUri: String = "",

		@field:SerializedName("Longitude")
	val longitude: Double = 0.0,

		@field:SerializedName("AveragePrice")
	val averagePrice: Double = 0.0,

		@field:SerializedName("Facilities")
	val facilities: List<String?>? = null,

		@field:SerializedName("Rooms")
	val rooms: List<RoomsItem> = ArrayList(),

		@field:SerializedName("HotelKey")
	val hotelKey: String = "",

		@field:SerializedName("Area")
	val area: String = "",

		@field:SerializedName("LowestRoomPrice")
	val lowestRoomPrice: Double = 0.0,

		@field:SerializedName("Price")
	val price: Double = 0.0,

		@field:SerializedName("PricePerRoomNight")
	val pricePerRoomNight: Double = 0.0,

		@field:SerializedName("Checkin")
	val checkin: String = "",

		@field:SerializedName("PricePerNight")
	val pricePerNight: Double = 0.0,

		@field:SerializedName("IsoCountryCode")
	val isoCountryCode: String = "",

		@field:SerializedName("Location")
	val location: String = ""
)