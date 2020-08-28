package opsigo.com.datalayer.request_model.accomodation.flight.ssr

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SsrRequest(

        @field:SerializedName("TravelAgent")
	val travelAgent: String? = null,

        @field:SerializedName("SegmentList")
	val segmentList: List<SegmentListItemRequest?>? = null,

        @field:SerializedName("Adult")
	val adult: Int? = null,

        @field:SerializedName("Infant")
	val infant: Int? = null,

        @field:SerializedName("Child")
	val child: Int? = null
)