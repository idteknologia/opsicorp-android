package opsigo.com.datalayer.model.accomodation.flight.reservation

import com.google.gson.annotations.SerializedName

data class TripFlightsItem(

	@field:SerializedName("Origin")
	val origin: String? = null,

	@field:SerializedName("BookingDate")
	val bookingDate: String? = null,

	@field:SerializedName("TripPlanId")
	val tripPlanId: Any? = null,

	@field:SerializedName("IsRefundable")
	val isRefundable: Boolean? = null,

	@field:SerializedName("Airline")
	val airline: Int? = null,

	@field:SerializedName("BookingDateString")
	val bookingDateString: String? = null,

	@field:SerializedName("Segments")
	val segments: List<SegmentsItem?>? = null,

	@field:SerializedName("Payments")
	val payments: List<Any?>? = null,

	@field:SerializedName("OriginView")
	val originView: Any? = null,

	@field:SerializedName("ActionDate")
	val actionDate: String? = null,

	@field:SerializedName("IsPostedHipchat")
	val isPostedHipchat: Boolean? = null,

	@field:SerializedName("Remarks")
	val remarks: List<Any?>? = null,

	@field:SerializedName("TicketNumber")
	val ticketNumber: Any? = null,

	@field:SerializedName("TimeLimitString")
	val timeLimitString: String? = null,

	@field:SerializedName("FlightType")
	val flightType: Int? = null,

	@field:SerializedName("Carrier")
	val carrier: Any? = null,

	@field:SerializedName("PrgText")
	val prgText: String? = null,

	@field:SerializedName("Child")
	val child: Int? = null,

	@field:SerializedName("OpsigoStatus")
	val opsigoStatus: Any? = null,

	@field:SerializedName("PaymentStatus")
	val paymentStatus: Any? = null,

	@field:SerializedName("PaymentSuccess")
	val paymentSuccess: Boolean? = null,

	@field:SerializedName("FlightTypeView")
	val flightTypeView: String? = null,

	@field:SerializedName("Status")
	val status: Any? = null,

	@field:SerializedName("PaymentReff")
	val paymentReff: Any? = null,

	@field:SerializedName("PaymentTimeLimit")
	val paymentTimeLimit: String? = null,

	@field:SerializedName("IsEmailSent")
	val isEmailSent: Boolean? = null,

	@field:SerializedName("TicketedDate")
	val ticketedDate: Any? = null,

	@field:SerializedName("PoNumber")
	val poNumber: Any? = null,

	@field:SerializedName("TicketDocName")
	val ticketDocName: Any? = null,

	@field:SerializedName("BookingDateView")
	val bookingDateView: String? = null,

	@field:SerializedName("HasCostCenterUpdated")
	val hasCostCenterUpdated: Boolean? = null,

	@field:SerializedName("PnrCode")
	val pnrCode: String? = null,

	@field:SerializedName("FlightDurationPerNum")
	val flightDurationPerNum: List<String?>? = null,

	@field:SerializedName("TimeLimitView")
	val timeLimitView: String? = null,

	@field:SerializedName("Passengers")
	val passengers: List<PassengersItem?>? = null,

	@field:SerializedName("ApprovedBy")
	val approvedBy: String? = null,

	@field:SerializedName("DestinationView")
	val destinationView: Any? = null,

	@field:SerializedName("TravelAgent")
	val travelAgent: Any? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("PrgNum")
	val prgNum: Int? = null,

	@field:SerializedName("JobProgress")
	val jobProgress: Any? = null,

	@field:SerializedName("Destination")
	val destination: String? = null,

	@field:SerializedName("IsRemoved")
	val isRemoved: Boolean? = null,

	@field:SerializedName("AmountBeforeReserved")
	val amountBeforeReserved: Int? = null,

	@field:SerializedName("OpsigoLastSync")
	val opsigoLastSync: String? = null,

	@field:SerializedName("FreeBaggage")
	val freeBaggage: Any? = null,

	@field:SerializedName("VoidDate")
	val voidDate: String? = null,

	@field:SerializedName("DiffAmountInfo")
	val diffAmountInfo: Any? = null,

	@field:SerializedName("Infant")
	val infant: Int? = null,

	@field:SerializedName("PnrId")
	val pnrId: String? = null,

	@field:SerializedName("LinkReference")
	val linkReference: String? = null,

	@field:SerializedName("ActionDateView")
	val actionDateView: String? = null,

	@field:SerializedName("TicketedDateView")
	val ticketedDateView: Any? = null,

	@field:SerializedName("Adult")
	val adult: Int? = null,

	@field:SerializedName("PrgJobType")
	val prgJobType: String? = null,

	@field:SerializedName("RemarkReject")
	val remarkReject: Any? = null,

	@field:SerializedName("TimeLimit")
	val timeLimit: String? = null,

	@field:SerializedName("AirlineView")
	val airlineView: Any? = null,

	@field:SerializedName("Amount")
	val amount: Int? = null,

	@field:SerializedName("Contact")
	val contact: Contact? = null,

	@field:SerializedName("IsVoid")
	val isVoid: Boolean? = null,

	@field:SerializedName("TicketDocUrl")
	val ticketDocUrl: Any? = null,

	@field:SerializedName("TripItemId")
	val tripItemId: String? = null
)