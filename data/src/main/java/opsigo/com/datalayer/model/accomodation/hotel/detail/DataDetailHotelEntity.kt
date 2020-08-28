package opsigo.com.datalayer.model.accomodation.hotel.detail

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class DataDetailHotelEntity(

        @field:SerializedName("IsHsre")
	val isHsre: Boolean = false,

        @field:SerializedName("Description")
	val description: String? = "",

        @field:SerializedName("Email")
	val email: String = "",

        @field:SerializedName("Address")
	val address: String = "",

        @field:SerializedName("Telephone")
	val telephone: String = "",

        @field:SerializedName("Images")
	val images: List<ImagesItem> = ArrayList(),

        @field:SerializedName("ProviderHotelCode")
	val providerHotelCode: String = "",

        @field:SerializedName("Website")
	val website: String = "",

        @field:SerializedName("Latitude")
	val latitude: Double? = null,

        @field:SerializedName("Longitude")
	val longitude: Double? = null,

        @field:SerializedName("Facilities")
	val facilities: List<FacilitiesItem> = ArrayList(),

        @field:SerializedName("Rooms")
	val rooms: List<Any> = ArrayList(),

        @field:SerializedName("HotelKey")
	val hotelKey: String = "",

        @field:SerializedName("Reviews")
	val reviews: List<ReviewsItem> = ArrayList(),

        @field:SerializedName("ProviderCode")
	val providerCode: String = "",

        @field:SerializedName("CancelPolicy")
	val cancelPolicy: String = "",

        @field:SerializedName("Facsimile")
	val facsimile: String = "",

        @field:SerializedName("HotelName")
	val hotelName: String = "",

        @field:SerializedName("IsoCountryCode")
	val isoCountryCode: String = ""
)