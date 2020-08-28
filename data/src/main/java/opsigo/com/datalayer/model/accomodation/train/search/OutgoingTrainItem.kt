package opsigo.com.datalayer.model.accomodation.train.search

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class OutgoingTrainItem(

        @field:SerializedName("Origin")
	val origin: String = "",

        @field:SerializedName("ArriveDateTimeView")
	val arriveDateTimeView: String = "",

        @field:SerializedName("Destination")
	val destination: String = "",

        @field:SerializedName("DepartureTime")
	val departureTime: String = "",

        @field:SerializedName("TrainName")
	val trainName: String = "",

        @field:SerializedName("Segments")
	val segments: List<SegmentsItemTrain> = ArrayList(),

        @field:SerializedName("Duration")
	val duration: String = "",

		@field:SerializedName("DurationShort")
		val durationTime: String = "",

        @field:SerializedName("Sequence")
	val sequence: String = "",

        @field:SerializedName("DepartDate")
	val departDate: String = "",

        @field:SerializedName("CarrierNumber")
	val carrierNumber: String = "",

        @field:SerializedName("ArrivalDate")
	val arrivalDate: String = "",

        @field:SerializedName("DepartDateTimeView")
	val departDateTimeView: String = "",

        @field:SerializedName("SelectionClass")
	val selectionClass: SelectionClass = SelectionClass(),

        @field:SerializedName("DepartureDate")
	val departureDate: String = "",

        @field:SerializedName("SelectionClassKey")
	val selectionClassKey: String = "",

        @field:SerializedName("ArriveDate")
	val arriveDate: String = "",

        @field:SerializedName("JourneyCode")
	val journeyCode: String = "",

        @field:SerializedName("ArrivalTime")
	val arrivalTime: String = ""
)