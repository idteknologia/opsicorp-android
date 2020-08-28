package opsigo.com.datalayer.model.approval.hotel

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.model.PassengersItem
import opsigo.com.datalayer.model.approval.hotel.Hotel

@Generated("com.robohorse.robopojogenerator")
data class TripHotelEntity(

		@field:SerializedName("Origin")
	val origin: Any? = null,

		@field:SerializedName("BookingDate")
	val bookingDate: String? = null,

		@field:SerializedName("IsDownloadPnr")
	val isDownloadPnr: Boolean? = null,

		@field:SerializedName("IsRefundable")
	val isRefundable: Boolean? = null,

		@field:SerializedName("Airline")
	val airline: Int? = null,

		@field:SerializedName("Segments")
	val segments: Any? = null,

		@field:SerializedName("Payments")
	val payments: Any? = null,

		@field:SerializedName("ReservedView")
	val reservedView: Any? = null,

		@field:SerializedName("TripId")
	val tripId: String? = null,

		@field:SerializedName("Pnr")
	val pnr: Any? = null,

		@field:SerializedName("TicketNumber")
	val ticketNumber: Any? = null,

		@field:SerializedName("Remarks")
	val remarks: List<Any?>? = null,

		@field:SerializedName("FlightType")
	val flightType: Int? = null,

		@field:SerializedName("UseWizardSeatSelection")
	val useWizardSeatSelection: Boolean? = null,

		@field:SerializedName("CallbackUri")
	val callbackUri: Any? = null,

		@field:SerializedName("Child")
	val child: Int? = null,

		@field:SerializedName("Status")
	val status: String? = null,

		@field:SerializedName("FlightTripType")
	val flightTripType: Int? = null,

		@field:SerializedName("IsManual")
	val isManual: Boolean? = null,

		@field:SerializedName("TicketedDate")
	val ticketedDate: Any? = null,

		@field:SerializedName("OpsigoPassengers")
	val opsigoPassengers: List<Any?>? = null,

		@field:SerializedName("Code")
	val code: Any? = null,

		@field:SerializedName("ReasonCode")
	val reasonCode: Any? = null,

		@field:SerializedName("TimeLimitView")
	val timeLimitView: Any? = null,

		@field:SerializedName("Passengers")
	val passengers: List<PassengersItem?>? = null,

		@field:SerializedName("CreatedView")
	val createdView: Any? = null,

		@field:SerializedName("Id")
	val id: String? = null,

		@field:SerializedName("Journey")
	val journey: Any? = null,

		@field:SerializedName("Members")
	val members: List<Any?>? = null,

		@field:SerializedName("Hotel")
	val hotel: Hotel? = null,

		@field:SerializedName("Reserved")
	val reserved: String? = null,

		@field:SerializedName("BookingCode")
	val bookingCode: Any? = null,

		@field:SerializedName("Destination")
	val destination: Any? = null,

		@field:SerializedName("IsRemoved")
	val isRemoved: Boolean? = null,

		@field:SerializedName("TrainName")
	val trainName: Any? = null,

		@field:SerializedName("Infant")
	val infant: Int? = null,

		@field:SerializedName("Created")
	val created: String? = null,

		@field:SerializedName("BookingProgress")
	val bookingProgress: List<Any?>? = null,

		@field:SerializedName("Adult")
	val adult: Int? = null,

		@field:SerializedName("Supplier")
	val supplier: Int? = null,

		@field:SerializedName("TimeLimit")
	val timeLimit: String? = null,

		@field:SerializedName("ListConflict")
	val listConflict: List<Any?>? = null,

		@field:SerializedName("TotalAmount")
	val totalAmount: Double? = null,

		@field:SerializedName("Contact")
	val contact: Any? = null,

		@field:SerializedName("BosInvoiceNo")
	val bosInvoiceNo: Any? = null,

		@field:SerializedName("TripRemarks")
	val tripRemarks: List<Any?>? = null,

		@field:SerializedName("LevelJob")
	val levelJob: Any? = null,

		@field:SerializedName("CauseViolatedTrainRules")
	val causeViolatedTrainRules: Any? = null,

		@field:SerializedName("CompanyCode")
	val companyCode: Any? = null,

		@field:SerializedName("IsViolatedTrainRules")
	val isViolatedTrainRules: Boolean? = null
)