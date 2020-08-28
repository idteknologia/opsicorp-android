package opsigo.com.datalayer.request_model.accomodation.train.reservation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.request_model.accomodation.train.validation.ContactValidationTrainRequest
import opsigo.com.datalayer.request_model.accomodation.train.validation.SegmentsItemValidationTrainRequest

@Generated("com.robohorse.robopojogenerator")
data class DataBookingReservationTrainRequest(

		@field:SerializedName("Origin")
		var origin: String = "",

		@field:SerializedName("Destination")
		var destination: String = "",

		@field:SerializedName("Segments")
		var segments: List<SegmentsItemReservationTrainRequest> = ArrayList(),

		@field:SerializedName("OpsigoPassengers")
		var opsigoPassengers: List<OpsigoPassengersItem> = ArrayList(),

		@field:SerializedName("ReasonCode")
		var reasonCode: String= "",

		@field:SerializedName("FlightTripType")
		var trainType: String = "",

		@field:SerializedName("Contact")
		var contact: ContactValidationTrainRequest = ContactValidationTrainRequest(),

		@field:SerializedName("Members")
		var members: List<String> = ArrayList(),

		@field:SerializedName("Remarks")
		var remarks: List<Any> = ArrayList(),

		@field:SerializedName("FlightType")
		var flightType: String = ""
)