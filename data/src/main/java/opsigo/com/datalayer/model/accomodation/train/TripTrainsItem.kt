package opsigo.com.datalayer.model.accomodation.train

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.model.accomodation.train.reservation.SegmentsItemReservationEntity
import opsigo.com.datalayer.model.cart.Contact

@Generated("com.robohorse.robopojogenerator")
data class TripTrainsItem(

		@field:SerializedName("Origin")
		var origin: String = "",

		@field:SerializedName("BookingDate")
		var bookingDate: String = "",

		@field:SerializedName("TripPlanId")
		var tripPlanId: String = "",

		@field:SerializedName("IsRefundable")
		var isRefundable: Boolean = false,

		@field:SerializedName("Segments")
		var segments: List<SegmentsItemReservationEntity> = ArrayList(),

		@field:SerializedName("Payments")
		var payments: List<PaymentsItem> = ArrayList(),

		@field:SerializedName("OriginView")
		var originView: String = "",

		@field:SerializedName("ActionDate")
		var actionDate: String = "",

		@field:SerializedName("IsPostedHipchat")
		var isPostedHipchat: Boolean = false,

		@field:SerializedName("Remarks")
		var remarks: List<Any?>? = null,

		@field:SerializedName("TicketNumber")
		var ticketNumber: String = "",

		@field:SerializedName("TimeLimitString")
		var timeLimitString: String = "",

		@field:SerializedName("FlightType")
		var flightType: Int = 0,

		@field:SerializedName("PrgText")
		var prgText: String = "",

		@field:SerializedName("Child")
		var child: Int = 0,

		@field:SerializedName("PaymentStatus")
		var paymentStatus: String = "",

		@field:SerializedName("PaymentSuccess")
		var paymentSuccess: Boolean = false,

		@field:SerializedName("FlightTypeView")
		var flightTypeView: String = "",

		@field:SerializedName("Status")
		var status: String = "",

		@field:SerializedName("PaymentReff")
		var paymentReff: String = "",

		@field:SerializedName("PaymentTimeLimit")
		var paymentTimeLimit: String = "",

		@field:SerializedName("IsEmailSent")
		var isEmailSent: Boolean = false,

		@field:SerializedName("TicketedDate")
		var ticketedDate: String = "",

		@field:SerializedName("PoNumber")
		var poNumber: String = "",

		@field:SerializedName("TicketDocName")
		var ticketDocName: String = "",

		@field:SerializedName("BookingDateView")
		var bookingDateView: String = "",

		@field:SerializedName("HasCostCenterUpdated")
		var hasCostCenterUpdated: Boolean = false,

		@field:SerializedName("PnrCode")
		var pnrCode: String = "",

		@field:SerializedName("FlightDurationPerNum")
		var flightDurationPerNum: String = "",

		@field:SerializedName("TimeLimitView")
		var timeLimitView: String = "",

		@field:SerializedName("Passengers")
		var passengers: List<PassengersItem> = ArrayList(),
//		var passengers: List<opsigo.com.datalayer.model.accomodation.train.reservation.PassengersItem?>? = null,

		@field:SerializedName("ApprovedBy")
		var approvedBy: String = "",

		@field:SerializedName("DestinationView")
		var destinationView: String = "",

		@field:SerializedName("TravelAgent")
		var travelAgent: String = "",

		@field:SerializedName("Id")
		var idTrain: String = "",

		@field:SerializedName("PrgNum")
		var prgNum: Double = 0.0,

		@field:SerializedName("ReferenceCode")
		var referenceCode: String = "",

		@field:SerializedName("JobProgress")
		var jobProgress: JobProgress,

		@field:SerializedName("Destination")
		var destination: String = "",

		@field:SerializedName("IsRemoved")
		var isRemoved: Boolean = false,

		@field:SerializedName("AmountBeforeReserved")
		var amountBeforeReserved: Double = 0.0,

		@field:SerializedName("OpsigoLastSync")
		var opsigoLastSync: String = "",

		@field:SerializedName("TrainName")
		var trainName: String = "",

		@field:SerializedName("DiffAmountInfo")
		var diffAmountInfo: String = "",

		@field:SerializedName("Infant")
		var infant: Int = 0,

		@field:SerializedName("PnrId")
		var pnrId: String = "",

		@field:SerializedName("LinkReference")
		var linkReference: String = "",

		@field:SerializedName("ActionDateView")
		var actionDateView: String = "",

		@field:SerializedName("TicketedDateView")
		var ticketedDateView: String = "",

		@field:SerializedName("Adult")
		var adult: Int = 0,

		@field:SerializedName("PrgJobType")
		var prgJobType: String = "",

		@field:SerializedName("RemarkReject")
		var remarkReject: String = "",

		@field:SerializedName("TimeLimit")
		var timeLimit: String = "",

		@field:SerializedName("Amount")
		var amount: Double = 0.0,

		@field:SerializedName("Contact")
		var contact: Contact = Contact(),

		@field:SerializedName("TicketDocUrl")
		var ticketDocUrl: String = "",

		@field:SerializedName("TripItemId")
		var tripItemId: String = ""

)