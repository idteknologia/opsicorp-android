package opsigo.com.datalayer.request_model.accomodation.flight.seat

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SeatMapFlightRequest(

        @field:SerializedName("MultiClass")
	val multiClass: Boolean? = null,

        @field:SerializedName("TravelAgent")
	val travelAgent: String? = null,

        @field:SerializedName("Adult")
	val adult: Int? = null,

        @field:SerializedName("Infant")
	val infant: Int? = null,

        @field:SerializedName("Segments")
	val segments: List<SegmenFlightRequest?>? = null,

        @field:SerializedName("Child")
	val child: Int? = null
)