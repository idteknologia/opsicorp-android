package opsigo.com.domainlayer.model.my_booking

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//@Parcelize
data class ItemPurchaseHotelModel(
	val mapUri: String? = null,
	val statusEnum: Int? = null,
	val status: String? = null,
	val checkInDateDisplay: String? = null,
	val roomsTotal: Int? = null,
	val remarkHotel: String? = null,
	val address: String? = null,
	val cancellationInfo: String? = null,
	val checkInDate: String? = null,
	val imageUrl: String? = null,
	val city: String? = null,
	val durationDays: Int? = null,
	val bookingContact: String? = null,
	val pnrCode: String? = null,
	val hotelRating: Int? = null,
	val roomCategory: String? = null,
	val area: String? = null,
	val freeBreakfast: Boolean? = null,
	val roomType: String? = null,
	val guests: List<GuestsItem?>? = null,
	val country: String? = null,
	val hotelName: String? = null,
	val roomService: String? = null,
	val countryCode: String? = null
)//:Parcelable

//@Parcelize
data class GuestsItem(
	val type: String? = null,
	val seatName: String? = null,
	val firstName: String? = null,
	val idNumber: String? = null,
	val title: String? = null,
	val index: Int? = null,
	val lastName: String? = null,
	val seatNumber: String? = null,
	val idType: String? = null,
	val birthDate: String? = null
)//:Parcelable

