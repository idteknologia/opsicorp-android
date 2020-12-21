package opsigo.com.datalayer.model

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.model.accomodation.flight.SegmentFlightEntity

@Generated("com.robohorse.robopojogenerator")
data class TripFlightsItem(

		@field:SerializedName("Origin")
	val origin: String = "",

//		@field:SerializedName("Carrier")
//	val carrier: String = "",

		@field:SerializedName("BookingDate")
	val bookingDate: String = "",

		@field:SerializedName("TripPlanId")
	val tripPlanId: String = "",

		@field:SerializedName("IsRefundable")
	val isRefundable: Boolean = false,

		@field:SerializedName("Airline")
	val airline: Int = 0,

		@field:SerializedName("Segments")
	val segments: List<SegmentFlightEntity> = ArrayList(),

		@field:SerializedName("Payments")
	val payments: List<PaymentsItem?>? = null,

		@field:SerializedName("OriginView")
	val originView: String = "",

		@field:SerializedName("ActionDate")
	val actionDate: String = "",

		@field:SerializedName("IsPostedHipchat")
	val isPostedHipchat: Boolean = false,

		@field:SerializedName("Remarks")
	val remarks: List<RemarksItem> = ArrayList(),
//	val remarks: List<Any?>? = null,

		@field:SerializedName("TicketNumber")
	val ticketNumber: String = "",

		@field:SerializedName("FlightType")
//	val flightType: String = "",
	val flightType: Int = 0,

		@field:SerializedName("PrgText")
	val prgText: String = "",

		@field:SerializedName("Child")
	val child: Int = 0,

		@field:SerializedName("OpsigoStatus")
	val opsigoStatus: String = "",

		@field:SerializedName("PaymentStatus")
	val paymentStatus: String = "",

		@field:SerializedName("FlightTypeView")
	val flightTypeView: String = "",

		@field:SerializedName("Status")
	val status: String = "",

		@field:SerializedName("PaymentReff")
	val paymentReff: String = "",

		@field:SerializedName("PaymentTimeLimit")
	val paymentTimeLimit: String = "",

		@field:SerializedName("IsEmailSent")
	val isEmailSent: Boolean = false,

		@field:SerializedName("TicketedDate")
	val ticketedDate: String = "",

		@field:SerializedName("PoNumber")
	val poNumber: String = "",

		@field:SerializedName("TicketDocName")
	val ticketDocName: String = "",

		@field:SerializedName("BookingDateView")
	val bookingDateView: String = "",

		@field:SerializedName("HasCostCenterUpdated")
	val hasCostCenterUpdated: Boolean = false,

		@field:SerializedName("PnrCode")
	val pnrCode: String = "",

		@field:SerializedName("FlightDurationPerNum")
	val flightDurationPerNum: List<String> = ArrayList<String>(),
//	val flightDurationPerNum: List<String?>? = null,

		@field:SerializedName("TimeLimitView")
	val timeLimitView: String = "",

		@field:SerializedName("Passengers")
	val passengers: List<PassengersItem?>? = null,

		@field:SerializedName("ApprovedBy")
	val approvedBy: String = "",

		@field:SerializedName("DestinationView")
	val destinationView: String = "",

		@field:SerializedName("TravelAgent")
	val travelAgent: String = "",

		@field:SerializedName("Id")
	val id: String = "",

		@field:SerializedName("PrgNum")
	val prgNum: Int = 0,

		@field:SerializedName("JobProgress")
	val jobProgress: JobProgress = JobProgress(),

		@field:SerializedName("Destination")
	val destination: String = "",

		@field:SerializedName("IsRemoved")
	val isRemoved: Boolean = false,

		@field:SerializedName("AmountBeforeReserved")
	val amountBeforeReserved: Int = 0,

		@field:SerializedName("OpsigoLastSync")
	val opsigoLastSync: String = "",

		@field:SerializedName("VoidDate")
	val voidDate: String = "",

		@field:SerializedName("DiffAmountInfo")
	val diffAmountInfo: String = "",

		@field:SerializedName("Infant")
	val infant: Int = 0,

		@field:SerializedName("PnrId")
	val pnrId: String? = null,

		@field:SerializedName("LinkReference")
	val linkReference: String = "",

		@field:SerializedName("ActionDateView")
	val actionDateView: String = "",

		@field:SerializedName("TicketedDateView")
	val ticketedDateView: String = "",

		@field:SerializedName("Adult")
	val adult: Int = 0,

		@field:SerializedName("PrgJobType")
	val prgJobType: String = "",

		@field:SerializedName("RemarkReject")
	val remarkReject: String = "",

		@field:SerializedName("TimeLimit")
	val timeLimit: String = "",

		@field:SerializedName("AirlineView")
	val airlineView: String = "",

		@field:SerializedName("Amount")
	val amount: String = "",

		@field:SerializedName("ContactRequest")
	val contact: Contact = Contact(),

		@field:SerializedName("IsVoid")
	val isVoid: Boolean = false,

		@field:SerializedName("TicketDocUrl")
	val ticketDocUrl: String = "",

		@field:SerializedName("TripItemId")
	val tripItemId: String = ""
)