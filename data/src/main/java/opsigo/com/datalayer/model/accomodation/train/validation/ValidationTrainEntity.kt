package opsigo.com.datalayer.model.accomodation.train.validation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ValidationTrainEntity(

		@field:SerializedName("isSecurity")
	val isSecurity: Boolean = false,

		@field:SerializedName("bookingResult")
	val bookingResult: BookingResultReservationEntity = BookingResultReservationEntity(),

		@field:SerializedName("securityMsg")
	val securityMsg: String  = "",

		@field:SerializedName("isViolated")
	val isViolated: Boolean = false,

		@field:SerializedName("message")
	val message: String  = "",

		@field:SerializedName("listConflictBooking")
	val listConflictBooking: List<Any> = ArrayList(),

		@field:SerializedName("conflict")
	val conflict: String  = ""
)