package opsigo.com.datalayer.model.accomodation.flight.validation

import com.google.gson.annotations.SerializedName

data class BookingResult(

		@field:SerializedName("Origin")
	val origin: String = "",

		@field:SerializedName("BookingDate")
	val bookingDate: String = "",

		@field:SerializedName("IsDownloadPnr")
	val isDownloadPnr: Boolean? = null,

		@field:SerializedName("IsRefundable")
	val isRefundable: Boolean? = null,

		@field:SerializedName("Airline")
	val airline: Int? = null,

		@field:SerializedName("Segments")
	val segments: List<SegmentsItem> = ArrayList(),
		//List<SegmentsItemReservationEntity> = ArrayList(),

		@field:SerializedName("Payments")
	val payments: String = "",

		@field:SerializedName("ReservedView")
	val reservedView: String = "",

		@field:SerializedName("TripId")
	val tripId: String = "",

		@field:SerializedName("Pnr")
	val pnr: String = "",

		@field:SerializedName("TicketNumber")
	val ticketNumber: String = "",

		@field:SerializedName("Remarks")
	val remarks: List<String?>? = null,

		@field:SerializedName("FlightType")
	val flightType: Int? = null,

		@field:SerializedName("UseWizardSeatSelection")
	val useWizardSeatSelection: Boolean? = null,

		@field:SerializedName("CallbackUri")
	val callbackUri: String = "",

		@field:SerializedName("Child")
	val child: Int? = null,

		@field:SerializedName("Status")
	val status: String = "",

		@field:SerializedName("FlightTripType")
	val flightTripType: Int? = null,

		@field:SerializedName("IsManual")
	val isManual: Boolean? = null,

		@field:SerializedName("TicketedDate")
	val ticketedDate: String = "",

		@field:SerializedName("OpsigoPassengers")
	val opsigoPassengers: List<Any?>? = null,

		@field:SerializedName("Code")
	val code: String = "",

		@field:SerializedName("ReasonCode")
	val reasonCode: String = "",

		@field:SerializedName("TimeLimitView")
	val timeLimitView: String = "",

		@field:SerializedName("Passengers")
	val passengers: List<PassengersItem?>? = null,

		@field:SerializedName("CreatedView")
	val createdView: String = "",

		@field:SerializedName("Id")
	val id: String = "",

		@field:SerializedName("Journey")
	val journey: String = "",

		@field:SerializedName("Members")
	val members: List<String?>? = null,

		@field:SerializedName("Hotel")
	val hotel: String = "",

		@field:SerializedName("Reserved")
	val reserved: String = "",

		@field:SerializedName("BookingCode")
	val bookingCode: String = "",

		@field:SerializedName("Destination")
	val destination: String = "",

		@field:SerializedName("IsRemoved")
	val isRemoved: Boolean? = null,

		@field:SerializedName("TrainName")
	val trainName: String = "",

		@field:SerializedName("Infant")
	val infant: Int? = null,

		@field:SerializedName("Created")
	val created: String = "",

		@field:SerializedName("BookingProgress")
	val bookingProgress: List<Any?>? = null,

		@field:SerializedName("Adult")
	val adult: Int? = null,

		@field:SerializedName("Supplier")
	val supplier: Int? = null,

		@field:SerializedName("EmployeeId")
	val employeeId: String = "",

		@field:SerializedName("TimeLimit")
	val timeLimit: String = "",

		@field:SerializedName("ListConflict")
	val listConflict: List<Any?>? = null,

		@field:SerializedName("Purpose")
	val purpose: String = "",

		@field:SerializedName("TotalAmount")
	val totalAmount: Int? = null,

		@field:SerializedName("Contact")
	val contactReservationFlightEntity: ContactReservationFlightEntity? = null,

		@field:SerializedName("BosInvoiceNo")
	val bosInvoiceNo: String = "",

		@field:SerializedName("TripRemarks")
	val tripRemarks: List<Any?>? = null,

		@field:SerializedName("LevelJob")
	val levelJob: String = "",

		@field:SerializedName("CauseViolatedTrainRules")
	val causeViolatedTrainRules: String = "",

		@field:SerializedName("CompanyCode")
	val companyCode: String = "",

		@field:SerializedName("IsViolatedTrainRules")
	val isViolatedTrainRules: Boolean? = null
)