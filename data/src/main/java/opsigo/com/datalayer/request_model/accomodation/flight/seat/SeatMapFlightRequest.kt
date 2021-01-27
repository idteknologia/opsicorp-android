package opsigo.com.datalayer.request_model.accomodation.flight.seat

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.request_model.accomodation.flight.reservation.SegmentFlightsRequest

@Generated("com.robohorse.robopojogenerator")
data class SeatMapFlightRequest(

        @field:SerializedName("MultiClass")
	var multiClass: Boolean? = null,

        @field:SerializedName("TravelAgent")
	var travelAgent: String? = null,

        @field:SerializedName("Adult")
	var adult: Int? = null,

        @field:SerializedName("Infant")
	var infant: Int? = null,

        @field:SerializedName("Segments")
	var segments: List<SegmentFlightsRequest?>? = null,

        @field:SerializedName("Child")
	var child: Int? = null
)