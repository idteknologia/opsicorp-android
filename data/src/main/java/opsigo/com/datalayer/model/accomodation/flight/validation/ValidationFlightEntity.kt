package opsigo.com.datalayer.model.accomodation.flight.validation

import com.google.gson.annotations.SerializedName

data class ValidationFlightEntity(

	@field:SerializedName("isSecurity")
	val isSecurity: Boolean = false,

	@field:SerializedName("restrictedDestination")
	val restrictedDestination: Boolean = false,

	@field:SerializedName("conflictFlight")
	val conflictFlight: String = "",

	@field:SerializedName("descAdvanceBooking")
	val descAdvanceBooking: String = "",

	@field:SerializedName("bookingResult")
	val bookingResult: BookingResult = BookingResult(),

	@field:SerializedName("securityMsg")
	val securityMsg: String = "",

	@field:SerializedName("isSecondary")
	val isSecondary: Boolean = false,

	@field:SerializedName("isAdvanceBooking")
	val isAdvanceBooking: Boolean = false,

	@field:SerializedName("isLowestFare")
	val isLowestFare: Boolean = false,

	@field:SerializedName("isAirlinePolicy")
	val isAirlinePolicy: Boolean = false,

	@field:SerializedName("listConflictBooking")
	val listConflictBooking: List<Any> = ArrayList()
)