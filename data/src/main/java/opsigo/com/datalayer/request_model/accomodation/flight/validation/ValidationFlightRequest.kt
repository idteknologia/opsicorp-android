package opsigo.com.datalayer.request_model.accomodation.flight.validation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ValidationFlightRequest(

		@field:SerializedName("Origin")
	var origin: String? = null,

		@field:SerializedName("Destination")
	var destination: String? = null,

		@field:SerializedName("Remarks")
	var remarks: List<String?>? = null,

		@field:SerializedName("contact")
	var contact: ContactValidationFlightRequest? = null,

		@field:SerializedName("FlightType")
	var flightType: Int? = null,

		@field:SerializedName("Segments")
	var segments: List<SegmentsItemRequest?>? = null,

		@field:SerializedName("Purpose")
	var purpose: String? = null,

		@field:SerializedName("Members")
	var members: List<String?>? = null
)