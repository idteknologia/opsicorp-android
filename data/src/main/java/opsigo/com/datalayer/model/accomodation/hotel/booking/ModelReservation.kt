package opsigo.com.datalayer.model.accomodation.hotel.booking

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ModelReservation(

		@field:SerializedName("Origin")
	val origin: String? = null,

		@field:SerializedName("BookingDate")
	val bookingDate: String? = null,

		@field:SerializedName("IsDownloadPnr")
	val isDownloadPnr: Boolean =false,

		@field:SerializedName("IsRefundable")
	val isRefundable: Boolean =false,

		@field:SerializedName("Airline")
	val airline: Int = 0,

		@field:SerializedName("Segments")
	val segments:String? = null,

		@field:SerializedName("Payments")
	val payments:String? = null,

		@field:SerializedName("ReservedView")
	val reservedView:String? = null,

		@field:SerializedName("TripId")
	val tripId: String? = null,

		@field:SerializedName("Pnr")
	val pnr:String? = null,

		@field:SerializedName("TicketNumber")
	val ticketNumber:String? = null,

		@field:SerializedName("Remarks")
	val remarks:String? = null,

		@field:SerializedName("NotComplyDetails")
	val notComplyDetails: List<Any> =ArrayList(),

		@field:SerializedName("FlightType")
	val flightType: Int = 0,

		@field:SerializedName("UseWizardSeatSelection")
	val useWizardSeatSelection: Boolean =false,

		@field:SerializedName("CallbackUri")
	val callbackUri:String? = null,

		@field:SerializedName("Child")
	val child: Int = 0,

		@field:SerializedName("Status")
	val status:String? = null,

		@field:SerializedName("FlightTripType")
	val flightTripType: Int = 0,

		@field:SerializedName("IsManual")
	val isManual: Boolean = false,

		@field:SerializedName("TicketedDate")
	val ticketedDate:String? = null,

		@field:SerializedName("OpsigoPassengers")
	val opsigoPassengers: List<Any> =ArrayList(),

		@field:SerializedName("Code")
	val code: String? = null,

		@field:SerializedName("ReasonCode")
	val reasonCode: String? = null,

		@field:SerializedName("TimeLimitView")
	val timeLimitView: String? = null,

		@field:SerializedName("Passengers")
	val passengers: List<PassengersItemHotel> = ArrayList(),

		@field:SerializedName("CreatedView")
	val createdView:String? = null,

		@field:SerializedName("Id")
	val id: String? = null,

		@field:SerializedName("Journey")
	val journey:String? = null,

		@field:SerializedName("Members")
	val members: List<String> = ArrayList(),

		@field:SerializedName("Hotel")
	val hotel: HotelReservation = HotelReservation(),

		@field:SerializedName("Reserved")
	val reserved: String? = null,

		@field:SerializedName("BookingCode")
	val bookingCode:String? = null,

		@field:SerializedName("Destination")
	val destination: String? = null,

		@field:SerializedName("IsRemoved")
	val isRemoved: Boolean = false,

		@field:SerializedName("TrainName")
	val trainName:String? = null,

		@field:SerializedName("Infant")
	val infant: Int = 0,

		@field:SerializedName("Created")
	val created: String? = null,

		@field:SerializedName("PaymentType")
	val paymentType: Int = 0,

		@field:SerializedName("BookingProgress")
	val bookingProgress: List<Any> =ArrayList(),

		@field:SerializedName("Adult")
	val adult: Int = 0,

		@field:SerializedName("Supplier")
	val supplier: Int = 0,

		@field:SerializedName("EmployeeId")
	val employeeId: String? = null,

		@field:SerializedName("TimeLimit")
	val timeLimit: String? = null,

		@field:SerializedName("ListConflict")
	val listConflict: List<Any> =ArrayList(),

		@field:SerializedName("Purpose")
	val purpose:String? = null,

		@field:SerializedName("TotalAmount")
	val totalAmount: Double? = null,

		@field:SerializedName("Contact")
	val contact: ContactHotelEntity = ContactHotelEntity(),

		@field:SerializedName("BosInvoiceNo")
	val bosInvoiceNo:String? = null,

		@field:SerializedName("TripRemarks")
	val tripRemarks: List<Any> =ArrayList(),

		@field:SerializedName("LevelJob")
	val levelJob:String? = null,

		@field:SerializedName("CauseViolatedTrainRules")
	val causeViolatedTrainRules:String? = null,

		@field:SerializedName("CompanyCode")
	val companyCode:String? = null,

		@field:SerializedName("IsViolatedTrainRules")
	val isViolatedTrainRules: Boolean = false
)