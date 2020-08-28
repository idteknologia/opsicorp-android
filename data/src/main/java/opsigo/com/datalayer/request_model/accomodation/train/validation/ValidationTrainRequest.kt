package opsigo.com.datalayer.request_model.accomodation.train.validation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ValidationTrainRequest(

		@field:SerializedName("Origin")
	var origin: String = "",

		@field:SerializedName("Destination")
	var destination: String = "",

		@field:SerializedName("LevelJob")
	var levelJob: String = "",

		@field:SerializedName("Remarks")
	var remarks: List<Any> = ArrayList(),

		@field:SerializedName("FlightType")
	var flightType: Int = 0,

		@field:SerializedName("Segments")
	var segments: List<SegmentsItemValidationTrainRequest> = ArrayList(),

		@field:SerializedName("Purpose")
	var purpose: String = "",

		@field:SerializedName("Members")
	var members: List<String> = ArrayList(),

		@field:SerializedName("Contact")
	var contact: ContactValidationTrainRequest = ContactValidationTrainRequest()
)