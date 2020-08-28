package opsigo.com.datalayer.model.listtripplan

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class TripFlightsItem(

		@field:SerializedName("Origin")
	val origin: String = "",

		@field:SerializedName("BookingDate")
	val bookingDate: String = "",

		@field:SerializedName("TripPlanId")
	val tripPlanId: String = "",

		@field:SerializedName("IsRefundable")
	val isRefundable: String = "",

		@field:SerializedName("Airline")
	val airline: String = "",

		@field:SerializedName("Segments")
	val segments: List<String> = ArrayList<String>(),

		@field:SerializedName("Payments")
	val payments: List<PaymentsItem> = ArrayList<PaymentsItem>(),

		@field:SerializedName("OriginView")
	val originView: String = "",

		@field:SerializedName("ActionDate")
	val actionDate: String = "",

		@field:SerializedName("IsPostedHipchat")
	val isPostedHipchat: String = "",

		@field:SerializedName("Remarks")
	val remarks: List<String> = ArrayList<String>(),

		@field:SerializedName("TicketNumber")
	val ticketNumber: String = "",

		@field:SerializedName("FlightType")
	val flightType: String = "",

		@field:SerializedName("PrgText")
	val prgText: String = "",

		@field:SerializedName("Child")
	val child: String = "",

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
	val isEmailSent: String = "",

		@field:SerializedName("TicketedDate")
	val ticketedDate: String = "",

		@field:SerializedName("PoNumber")
	val poNumber: String = "",

		@field:SerializedName("TicketDocName")
	val ticketDocName: String = "",

		@field:SerializedName("BookingDateView")
	val bookingDateView: String = "",

		@field:SerializedName("HasCostCenterUpdated")
	val hasCostCenterUpdated: String = "",

		@field:SerializedName("PnrCode")
	val pnrCode: String = "",

		@field:SerializedName("FlightDurationPerNum")
	val flightDurationPerNum: String = "",

		@field:SerializedName("TimeLimitView")
	val timeLimitView: String = "",

		@field:SerializedName("Passengers")
	val passengers: List<String> = ArrayList<String>(),

		@field:SerializedName("ApprovedBy")
	val approvedBy: String = "",

		@field:SerializedName("DestinationView")
	val destinationView: String = "",

		@field:SerializedName("TravelAgent")
	val travelAgent: String = "",

		@field:SerializedName("Id")
	val id: String = "",

		@field:SerializedName("PrgNum")
	val prgNum: String = "",

		@field:SerializedName("JobProgress")
	val jobProgress: String = "",

		@field:SerializedName("Destination")
	val destination: String = "",

		@field:SerializedName("IsRemoved")
	val isRemoved: String = "",

		@field:SerializedName("AmountBeforeReserved")
	val amountBeforeReserved: String = "",

		@field:SerializedName("OpsigoLastSync")
	val opsigoLastSync: String = "",

		@field:SerializedName("VoidDate")
	val voidDate: String = "",

		@field:SerializedName("DiffAmountInfo")
	val diffAmountInfo: String = "",

		@field:SerializedName("Infant")
	val infant: String = "",

		@field:SerializedName("PnrId")
	val pnrId: String = "",

		@field:SerializedName("LinkReference")
	val linkReference: String = "",

		@field:SerializedName("ActionDateView")
	val actionDateView: String = "",

		@field:SerializedName("TicketedDateView")
	val ticketedDateView: String = "",

		@field:SerializedName("Adult")
	val adult: String = "",

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

//		@field:SerializedName("Contact")
//	val contact: Contact = Contact(),

		@field:SerializedName("IsVoid")
	val isVoid: String = "",

		@field:SerializedName("TicketDocUrl")
	val ticketDocUrl: String = "",

		@field:SerializedName("TripItemId")
	val tripItemId: String = ""
)