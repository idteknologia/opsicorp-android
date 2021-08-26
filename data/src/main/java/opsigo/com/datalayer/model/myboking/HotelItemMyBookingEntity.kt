package opsigo.com.datalayer.model.myboking

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HotelItemMyBookingEntity(

	@field:SerializedName("MapUri")
	val mapUri: String? = null,

	@field:SerializedName("StatusEnum")
	val statusEnum: Int? = null,

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("CheckInDateDisplay")
	val checkInDateDisplay: String? = null,

	@field:SerializedName("RoomsTotal")
	val roomsTotal: Int? = null,

	@field:SerializedName("RemarkHotel")
	val remarkHotel: String? = null,

	@field:SerializedName("Address")
	val address: String? = null,

	@field:SerializedName("CancellationInfo")
	val cancellationInfo: String? = null,

	@field:SerializedName("CheckInDate")
	val checkInDate: String? = null,

	@field:SerializedName("ImageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("City")
	val city: String? = null,

	@field:SerializedName("DurationDays")
	val durationDays: Int? = null,

	@field:SerializedName("BookingContact")
	val bookingContact: String? = null,

	@field:SerializedName("PnrCode")
	val pnrCode: String? = null,

	@field:SerializedName("HotelRating")
	val hotelRating: Int = 0,

	@field:SerializedName("RoomCategory")
	val roomCategory: String? = null,

	@field:SerializedName("Area")
	val area: String? = null,

	@field:SerializedName("FreeBreakfast")
	val freeBreakfast: Boolean? = null,

	@field:SerializedName("RoomType")
	val roomType: String? = null,

	@field:SerializedName("Guests")
	val guests: List<GuestsItem?>? = null,

	@field:SerializedName("Country")
	val country: String? = null,

	@field:SerializedName("HotelName")
	val hotelName: String? = null,

	@field:SerializedName("RoomService")
	val roomService: String? = null,

	@field:SerializedName("CountryCode")
	val countryCode: String? = null
):Parcelable

@Parcelize
data class GuestsItem(

	@field:SerializedName("Type")
	val type: String? = null,

	@field:SerializedName("SeatName")
	val seatName: String? = null,

	@field:SerializedName("FirstName")
	val firstName: String? = null,

	@field:SerializedName("IdNumber")
	val idNumber: String? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("Index")
	val index: Int? = null,

	@field:SerializedName("LastName")
	val lastName: String? = null,

	@field:SerializedName("SeatNumber")
	val seatNumber: String? = null,

	@field:SerializedName("IdType")
	val idType: String? = null,

	@field:SerializedName("BirthDate")
	val birthDate: String? = null
):Parcelable
