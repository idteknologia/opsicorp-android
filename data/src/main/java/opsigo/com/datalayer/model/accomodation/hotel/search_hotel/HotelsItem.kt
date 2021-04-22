package opsigo.com.datalayer.model.accomodation.hotel.search_hotel

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class HotelsItem(

		@field:SerializedName("Email")
	val email: String? = null,

		@field:SerializedName("Address")
	val address: String? = null,

		@field:SerializedName("CountryName")
	val countryName: String? = null,

		@field:SerializedName("MapImageUri")
	val mapImageUri: String? = null,

		@field:SerializedName("Website")
	val website: String? = null,

		@field:SerializedName("Latitude")
	val latitude: Double = 0.0,

		@field:SerializedName("StarRating")
	val starRating: Double = 0.0,

		@field:SerializedName("Checkout")
	val checkout: String? = null,

		@field:SerializedName("Phone")
	val phone: String? = null,

		@field:SerializedName("ProviderCode")
	val providerCode: String? = null,

		@field:SerializedName("Currency")
	val currency: String? = null,

		@field:SerializedName("HaveWifi")
	val haveWifi: Boolean = false,

		@field:SerializedName("Facsimile")
	val facsimile: String? = null,

		@field:SerializedName("CorrelationId")
	val correlationId: String? = null,

		@field:SerializedName("HotelName")
	val hotelName: String? = null,

		@field:SerializedName("CityKey")
	val cityKey: String? = null,

		@field:SerializedName("MapUri")
	val mapUri: String? = null,

		@field:SerializedName("Status")
	val status: Int = 0,

		@field:SerializedName("IsHsre")
	val isHsre: Boolean = false,

		@field:SerializedName("PlaceRating")
	val placeRating: Double = 0.0,

		@field:SerializedName("Mapped")
	val mapped: Boolean = false,

		@field:SerializedName("ProviderHotelCode")
	val providerHotelCode: String? = null,

		@field:SerializedName("ThumbUri")
	val thumbUri: String? = null,

		@field:SerializedName("Duration")
	val duration: Int = 0,

		@field:SerializedName("CityName")
	val cityName: String? = null,

		@field:SerializedName("ImageUri")
	val imageUri: String? = null,

		@field:SerializedName("Longitude")
	val longitude: Double = 0.0,

		@field:SerializedName("AveragePrice")
	val averagePrice: Double = 0.0,

		@field:SerializedName("Facilities")
	val facilities: List<String?>? = null,

		@field:SerializedName("Rooms")
	val rooms: List<RoomsItem> = ArrayList(),

		@field:SerializedName("HotelKey")
	val hotelKey: String? = null,

		@field:SerializedName("Area")
	val area: String? = null,

		@field:SerializedName("LowestRoomPrice")
	val lowestRoomPrice: Double = 0.0,

		@field:SerializedName("Price")
	val price: Double = 0.0,

		@field:SerializedName("PricePerRoomNight")
	val pricePerRoomNight: Double = 0.0,

		@field:SerializedName("Checkin")
	val checkin: String? = null,

		@field:SerializedName("PricePerNight")
	val pricePerNight: Double = 0.0,

		@field:SerializedName("IsoCountryCode")
	val isoCountryCode: String? = null,

		@field:SerializedName("Location")
	val location: String? = null
)