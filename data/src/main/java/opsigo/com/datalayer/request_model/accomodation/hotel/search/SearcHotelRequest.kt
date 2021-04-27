package opsigo.com.datalayer.request_model.accomodation.hotel.search

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SearcHotelRequest(

	@field:SerializedName("DestinationKey")
	var destinationKey: String? = null,

	@field:SerializedName("Origin")
	var origin: String = "",

	@field:SerializedName("Destination")
	var destination: String = "",

	@field:SerializedName("CheckInDate")
	var checkInDate: String = "",

	@field:SerializedName("CountGuest")
	var countGuest: Int = 0,

	@field:SerializedName("purpose")
	var purpose: String = "",

	@field:SerializedName("GuestPassport")
	var guestPassport: String = "",

	@field:SerializedName("travelAgent")
	var travelAgent: String = "",

	@field:SerializedName("CountRoom")
	var countRoom: Int = 0,

	@field:SerializedName("HotelName")
	var hotelName: String = "",

	@field:SerializedName("CheckOutDate")
	var checkOutDate: String = "",

	@field:SerializedName("Latitude")
	var latitude: Double? = null,

	@field:SerializedName("Longitude")
	var longitude: Double? =null,

	@field:SerializedName("Adult")
	var adult: Int = 1,
	@field:SerializedName("Child")
	var child: Int = 0,
	@field:SerializedName("Infant")
	var infant: Int =0,

)