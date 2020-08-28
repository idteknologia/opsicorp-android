package opsigo.com.datalayer.model.accomodation.hotel.validation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class BookingResult(

		@field:SerializedName("Origin")
	val origin: String = "",

		@field:SerializedName("BookingDate")
	val bookingDate: String = "",

		@field:SerializedName("IsDownloadPnr")
	val isDownloadPnr: Boolean = false,

		@field:SerializedName("IsRefundable")
	val isRefundable: Boolean = false,

		@field:SerializedName("Airline")
	val airline: Int = 0,

		@field:SerializedName("Segments")
	val segments: String = "",

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
	val remarks: String = "",

		@field:SerializedName("NotComplyDetails")
	val notComplyDetails: List<Any> = ArrayList(),

		@field:SerializedName("FlightType")
	val flightType: Int = 0,

		@field:SerializedName("UseWizardSeatSelection")
	val useWizardSeatSelection: Boolean = false,

		@field:SerializedName("CallbackUri")
	val callbackUri: String = "",

		@field:SerializedName("Child")
	val child: Int = 0,

		@field:SerializedName("Status")
	val status: String = "",

		@field:SerializedName("FlightTripType")
	val flightTripType: Int = 0,

		@field:SerializedName("IsManual")
	val isManual: Boolean = false,

		@field:SerializedName("TicketedDate")
	val ticketedDate: String = "",

		@field:SerializedName("OpsigoPassengers")
	val opsigoPassengers: List<Any> = ArrayList(),

		@field:SerializedName("Code")
	val code: String = "",

		@field:SerializedName("ReasonCode")
	val reasonCode: String = "",

		@field:SerializedName("TimeLimitView")
	val timeLimitView: String = "",

		@field:SerializedName("Passengers")
	val passengers: List<Any> = ArrayList(),

		@field:SerializedName("CreatedView")
	val createdView: String = "",

		@field:SerializedName("Id")
	val id: String = "",

		@field:SerializedName("Journey")
	val journey: String = "",

		@field:SerializedName("Members")
	val members: List<String> = ArrayList(),

		@field:SerializedName("Hotel")
	val hotel: HotelValidationEntity = HotelValidationEntity(),

		@field:SerializedName("Reserved")
	val reserved: String = "",

		@field:SerializedName("BookingCode")
	val bookingCode: String = "",

		@field:SerializedName("Destination")
	val destination: String = "",

		@field:SerializedName("IsRemoved")
	val isRemoved: Boolean = false,

		@field:SerializedName("TrainName")
	val trainName: String = "",

		@field:SerializedName("Infant")
	val infant: Int = 0,

		@field:SerializedName("Created")
	val created: String = "",

		@field:SerializedName("PaymentType")
	val paymentType: Int = 0,

		@field:SerializedName("BookingProgress")
	val bookingProgress: List<Any> = ArrayList(),

		@field:SerializedName("Adult")
	val adult: Int = 0,

		@field:SerializedName("Supplier")
	val supplier: Int = 0,

		@field:SerializedName("EmployeeId")
	val employeeId: String = "",

		@field:SerializedName("TimeLimit")
	val timeLimit: String = "",

		@field:SerializedName("ListConflict")
	val listConflict: List<Any> = ArrayList(),

		@field:SerializedName("Purpose")
	val purpose: String = "",

		@field:SerializedName("TotalAmount")
	val totalAmount: Double = 0.0,

		@field:SerializedName("Contact")
	val contact: ContactHotelEntity = ContactHotelEntity(),

		@field:SerializedName("BosInvoiceNo")
	val bosInvoiceNo: String = "",

		@field:SerializedName("TripRemarks")
	val tripRemarks: List<Any> = ArrayList(),

		@field:SerializedName("LevelJob")
	val levelJob: String = "",

		@field:SerializedName("CauseViolatedTrainRules")
	val causeViolatedTrainRules: String = "",

		@field:SerializedName("CompanyCode")
	val companyCode: String = "",

		@field:SerializedName("IsViolatedTrainRules")
	val isViolatedTrainRules: Boolean = false
)