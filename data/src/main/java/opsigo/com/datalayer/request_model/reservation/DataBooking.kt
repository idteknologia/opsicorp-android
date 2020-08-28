package opsigo.com.datalayer.request_model.reservation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class DataBooking(

        @field:SerializedName("Origin")
	val origin: String = "",

        @field:SerializedName("Destination")
	val destination: String = "",

        @field:SerializedName("isDownloadPnr")
	val isDownloadPnr: String = "",

        @field:SerializedName("FlightTripType")
	val flightTripType: String = "",

        @field:SerializedName("isManual")
	val isManual: String = "",

        @field:SerializedName("FlightType")
	val flightType: String = "",

        @field:SerializedName("Segments")
	val segments: List<SegmentsItem?>? = null,

        @field:SerializedName("Members")
	val members: List<String?>? = null
)