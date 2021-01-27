package opsigo.com.datalayer.request_model.accomodation.flight.ssr

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SsrRequest(

        @field:SerializedName("TravelAgent")
	var travelAgent: String? = null,

        @field:SerializedName("SegmentList")
	var segmentList: List<SegmentListItemRequest?>? = null,

        @field:SerializedName("Adult")
	var adult: Int? = null,

        @field:SerializedName("Infant")
	var infant: Int? = null,

        @field:SerializedName("Child")
	var child: Int? = null
)