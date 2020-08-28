package opsigo.com.datalayer.request_model.accomodation.flight.reservation

import com.google.gson.annotations.SerializedName

//import opsigo.com.datalayer.request_model.accomodation.train.reservation.OpsigoPassengersItem

data class DataBookingFlightRequest(

		@field:SerializedName("Origin")
	var origin: String = "",

		@field:SerializedName("Destination")
	var destination: String = "",

		@field:SerializedName("isDownloadPnr")
	var isDownloadPnr: Boolean = false,

		@field:SerializedName("FlightTripType")
	var flightTripType: Int = 0,

//
//		@field:SerializedName("Remarks")
//	var remarks: List<String?>? = null,

		@field:SerializedName("isManual")
	var isManual: Boolean = false,

		@field:SerializedName("FlightType")
	var flightType: String = "",

		/*
		@field:SerializedName("Segments")
		var segments: List<SegmentsItemReservationTrainRequest> = ArrayList(),
		 */

		@field:SerializedName("Segments")
	var segments: List<SegmentFlightsRequest> = ArrayList(),
//	var segments: List<SegmentsItem?>? = null,

//		@field:SerializedName("OpsigoPassengers")
//	var opsigoPassengers: List<OpsigoPassengersItem?>? = null,

		@field:SerializedName("OpsigoPassengers")
	var opsigoPassengers: List<PassangersFlightRequest> = ArrayList(),

		@field:SerializedName("Members")
		var members: List<String> = ArrayList(),

		@field:SerializedName("Remarks")
		var remarks: List<Any> = ArrayList(),

		@field:SerializedName("Contact")
	var contact: ContactFlightRequest = ContactFlightRequest()
//	var contact: Contact? = null
//var contact: ContactValidationTrainRequest = ContactValidationTrainRequest(),
)