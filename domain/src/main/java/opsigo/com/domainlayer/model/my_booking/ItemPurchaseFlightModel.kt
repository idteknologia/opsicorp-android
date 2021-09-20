package opsigo.com.domainlayer.model.my_booking

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemFlightModel(
	val status: String? = null,
	val passengers: List<PassengersItemFLight?>? = null,
	val destinationCity: String? = null,
	val num: Int? = null,
	val segments: List<SegmentsItem?>? = null,
	val originCity: String? = null,
	val pnrCode: String? = null
):Parcelable

@Parcelize
data class SegmentsItem(
	val origin: String? = null,
	val status: String? = null,
	val destination: String? = null,
	val airlineImageUrl: String? = null,
	val destinationCity: String? = null,
	val num: Int? = null,
	val airlineName: String? = null,
	val originCity: String? = null,
	val duration: String? = null,
	val classCategory: String? = null,
	val destinationAirport: String? = null,
	val pnrCode: String? = null,
	val originAirport: String? = null,
	val classCode: String? = null,
	val departureTimeDisplay: String? = null,
	val flightNumber: String? = null,
	val departureDate: String? = null,
	val departureDateDisplay: String? = null,
	val seq: Int? = null
):Parcelable

@Parcelize
data class PassengersItemFLight(
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
): Parcelable
