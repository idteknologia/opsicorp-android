package opsigo.com.datalayer.model.cart

import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.model.accomodation.train.reservation.SegmentsItemReservationEntity

data class SummaryEntity(

		@field:SerializedName("Origin")
	val origin: String? = null,

		@field:SerializedName("CreationDate")
	val creationDate: String? = null,

		@field:SerializedName("IsShowPolicy")
	val isShowPolicy: Boolean = false,

		@field:SerializedName("TimeLimitRemaining")
	val timeLimitRemaining: String? = null,

		@field:SerializedName("TotalAllowance")
	val totalAllowance: Int = 0,

		@field:SerializedName("TotalExpenditureAirline")
	val totalExpenditureAirline: Double = 0.0,

		@field:SerializedName("TripMembers")
	val tripMembers: List<Any?>? = null,

		@field:SerializedName("Remark")
	val remark: String? = null,

		@field:SerializedName("BudgetSourceName")
	val budgetSourceName: Any? = null,

		@field:SerializedName("LastModified")
	val lastModified: String? = null,

		@field:SerializedName("SeqC4")
	val seqC4: Int = 0,

		@field:SerializedName("Currency")
	val currency: String? = null,

		@field:SerializedName("TotalPaid")
	val totalPaid: Int = 0,

		@field:SerializedName("BudgetView")
	val budgetView: Any? = null,

		@field:SerializedName("DestinationName")
	val destinationName: String? = null,

		@field:SerializedName("TotalBudget")
	val totalBudget: Int = 0,

		@field:SerializedName("Status")
	val status: Int = 0,

		@field:SerializedName("TripAirlines")
	val tripAirlines: List<Any?>? = null,

		@field:SerializedName("TripParticipants")
	val tripParticipants: List<TripParticipantsItem?>? = null,

		@field:SerializedName("CreatedBy")
	val createdBy: String? = null,

		@field:SerializedName("CreationDateRelativeView")
	val creationDateRelativeView: String? = null,

		@field:SerializedName("Routes")
	val routes: ArrayList<RoutesItem> = ArrayList(),

		@field:SerializedName("ReffIdHotel")
	val reffIdHotel: Any? = null,

		@field:SerializedName("Code")
	val code: String? = null,

		@field:SerializedName("CreationDateString")
	val creationDateString: String? = null,

		@field:SerializedName("SubmitedDate")
	val submitedDate: Any? = null,

		@field:SerializedName("TripDateView")
	val tripDateView: String? = null,

		@field:SerializedName("ApproveDate")
	val approveDate: Any? = null,

		@field:SerializedName("TripHotels")
	val tripHotels: List<TripHotelsItem?>? = null,

		@field:SerializedName("TripExpenditures")
	val tripExpenditures: Any? = null,

		@field:SerializedName("DestinationView")
	val destinationView: String? = null,

		@field:SerializedName("TimeLimitShorted")
	val timeLimitShorted: String? = null,

		@field:SerializedName("IsMultiTripForm")
	val isMultiTripForm: Boolean = false,

		@field:SerializedName("Id")
	val id: String? = null,

		@field:SerializedName("TripHistories")
	val tripHistories: List<TripHistoriesItem?>? = null,

		@field:SerializedName("BudgetDesc")
	val budgetDesc: Any? = null,

		@field:SerializedName("Trains")
	val trains: List<TrainsItem?>? = null,

		@field:SerializedName("TripClaims")
	val tripClaims: List<Any?>? = null,

		@field:SerializedName("LastModifiedBy")
	val lastModifiedBy: String? = null,

		@field:SerializedName("TripApprovals")
	val tripApprovals: List<Any?>? = null,

		@field:SerializedName("StatusView")
	val statusView: String? = null,

		@field:SerializedName("Golper")
	val golper: Int = 0,

		@field:SerializedName("ExpiredIn")
	val expiredIn: String? = null,

		@field:SerializedName("TripCodeOld")
	val tripCodeOld: Any? = null,

		@field:SerializedName("TimeLimit")
	val timeLimit: String? = null,

		@field:SerializedName("ReturnDate")
	val returnDate: String? = null,

		@field:SerializedName("TripTrains")
	val tripTrains: List<Any?>? = null,

		@field:SerializedName("FlightEstimatedCostView")
	val flightEstimatedCostView: String? = null,

		@field:SerializedName("ReturnDateView")
	val returnDateView: String? = null,

		@field:SerializedName("OriginName")
	val originName: String? = null,

		@field:SerializedName("Type")
	val type: Int = 0,

		@field:SerializedName("PolicyComplianceStatus")
	val policyComplianceStatus: Int = 0,

		@field:SerializedName("PccId")
	val pccId: String? = null,

		@field:SerializedName("HotelEstimatedCost")
	val hotelEstimatedCost: Int = 0,

		@field:SerializedName("FileC4")
	val fileC4: Any? = null,

		@field:SerializedName("TravelAgentAccount")
	val travelAgentAccount: String? = null,

		@field:SerializedName("IsPrivateTrip")
	val isPrivateTrip: Boolean = false,

		@field:SerializedName("PaymentTypeView")
	val paymentTypeView: String? = null,

		@field:SerializedName("Destinations")
	val destinations: List<String?>? = null,

		@field:SerializedName("BusinessTripType")
	val businessTripType: Any? = null,

		@field:SerializedName("SharingCostCenters")
	val sharingCostCenters: List<Any?>? = null,

		@field:SerializedName("IsBookAfterApprove")
	val isBookAfterApprove: Boolean = false,

		@field:SerializedName("BudgetType")
	val budgetType: Any? = null,

		@field:SerializedName("HotelEstimatedCostView")
	val hotelEstimatedCostView: String? = null,

		@field:SerializedName("OriginView")
	val originView: String? = null,

		@field:SerializedName("StartDate")
	val startDate: String? = null,

		@field:SerializedName("IsDomestic")
	val isDomestic: Boolean = false,

		@field:SerializedName("TrainEstimatedCost")
	val trainEstimatedCost: Int = 0,

		@field:SerializedName("IsPersonalTrip")
	val isPersonalTrip: Boolean = false,

		@field:SerializedName("TravelAgentUrl")
	val travelAgentUrl: String? = null,

		@field:SerializedName("TimeLimitString")
	val timeLimitString: String? = null,

		@field:SerializedName("TripBudgetComponents")
	val tripBudgetComponents: Any? = null,

		@field:SerializedName("TrnNumber")
	val trnNumber: Any? = null,

		@field:SerializedName("TotalExpenditureHotel")
	val totalExpenditureHotel: Int = 0,

		@field:SerializedName("DurationDay")
	val durationDay: Int = 0,

		@field:SerializedName("OriRoutes")
	val oriRoutes: Any? = null,

		@field:SerializedName("IsWithoutSPJ")
	val isWithoutSPJ: Boolean = false,

		@field:SerializedName("BudgetId")
	val budgetId: String? = null,

		@field:SerializedName("EstimatedCost")
	val estimatedCost: Int = 0,

		@field:SerializedName("LastGeneratedToC4")
	val lastGeneratedToC4: String? = null,

		@field:SerializedName("OtherPurpose")
	val otherPurpose: Any? = null,

		@field:SerializedName("ReasonCode")
	val reasonCode: Any? = null,

		@field:SerializedName("PostTripClaims")
	val postTripClaims: List<Any?>? = null,

		@field:SerializedName("FlightEstimatedCost")
	val flightEstimatedCost: Int = 0,

		@field:SerializedName("TripAttachments")
	val tripAttachments: ArrayList<TripAttachmentItem>? = null,

		@field:SerializedName("Destination")
	val destination: String? = null,

		@field:SerializedName("CityNameApi")
	val cityNameApi: Any? = null,

		@field:SerializedName("TotalExpenditure")
	val totalExpenditure: Double = 0.0,

		@field:SerializedName("TransportationType")
	val transportationType: Any? = null,

		@field:SerializedName("SubmitDateView")
	val submitDateView: Any? = null,

		@field:SerializedName("PurposeCode")
	val purposeCode: Any? = null,

		@field:SerializedName("isApproval")
	val isApproval: Boolean = false,

		@field:SerializedName("IsHavePayment")
	val isHavePayment: Boolean = false,

		@field:SerializedName("CostCenter")
	val costCenter: Any? = null,

		@field:SerializedName("Flights")
	val flights: List<FlightsItem?>? = null,

		@field:SerializedName("CreationDateView")
	val creationDateView: String? = null,

		@field:SerializedName("RemarkSKPD")
	val remarkSKPD: Any? = null,

		@field:SerializedName("PaymentType")
	val paymentType: Int = 0,

	@field:SerializedName("PaymentStatus")
	val paymentStatus: Int = 0,

	@field:SerializedName("PaymentStatusView")
	val paymentStatusView : String? = null,

	@field:SerializedName("StartDateView")
	val startDateView: String? = null,

		@field:SerializedName("PolicyCause")
	val policyCause: Any? = null,

		@field:SerializedName("TotalExpenditureTrain")
	val totalExpenditureTrain: Int = 0,

		@field:SerializedName("TrainEstimatedCostView")
	val trainEstimatedCostView: String? = null,

		@field:SerializedName("Purpose")
	val purpose: String? = null,

		@field:SerializedName("PolicyReasonCode")
	val policyReasonCode: Int = 0,

		@field:SerializedName("ApprovalCode")
	val approvalCode: Any? = null,

		@field:SerializedName("CostCenterId")
	val costCenterId: Any? = null,

		@field:SerializedName("IsGeneratedToC4")
	val isGeneratedToC4: Boolean = false,

		@field:SerializedName("Contact")
	val contact: Contact? = null,

		@field:SerializedName("DueDateApproval")
	val dueDateApproval: String? = null,

		@field:SerializedName("TripItems")
	val tripItems: List<Any?>? = null,

		@field:SerializedName("WbsNo")
		val wbsNo: String? = null,

		@field:SerializedName("SubmitDateString")
	val submitDateString: Any? = null
)



data class TripAttachmentItem(

		@field:SerializedName("Id")
		val id: String? = null,

		@field:SerializedName("TripPlanId")
		val tripPlanId: String? = null,

		@field:SerializedName("Description")
		val description: String? = null,

		@field:SerializedName("Url")
		val url: String? = null
)

data class RoutesItem(

	@field:SerializedName("Origin")
	val origin: String? = null,

	@field:SerializedName("Destination")
	val destination: String? = null,

	@field:SerializedName("Transportation")
	val transportation: Double = 0.0,

	@field:SerializedName("DepartureDateView")
	val departureDateView: String? = null,

	@field:SerializedName("DepartureDate")
	val departureDate: String? = null,

	@field:SerializedName("ArriveDate")
	val arriveDate: String? = null,

	@field:SerializedName("DepartDate")
	val departDate: String? = null
)

data class TrainsItem(

	@field:SerializedName("Status")
	val status: Int = 0,

	@field:SerializedName("IsSecondaryLevel")
	val isSecondaryLevel: Boolean = false,

	@field:SerializedName("IsRemoved")
	val isRemoved: Boolean = false,

	@field:SerializedName("IsDownloadPnr")
	val isDownloadPnr: Boolean = false,

	@field:SerializedName("TripPlanId")
	val tripPlanId: String? = null,

	@field:SerializedName("IsManual")
	val isManual: Boolean = false,

	@field:SerializedName("TripTrains")
	val tripTrains: List<TripTrainsItem?>? = null,

	@field:SerializedName("TravelAgentAccount")
	val travelAgentAccount: String? = null,

	@field:SerializedName("HasConfirmed")
	val hasConfirmed: Boolean = false,

	@field:SerializedName("ItemType")
	val itemType: Int = 0,

	@field:SerializedName("Amount")
	val amount: Int = 0,

	@field:SerializedName("TripMemberId")
	val tripMemberId: String? = null,

	@field:SerializedName("ReasonCode")
	val reasonCode: String? = null,

	@field:SerializedName("HasCostCenterUpdated")
	val hasCostCenterUpdated: Boolean = false,

	@field:SerializedName("TripHotels")
	val tripHotels: List<Any?>? = null,

	@field:SerializedName("TripFlights")
	val tripFlights: List<Any?>? = null,

	@field:SerializedName("IsViolatedHotelRules")
	val isViolatedHotelRules: Boolean = false,

	@field:SerializedName("FlightType")
	val flightType: Int = 0,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("EmployeeId")
	val employeeId: String? = null,

	@field:SerializedName("EmployeeName")
	val employeeName: String? = null
)

data class HotelBookingCodesItem(

	@field:SerializedName("BookingCode")
	val bookingCode: Any? = null,

	@field:SerializedName("BookingStatus")
	val bookingStatus: String? = null
)

data class FlightBookingCodesItem(

	@field:SerializedName("BookingCode")
	val bookingCode: String? = null,

	@field:SerializedName("BookingStatus")
	val bookingStatus: String? = null
)

data class TripItemTypesItem(

	@field:SerializedName("Type")
	val type: Int = 0,

	@field:SerializedName("TripItems")
	val tripItems: List<TripItemsItem?>? = null,

	@field:SerializedName("Items")
	val items: List<Any?>? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("Name")
	val name: String? = null
)

data class Hotel(

	@field:SerializedName("RemarkHotel")
	val remarkHotel: String? = null,

	@field:SerializedName("Address")
	val address: String? = null,

	@field:SerializedName("IsSync")
	val isSync: Boolean = false,

	@field:SerializedName("Payments")
	val payments: List<PaymentsItem?>? = null,

	@field:SerializedName("Hash")
	val hash: Any? = null,

	@field:SerializedName("Image")
	val image: String? = null,

	@field:SerializedName("CancellationPolicies")
	val cancellationPolicies: List<Any?>? = null,

	@field:SerializedName("ActionDate")
	val actionDate: String? = null,

	@field:SerializedName("IsFullCharge")
	val isFullCharge: Boolean = false,

	@field:SerializedName("Checkout")
	val checkout: String? = null,

	@field:SerializedName("RoomSelector")
	val roomSelector: String? = null,

	@field:SerializedName("Currency")
	val currency: String? = null,

	@field:SerializedName("DestinationName")
	val destinationName: Any? = null,

	@field:SerializedName("RoomService")
	val roomService: String? = null,

	@field:SerializedName("CauseViolatedRules")
	val causeViolatedRules: String? = null,

	@field:SerializedName("CorrelationId")
	val correlationId: String? = null,

	@field:SerializedName("EmpId")
	val empId: Any? = null,

	@field:SerializedName("Status")
	val status: Int = 0,

	@field:SerializedName("CheckinFormatString")
	val checkinFormatString: String? = null,

	@field:SerializedName("IsEmailSent")
	val isEmailSent: Boolean = false,

	@field:SerializedName("TicketedDate")
	val ticketedDate: Any? = null,

	@field:SerializedName("StatusName")
	val statusName: String? = null,

	@field:SerializedName("IsRefund")
	val isRefund: Boolean = false,

	@field:SerializedName("DiscountAmount")
	val discountAmount: Int = 0,

	@field:SerializedName("CheckinView")
	val checkinView: String? = null,

	@field:SerializedName("TripCode")
	val tripCode: Any? = null,

	@field:SerializedName("TimeLimitView")
	val timeLimitView: String? = null,

	@field:SerializedName("IsViolatedHotelRules")
	val isViolatedHotelRules: Boolean = false,

	@field:SerializedName("Country")
	val country: String? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("BookingNumber")
	val bookingNumber: Any? = null,

	@field:SerializedName("BookingCode")
	val bookingCode: Any? = null,

	@field:SerializedName("BookedDate")
	val bookedDate: String? = null,

	@field:SerializedName("TourismTax")
	val tourismTax: Int = 0,

	@field:SerializedName("HotelType")
	val hotelType: Int = 0,

	@field:SerializedName("CostId")
	val costId: Any? = null,

	@field:SerializedName("RoomType")
	val roomType: String? = null,

	@field:SerializedName("LinkReference")
	val linkReference: Any? = null,

	@field:SerializedName("CancellationApi")
	val cancellationApi: String? = null,

	@field:SerializedName("TimeLimit")
	val timeLimit: String? = null,

	@field:SerializedName("MapUri")
	val mapUri: String? = null,

	@field:SerializedName("ReffId")
	val reffId: Any? = null,

	@field:SerializedName("Amount")
	val amount: Int = 0,

	@field:SerializedName("TotalAmountApi")
	val totalAmountApi: Int = 0,

	@field:SerializedName("Facilities")
	val facilities: Any? = null,

	@field:SerializedName("Rooms")
	val rooms: Int = 0,

	@field:SerializedName("AmountWithSf")
	val amountWithSf: Int = 0,

	@field:SerializedName("CauseViolatedRulesParagraf")
	val causeViolatedRulesParagraf: String? = null,

	@field:SerializedName("TimeLimitTripPlan")
	val timeLimitTripPlan: Any? = null,

	@field:SerializedName("DiscountPercentage")
	val discountPercentage: Int = 0,

	@field:SerializedName("Checkin")
	val checkin: String? = null,

	@field:SerializedName("TripPlanId")
	val tripPlanId: String? = null,

	@field:SerializedName("Email")
	val email: String? = null,

	@field:SerializedName("IsForwardedBooking")
	val isForwardedBooking: Boolean = false,

	@field:SerializedName("FormBookingUrl")
	val formBookingUrl: String? = null,

	@field:SerializedName("CancelReff")
	val cancelReff: Any? = null,

	@field:SerializedName("Name")
	val name: String? = null,

	@field:SerializedName("Remarks")
	val remarks: List<RemarksItem?>? = null,

	@field:SerializedName("ContractNo")
	val contractNo: Any? = null,

	@field:SerializedName("BookingId")
	val bookingId: Any? = null,

	@field:SerializedName("TimeLimitString")
	val timeLimitString: String? = null,

	@field:SerializedName("ResponseBookUrl")
	val responseBookUrl: Any? = null,

	@field:SerializedName("PaymentStatus")
	val paymentStatus: Any? = null,

	@field:SerializedName("PaymentSuccess")
	val paymentSuccess: Boolean = false,

	@field:SerializedName("IsSentEmail")
	val isSentEmail: Boolean = false,

	@field:SerializedName("PaymentReff")
	val paymentReff: Any? = null,

	@field:SerializedName("ConfirmationId")
	val confirmationId: String? = null,

	@field:SerializedName("CitiNameApi")
	val citiNameApi: String? = null,

	@field:SerializedName("Guest")
	val guest: List<GuestItem?>? = null,

	@field:SerializedName("ConfirmationUrl")
	val confirmationUrl: String? = null,

	@field:SerializedName("Duration")
	val duration: Int = 0,

	@field:SerializedName("City")
	val city: Any? = null,

	@field:SerializedName("CityName")
	val cityName: Any? = null,

	@field:SerializedName("EmailFD")
	val emailFD: Any? = null,

	@field:SerializedName("ServiceFee")
	val serviceFee: Int = 0,

	@field:SerializedName("Area")
	val area: String? = null,

	@field:SerializedName("IsFullDeduct")
	val isFullDeduct: Boolean = false,

	@field:SerializedName("DiscountType")
	val discountType: Int = 0,

	@field:SerializedName("PlafondAmount")
	val plafondAmount: Int = 0,

	@field:SerializedName("TravelAgent")
	val travelAgent: Any? = null,

	@field:SerializedName("CreatedDate")
	val createdDate: String? = null,

	@field:SerializedName("Message")
	val message: Any? = null,

	@field:SerializedName("Rating")
	val rating: Int = 0,

	@field:SerializedName("IsTourism")
	val isTourism: Boolean = false,

	@field:SerializedName("CancellationPoliciesView")
	val cancellationPoliciesView: List<String?>? = null,

	@field:SerializedName("CheckoutView")
	val checkoutView: String? = null,

	@field:SerializedName("EmailFO")
	val emailFO: Any? = null,

	@field:SerializedName("PnrId")
	val pnrId: Any? = null,

	@field:SerializedName("Phone")
	val phone: String? = null,

	@field:SerializedName("IsGuarantedBooking")
	val isGuarantedBooking: Boolean = false,

	@field:SerializedName("RemarkReject")
	val remarkReject: Any? = null,

	@field:SerializedName("IsHsre")
	val isHsre: Boolean = false,

	@field:SerializedName("CheckOutFormatString")
	val checkOutFormatString: String? = null,

	@field:SerializedName("IsReschedule")
	val isReschedule: Boolean = false,

	@field:SerializedName("TotalAmount")
	val totalAmount: Int = 0,

	@field:SerializedName("IsWithBreakfast")
	val isWithBreakfast: Boolean = false,

	@field:SerializedName("BookingCodeNew")
	val bookingCodeNew: Any? = null,

	@field:SerializedName("Contact")
	val contact: Contact? = null,

	@field:SerializedName("HotelKey")
	val hotelKey: String? = null,

	@field:SerializedName("RoomCategory")
	val roomCategory: String? = null,

	@field:SerializedName("TimeLimitFormatString")
	val timeLimitFormatString: String? = null,

	@field:SerializedName("TripItemId")
	val tripItemId: String? = null,

	@field:SerializedName("AmountPerNight")
	val amountPerNight: Int = 0
)

data class TripItemsItem(

	@field:SerializedName("Status")
	val status: Int = 0,

	@field:SerializedName("IsSecondaryLevel")
	val isSecondaryLevel: Boolean = false,

	@field:SerializedName("IsRemoved")
	val isRemoved: Boolean = false,

	@field:SerializedName("IsDownloadPnr")
	val isDownloadPnr: Boolean = false,

	@field:SerializedName("TripPlanId")
	val tripPlanId: String? = null,

	@field:SerializedName("IsManual")
	val isManual: Boolean = false,

	@field:SerializedName("TripTrains")
	val tripTrains: List<TripTrainsItem?>? = null,

	@field:SerializedName("TravelAgentAccount")
	val travelAgentAccount: String? = null,

	@field:SerializedName("HasConfirmed")
	val hasConfirmed: Boolean = false,

	@field:SerializedName("ItemType")
	val itemType: Int = 0,

	@field:SerializedName("Amount")
	val amount: Double = 0.0,

	@field:SerializedName("TripMemberId")
	val tripMemberId: String? = null,

	@field:SerializedName("ReasonCode")
	val reasonCode: String? = null,

	@field:SerializedName("HasCostCenterUpdated")
	val hasCostCenterUpdated: Boolean = false,

	@field:SerializedName("TripHotels")
	val tripHotels: List<TripHotelsItems?>? = null,

	@field:SerializedName("TripFlights")
	val tripFlights: List<TripFlightsItem>? = null,

	@field:SerializedName("IsViolatedHotelRules")
	val isViolatedHotelRules: Boolean = false,

	@field:SerializedName("FlightType")
	val flightType: Int = 0,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("EmployeeId")
	val employeeId: String? = null,

	@field:SerializedName("EmployeeName")
	val employeeName: String? = null
)


data class PaymentsItem(

		@field:SerializedName("Amount")
		val amount: Double = 0.0,

		@field:SerializedName("Currency")
		val currency: String? = null,

		@field:SerializedName("Title")
		val title: String? = null,

		@field:SerializedName("Id")
		val id: String? = null,

		@field:SerializedName("Code")
		val code: String? = null,

		@field:SerializedName("TripFlightId")
		val tripFlightId: String? = null,

		@field:SerializedName("Seq")
		val seq: Int = 0
)

data class Passport(

		@field:SerializedName("Expire")
		val expire: Any? = null,

		@field:SerializedName("PassengerId")
		val passengerId: String? = null,

		@field:SerializedName("Number")
		val number: Any? = null,

		@field:SerializedName("FirstName")
		val firstName: Any? = null,

		@field:SerializedName("OriginCountry")
		val originCountry: Any? = null,

		@field:SerializedName("Id")
		val id: String? = null,

		@field:SerializedName("LastName")
		val lastName: Any? = null
)


data class ContactHotel(

		@field:SerializedName("Company")
		val company: Any? = null,

		@field:SerializedName("Email")
		val email: String? = null,

		@field:SerializedName("JobTitleName")
		val jobTitleName: Any? = null,

		@field:SerializedName("FirstName")
		val firstName: String? = null,

		@field:SerializedName("Title")
		val title: String? = null,

		@field:SerializedName("ParentCompanyName")
		val parentCompanyName: Any? = null,

		@field:SerializedName("JobTitle")
		val jobTitle: Any? = null,

		@field:SerializedName("MobilePhone")
		val mobilePhone: String? = null,

		@field:SerializedName("Nationality")
		val nationality: Any? = null,

		@field:SerializedName("JobTitleId")
		val jobTitleId: String? = null,

		@field:SerializedName("ParentCompany")
		val parentCompany: Any? = null,

		@field:SerializedName("CompanyName")
		val companyName: Any? = null,

		@field:SerializedName("HomePhone")
		val homePhone: Any? = null,

		@field:SerializedName("TripHotelId")
		val tripHotelId: String? = null,

		@field:SerializedName("Id")
		val id: String? = null,

		@field:SerializedName("LastName")
		val lastName: String? = null,

		@field:SerializedName("NikEmployee")
		val nikEmployee: Any? = null,

		@field:SerializedName("TripFlightId")
		val tripFlightId: String? = null,

		@field:SerializedName("CompanyCode")
		val companyCode: Any? = null,

		@field:SerializedName("BirthDate")
		val birthDate: Any? = null
)

data class Contact(

		@field:SerializedName("TripPlanId")
		val tripPlanId: String? = null,

		@field:SerializedName("Company")
		val company: Any? = null,

		@field:SerializedName("Email")
		val email: String? = null,

		@field:SerializedName("Address")
		val address: Any? = null,

		@field:SerializedName("PostalCode")
		val postalCode: Any? = null,

		@field:SerializedName("ParentCompanyName")
		val parentCompanyName: Any? = null,

		@field:SerializedName("JobTitle")
		val jobTitle: Any? = null,

		@field:SerializedName("MobilePhone")
		val mobilePhone: String? = null,

		@field:SerializedName("CompanyName")
		val companyName: Any? = null,

		@field:SerializedName("JobTitleId")
		val jobTitleId: String? = null,

		@field:SerializedName("NikEmployee")
		val nikEmployee: Any? = null,

		@field:SerializedName("EmployeeId")
		val employeeId: String? = null,

		@field:SerializedName("JobTitleName")
		val jobTitleName: Any? = null,

		@field:SerializedName("FirstName")
		val firstName: String? = null,

		@field:SerializedName("Title")
		val title: String? = null,

		@field:SerializedName("City")
		val city: Any? = null,

		@field:SerializedName("Nationality")
		val nationality: Any? = null,

		@field:SerializedName("ProvinceState")
		val provinceState: Any? = null,

		@field:SerializedName("ParentCompany")
		val parentCompany: Any? = null,

		@field:SerializedName("HomePhone")
		val homePhone: String? = null,

		@field:SerializedName("FullName")
		val fullName: String? = null,

		@field:SerializedName("Id")
		val id: String? = null,

		@field:SerializedName("LastName")
		val lastName: String? = null,

		@field:SerializedName("CompanyCode")
		val companyCode: Any? = null,

		@field:SerializedName("BirthDate")
		val birthDate: Any? = null,

		@field:SerializedName("TripHotelId")
		val tripHotelId: String? = null,

		@field:SerializedName("TripFlightId")
		val tripFlightId: String? = null
)

data class GuestItem(

		@field:SerializedName("OtherPhone")
		val otherPhone: Any? = null,

		@field:SerializedName("Email")
		val email: Any? = null,

		@field:SerializedName("FirstName")
		val firstName: String? = null,

		@field:SerializedName("IdNumber")
		val idNumber: String? = null,

		@field:SerializedName("TicketNumberNew")
		val ticketNumberNew: Any? = null,

		@field:SerializedName("AssignedRoom")
		val assignedRoom: Any? = null,

		@field:SerializedName("OrderInRoom")
		val orderInRoom: Any? = null,

		@field:SerializedName("Title")
		val title: String? = null,

		@field:SerializedName("Gender")
		val gender: Any? = null,

		@field:SerializedName("MobilePhone")
		val mobilePhone: String? = null,

		@field:SerializedName("Nationality")
		val nationality: String? = null,

		@field:SerializedName("Type")
		val type: String? = null,

		@field:SerializedName("Passport")
		val passport: Passport? = null,

		@field:SerializedName("HomePhone")
		val homePhone: String? = null,

		@field:SerializedName("TicketNumber")
		val ticketNumber: Any? = null,

		@field:SerializedName("Remarks")
		val remarks: List<Any?>? = null,

		@field:SerializedName("Id")
		val id: String? = null,

		@field:SerializedName("LastName")
		val lastName: String? = null,

		@field:SerializedName("Seq")
		val seq: Int = 0,

		@field:SerializedName("BirthDate")
		val birthDate: String? = null
)


data class RemarksItemsHotel(

		@field:SerializedName("TripPlanId")
		val tripPlanId: String? = null,

		@field:SerializedName("TripTrainId")
		val tripTrainId: String? = null,

		@field:SerializedName("Created")
		val created: String? = null,

		@field:SerializedName("PnrId")
		val pnrId: String? = null,

		@field:SerializedName("Username")
		val username: String? = null,

		@field:SerializedName("TripTrainPassengerId")
		val tripTrainPassengerId: String? = null,

		@field:SerializedName("TripItemId")
		val tripItemId: String? = null,

		@field:SerializedName("TripHotelId")
		val tripHotelId: String? = null,

		@field:SerializedName("Id")
		val id: String? = null,

		@field:SerializedName("EmployeeId")
		val employeeId: String? = null,

		@field:SerializedName("TripFlightPassengerId")
		val tripFlightPassengerId: String? = null,

		@field:SerializedName("Body")
		val body: String? = null,

		@field:SerializedName("TripFlightId")
		val tripFlightId: String? = null,

		@field:SerializedName("Seq")
		val seq: Int? = null
)

data class TripHotelsItems(

		@field:SerializedName("RemarkHotel")
		val remarkHotel: String? = null,

		@field:SerializedName("Address")
		val address: String? = null,

		@field:SerializedName("IsSync")
		val isSync: Boolean = false,

		@field:SerializedName("Payments")
		val payments: List<PaymentsItem?>? = null,

		@field:SerializedName("Hash")
		val hash: Any? = null,

		@field:SerializedName("Image")
		val image: String? = null,

		@field:SerializedName("CancellationPolicies")
		val cancellationPolicies: List<Any?>? = null,

		@field:SerializedName("ActionDate")
		val actionDate: String? = null,

		@field:SerializedName("IsFullCharge")
		val isFullCharge: Boolean = false,

		@field:SerializedName("Checkout")
		val checkout: String? = null,

		@field:SerializedName("RoomSelector")
		val roomSelector: String? = null,

		@field:SerializedName("Currency")
		val currency: String? = null,

		@field:SerializedName("DestinationName")
		val destinationName: Any? = null,

		@field:SerializedName("RoomService")
		val roomService: String? = null,

		@field:SerializedName("CauseViolatedRules")
		val causeViolatedRules: String? = null,

		@field:SerializedName("CorrelationId")
		val correlationId: String? = null,

		@field:SerializedName("EmpId")
		val empId: Any? = null,

		@field:SerializedName("Status")
		val status: Int? = null,

		@field:SerializedName("CheckinFormatString")
		val checkinFormatString: String? = null,

		@field:SerializedName("IsEmailSent")
		val isEmailSent: Boolean = false,

		@field:SerializedName("TicketedDate")
		val ticketedDate: Any? = null,

		@field:SerializedName("StatusName")
		val statusName: String? = null,

		@field:SerializedName("IsRefund")
		val isRefund: Boolean = false,

		@field:SerializedName("DiscountAmount")
		val discountAmount: Int? = null,

		@field:SerializedName("CheckinView")
		val checkinView: String? = null,

		@field:SerializedName("TripCode")
		val tripCode: Any? = null,

		@field:SerializedName("TimeLimitView")
		val timeLimitView: String? = null,

		@field:SerializedName("IsViolatedHotelRules")
		val isViolatedHotelRules: Boolean = false,

		@field:SerializedName("Country")
		val country: String? = null,

		@field:SerializedName("Id")
		val id: String? = null,

		@field:SerializedName("BookingNumber")
		val bookingNumber: Any? = null,

		@field:SerializedName("BookingCode")
		val bookingCode: String? = null,

		@field:SerializedName("BookedDate")
		val bookedDate: String? = null,

		@field:SerializedName("TourismTax")
		val tourismTax: Int? = null,

		@field:SerializedName("HotelType")
		val hotelType: Int? = null,

		@field:SerializedName("CostId")
		val costId: Any? = null,

		@field:SerializedName("RoomType")
		val roomType: String? = null,

		@field:SerializedName("LinkReference")
		val linkReference: Any? = null,

		@field:SerializedName("CancellationApi")
		val cancellationApi: String? = null,

		@field:SerializedName("TimeLimit")
		val timeLimit: String? = null,

		@field:SerializedName("MapUri")
		val mapUri: String? = null,

		@field:SerializedName("ReffId")
		val reffId: Any? = null,

		@field:SerializedName("Amount")
		val amount: Int? = null,

		@field:SerializedName("TotalAmountApi")
		val totalAmountApi: Int? = null,

		@field:SerializedName("Facilities")
		val facilities: Any? = null,

		@field:SerializedName("Rooms")
		val rooms: Int? = null,

		@field:SerializedName("AmountWithSf")
		val amountWithSf: Int? = null,

		@field:SerializedName("CauseViolatedRulesParagraf")
		val causeViolatedRulesParagraf: String? = null,

		@field:SerializedName("TimeLimitTripPlan")
		val timeLimitTripPlan: Any? = null,

		@field:SerializedName("DiscountPercentage")
		val discountPercentage: Int? = null,

		@field:SerializedName("Checkin")
		val checkin: String? = null,

		@field:SerializedName("TripPlanId")
		val tripPlanId: String? = null,

		@field:SerializedName("Email")
		val email: String? = null,

		@field:SerializedName("IsForwardedBooking")
		val isForwardedBooking: Boolean = false,

		@field:SerializedName("FormBookingUrl")
		val formBookingUrl: String? = null,

		@field:SerializedName("CancelReff")
		val cancelReff: Any? = null,

		@field:SerializedName("Name")
		val name: String? = null,

		@field:SerializedName("Remarks")
		val remarks: List<RemarksItemsHotel?>? = null,

		@field:SerializedName("ContractNo")
		val contractNo: Any? = null,

		@field:SerializedName("BookingId")
		val bookingId: Any? = null,

		@field:SerializedName("TimeLimitString")
		val timeLimitString: String? = null,

		@field:SerializedName("ResponseBookUrl")
		val responseBookUrl: Any? = null,

		@field:SerializedName("PaymentStatus")
		val paymentStatus: Any? = null,

		@field:SerializedName("PaymentSuccess")
		val paymentSuccess: Boolean = false,

		@field:SerializedName("IsSentEmail")
		val isSentEmail: Boolean = false,

		@field:SerializedName("PaymentReff")
		val paymentReff: Any? = null,

		@field:SerializedName("ConfirmationId")
		val confirmationId: String? = null,

		@field:SerializedName("CitiNameApi")
		val citiNameApi: String? = null,

		@field:SerializedName("Guest")
		val guest: List<GuestItem?>? = null,

		@field:SerializedName("ConfirmationUrl")
		val confirmationUrl: String? = null,

		@field:SerializedName("Duration")
		val duration: Int? = null,

		@field:SerializedName("City")
		val city: Any? = null,

		@field:SerializedName("CityName")
		val cityName: Any? = null,

		@field:SerializedName("EmailFD")
		val emailFD: Any? = null,

		@field:SerializedName("ServiceFee")
		val serviceFee: Int? = null,

		@field:SerializedName("Area")
		val area: String? = null,

		@field:SerializedName("IsFullDeduct")
		val isFullDeduct: Boolean = false,

		@field:SerializedName("DiscountType")
		val discountType: Int? = null,

		@field:SerializedName("PlafondAmount")
		val plafondAmount: Int? = null,

		@field:SerializedName("TravelAgent")
		val travelAgent: Any? = null,

		@field:SerializedName("CreatedDate")
		val createdDate: String? = null,

		@field:SerializedName("Message")
		val message: Any? = null,

		@field:SerializedName("Rating")
		val rating: Int? = null,

		@field:SerializedName("IsTourism")
		val isTourism: Boolean = false,

		@field:SerializedName("CancellationPoliciesView")
		val cancellationPoliciesView: List<String?>? = null,

		@field:SerializedName("CheckoutView")
		val checkoutView: String? = null,

		@field:SerializedName("EmailFO")
		val emailFO: Any? = null,

		@field:SerializedName("PnrId")
		val pnrId: Any? = null,

		@field:SerializedName("Phone")
		val phone: String? = null,

		@field:SerializedName("IsGuarantedBooking")
		val isGuarantedBooking: Boolean = false,

		@field:SerializedName("RemarkReject")
		val remarkReject: Any? = null,

		@field:SerializedName("IsHsre")
		val isHsre: Boolean = false,

		@field:SerializedName("CheckOutFormatString")
		val checkOutFormatString: String? = null,

		@field:SerializedName("IsReschedule")
		val isReschedule: Boolean = false,

		@field:SerializedName("TotalAmount")
		val totalAmount: Int? = null,

		@field:SerializedName("IsWithBreakfast")
		val isWithBreakfast: Boolean = false,

		@field:SerializedName("BookingCodeNew")
		val bookingCodeNew: Any? = null,

		@field:SerializedName("Contact")
		val contact: ContactHotel? = null,

		@field:SerializedName("HotelKey")
		val hotelKey: String? = null,

		@field:SerializedName("RoomCategory")
		val roomCategory: String? = null,

		@field:SerializedName("TimeLimitFormatString")
		val timeLimitFormatString: String? = null,

		@field:SerializedName("TripItemId")
		val tripItemId: String? = null,

		@field:SerializedName("AmountPerNight")
		val amountPerNight: Int? = null
)

data class RemarksItem(

	@field:SerializedName("TripPlanId")
	val tripPlanId: String? = null,

	@field:SerializedName("TripTrainId")
	val tripTrainId: String? = null,

	@field:SerializedName("Created")
	val created: String? = null,

	@field:SerializedName("PnrId")
	val pnrId: String? = null,

	@field:SerializedName("Username")
	val username: String? = null,

	@field:SerializedName("TripTrainPassengerId")
	val tripTrainPassengerId: String? = null,

	@field:SerializedName("TripItemId")
	val tripItemId: String? = null,

	@field:SerializedName("TripHotelId")
	val tripHotelId: String? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("EmployeeId")
	val employeeId: String? = null,

	@field:SerializedName("TripFlightPassengerId")
	val tripFlightPassengerId: String? = null,

	@field:SerializedName("Body")
	val body: String? = null,

	@field:SerializedName("TripFlightId")
	val tripFlightId: String? = null,

	@field:SerializedName("Seq")
	val seq: Int = 0
)

data class TripHistoriesItem(

	@field:SerializedName("TripPlanId")
	val tripPlanId: String? = null,

	@field:SerializedName("Action")
	val action: String? = null,

	@field:SerializedName("Company")
	val company: Any? = null,

	@field:SerializedName("Description")
	val description: String? = null,

	@field:SerializedName("Username")
	val username: String? = null,

	@field:SerializedName("CreatedDate")
	val createdDate: String? = null,

	@field:SerializedName("Division")
	val division: Any? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("JobTitle")
	val jobTitle: Any? = null,

	@field:SerializedName("ReasonCode")
	val reasonCode: Any? = null
)

data class TripHotelsItem(

	@field:SerializedName("Origin")
	val origin: Any? = null,

	@field:SerializedName("BookingDate")
	val bookingDate: String? = null,

	@field:SerializedName("IsDownloadPnr")
	val isDownloadPnr: Boolean = false,

	@field:SerializedName("IsRefundable")
	val isRefundable: Boolean = false,

	@field:SerializedName("Airline")
	val airline: Int = 0,

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

	@field:SerializedName("NotComplyDetails")
	val notComplyDetails: List<Any?>? = null,

	@field:SerializedName("FlightType")
	val flightType: Int = 0,

	@field:SerializedName("UseWizardSeatSelection")
	val useWizardSeatSelection: Boolean = false,

	@field:SerializedName("CallbackUri")
	val callbackUri: Any? = null,

	@field:SerializedName("Child")
	val child: Int = 0,

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("FlightTripType")
	val flightTripType: Int = 0,

	@field:SerializedName("IsManual")
	val isManual: Boolean = false,

	@field:SerializedName("IsWithoutSpj")
	val isWithoutSpj: Boolean = false,

	@field:SerializedName("TicketedDate")
	val ticketedDate: Any? = null,

	@field:SerializedName("OpsigoPassengers")
	val opsigoPassengers: List<Any?>? = null,

	@field:SerializedName("BookingDateView")
	val bookingDateView: Any? = null,

	@field:SerializedName("Code")
	val code: Any? = null,

	@field:SerializedName("ReasonCode")
	val reasonCode: String? = null,

	@field:SerializedName("IsUpdatePassport")
	val isUpdatePassport: Boolean = false,

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
	val isRemoved: Boolean = false,

	@field:SerializedName("TrainName")
	val trainName: Any? = null,

	@field:SerializedName("Infant")
	val infant: Int = 0,

	@field:SerializedName("Created")
	val created: String? = null,

	@field:SerializedName("PaymentType")
	val paymentType: Int = 0,

	@field:SerializedName("BookingProgress")
	val bookingProgress: List<Any?>? = null,

	@field:SerializedName("Adult")
	val adult: Int = 0,

	@field:SerializedName("Supplier")
	val supplier: Int = 0,

	@field:SerializedName("EmployeeId")
	val employeeId: String? = null,

	@field:SerializedName("TimeLimit")
	val timeLimit: String? = null,

	@field:SerializedName("ListConflict")
	val listConflict: List<Any?>? = null,

	@field:SerializedName("Amount")
	val amount: Int = 0,

	@field:SerializedName("Purpose")
	val purpose: Any? = null,

	@field:SerializedName("TotalAmount")
	val totalAmount: Int = 0,

	@field:SerializedName("ApprovalCode")
	val approvalCode: Any? = null,

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
	val isViolatedTrainRules: Boolean = false
)

data class TripPlanItemsItem(

	@field:SerializedName("Status")
	val status: Int = 0,

	@field:SerializedName("IsSecondaryLevel")
	val isSecondaryLevel: Boolean = false,

	@field:SerializedName("IsRemoved")
	val isRemoved: Boolean = false,

	@field:SerializedName("IsDownloadPnr")
	val isDownloadPnr: Boolean = false,

	@field:SerializedName("TripPlanId")
	val tripPlanId: String? = null,

	@field:SerializedName("IsManual")
	val isManual: Boolean = false,

	@field:SerializedName("TripTrains")
	val tripTrains: List<Any?>? = null,

	@field:SerializedName("TravelAgentAccount")
	val travelAgentAccount: String? = null,

	@field:SerializedName("HasConfirmed")
	val hasConfirmed: Boolean = false,

	@field:SerializedName("ItemType")
	val itemType: Int = 0,

	@field:SerializedName("Amount")
	val amount: Double = 0.0,

	@field:SerializedName("TripMemberId")
	val tripMemberId: String? = null,

	@field:SerializedName("ReasonCode")
	val reasonCode: String? = null,

	@field:SerializedName("HasCostCenterUpdated")
	val hasCostCenterUpdated: Boolean = false,

	@field:SerializedName("TripHotels")
	val tripHotels: List<TripHotelsItem?>? = null,

	@field:SerializedName("TripFlights")
	val tripFlights: List<Any?>? = null,

	@field:SerializedName("IsViolatedHotelRules")
	val isViolatedHotelRules: Boolean = false,

	@field:SerializedName("FlightType")
	val flightType: Int = 0,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("EmployeeId")
	val employeeId: String? = null,

	@field:SerializedName("EmployeeName")
	val employeeName: String? = null
)

data class JobProgress(

	@field:SerializedName("RunStart")
	val runStart: String? = null,

	@field:SerializedName("RunEnd")
	val runEnd: String? = null,

	@field:SerializedName("ProgressNum")
	val progressNum: Int = 0,

	@field:SerializedName("Progress")
	val progress: String? = null,

	@field:SerializedName("IsManual")
	val isManual: Boolean = false,

	@field:SerializedName("RowKey")
	val rowKey: Any? = null,

	@field:SerializedName("Text")
	val text: String? = null,

	@field:SerializedName("Timestamp")
	val timestamp: String? = null,

	@field:SerializedName("PnrCode")
	val pnrCode: Any? = null,

	@field:SerializedName("JobType")
	val jobType: Any? = null,

	@field:SerializedName("PnrId")
	val pnrId: Any? = null,

	@field:SerializedName("ETag")
	val eTag: Any? = null,

	@field:SerializedName("PartitionKey")
	val partitionKey: Any? = null,

	@field:SerializedName("ReferenceCode")
	val referenceCode: Any? = null,

	@field:SerializedName("Key")
	val key: Any? = null
)


data class TripTrainsItem(

		@field:SerializedName("Origin")
	val origin: String? = null,

		@field:SerializedName("BookingDate")
	val bookingDate: String? = null,

		@field:SerializedName("TripPlanId")
	val tripPlanId: Any? = null,

		@field:SerializedName("IsRefundable")
	val isRefundable: Boolean = false,

		@field:SerializedName("Segments")
	val segments: ArrayList<SegmentsItemReservationEntity> = ArrayList(),

		@field:SerializedName("Payments")
	val payments: List<PaymentsItem?>? = null,

		@field:SerializedName("OriginView")
	val originView: String? = null,

		@field:SerializedName("ActionDate")
	val actionDate: String? = null,

		@field:SerializedName("SequenceBRI")
	val sequenceBRI: Int = 0,

		@field:SerializedName("IsPostedHipchat")
	val isPostedHipchat: Boolean = false,

		@field:SerializedName("Remarks")
	val remarks: List<Any?>? = null,

		@field:SerializedName("TicketNumber")
	val ticketNumber: Any? = null,

		@field:SerializedName("TimeLimitString")
	val timeLimitString: String? = null,

		@field:SerializedName("FlightType")
	val flightType: Int = 0,

		@field:SerializedName("PrgText")
	val prgText: String? = null,

		@field:SerializedName("Child")
	val child: Int = 0,

		@field:SerializedName("PaymentStatus")
	val paymentStatus: Any? = null,

		@field:SerializedName("PaymentSuccess")
	val paymentSuccess: Boolean = false,

		@field:SerializedName("selected")
	val selected: Boolean = false,

		@field:SerializedName("FlightTypeView")
	val flightTypeView: String? = null,

		@field:SerializedName("Status")
	val status: String? = null,

		@field:SerializedName("PaymentReff")
	val paymentReff: Any? = null,

		@field:SerializedName("PaymentTimeLimit")
	val paymentTimeLimit: String? = null,

		@field:SerializedName("IsEmailSent")
	val isEmailSent: Boolean = false,

		@field:SerializedName("TicketedDate")
	val ticketedDate: String? = null,

		@field:SerializedName("PoNumber")
	val poNumber: Any? = null,

		@field:SerializedName("TicketDocName")
	val ticketDocName: Any? = null,

		@field:SerializedName("BookingDateView")
	val bookingDateView: String? = null,

		@field:SerializedName("HasCostCenterUpdated")
	val hasCostCenterUpdated: Boolean = false,

		@field:SerializedName("PnrCode")
	val pnrCode: String? = null,

		@field:SerializedName("FlightDurationPerNum")
	val flightDurationPerNum: Any? = null,

		@field:SerializedName("TimeLimitView")
	val timeLimitView: String? = null,

		@field:SerializedName("Passengers")
	val passengers: List<PassengersItem?>? = null,

		@field:SerializedName("ApprovedBy")
	val approvedBy: String? = null,

		@field:SerializedName("DestinationView")
	val destinationView: String? = null,

		@field:SerializedName("TravelAgent")
	val travelAgent: Any? = null,

		@field:SerializedName("Id")
	val id: String? = null,

		@field:SerializedName("PrgNum")
	val prgNum: Int = 0,

		@field:SerializedName("ReferenceCode")
	val referenceCode: String? = null,

		@field:SerializedName("JobProgress")
	val jobProgress: JobProgress? = null,

		@field:SerializedName("Destination")
	val destination: String? = null,

		@field:SerializedName("IsRemoved")
	val isRemoved: Boolean = false,

		@field:SerializedName("AmountBeforeReserved")
	val amountBeforeReserved: Int = 0,

		@field:SerializedName("OpsigoLastSync")
	val opsigoLastSync: String? = null,

		@field:SerializedName("TrainName")
	val trainName: String? = null,

		@field:SerializedName("DiffAmountInfo")
	val diffAmountInfo: Any? = null,

		@field:SerializedName("Infant")
	val infant: Int = 0,

		@field:SerializedName("PnrId")
	val pnrId: String? = null,

		@field:SerializedName("LinkReference")
	val linkReference: String? = null,

		@field:SerializedName("ActionDateView")
	val actionDateView: String? = null,

		@field:SerializedName("TicketedDateView")
	val ticketedDateView: String? = null,

		@field:SerializedName("Adult")
	val adult: Int = 0,

		@field:SerializedName("PrgJobType")
	val prgJobType: String? = null,

		@field:SerializedName("RemarkReject")
	val remarkReject: Any? = null,

		@field:SerializedName("TimeLimit")
	val timeLimit: String? = null,

		@field:SerializedName("Amount")
	val amount: Int = 0,

		@field:SerializedName("Contact")
	val contact: Contact? = null,

		@field:SerializedName("TicketDocUrl")
	val ticketDocUrl: Any? = null,

		@field:SerializedName("TripItemId")
	val tripItemId: String? = null
)

data class PassengersItem(

	@field:SerializedName("Email")
	val email: String? = null,

	@field:SerializedName("PassportExpire")
	val passportExpire: Any? = null,

	@field:SerializedName("IdNumber")
	val idNumber: String? = null,

	@field:SerializedName("EmployeeNik")
	val employeeNik: Any? = null,

	@field:SerializedName("Index")
	val index: Int = 0,

	@field:SerializedName("FrequentFlyer")
	val frequentFlyer: Any? = null,

	@field:SerializedName("MobilePhone")
	val mobilePhone: String? = null,

	@field:SerializedName("TravelProfileId")
	val travelProfileId: Any? = null,

	@field:SerializedName("RemarksPax")
	val remarksPax: List<Any?>? = null,

	@field:SerializedName("Remarks")
	val remarks: Any? = null,

	@field:SerializedName("TicketNumber")
	val ticketNumber: Any? = null,

	@field:SerializedName("SeatText")
	val seatText: String? = null,

	@field:SerializedName("PicEmployeeName")
	val picEmployeeName: Any? = null,

	@field:SerializedName("IdentityType")
	val identityType: Any? = null,

	@field:SerializedName("BudgetId")
	val budgetId: Any? = null,

	@field:SerializedName("RelationOfPicView")
	val relationOfPicView: String? = null,

	@field:SerializedName("Sequence")
	val sequence: Any? = null,

	@field:SerializedName("PassportFirstName")
	val passportFirstName: Any? = null,

	@field:SerializedName("IsGuest")
	val isGuest: Boolean = false,

	@field:SerializedName("Seats")
	val seats: List<Any?>? = null,

	@field:SerializedName("SeatName")
	val seatName: Any? = null,

	@field:SerializedName("PassportNumber")
	val passportNumber: Any? = null,

	@field:SerializedName("PassportLastName")
	val passportLastName: Any? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("LastName")
	val lastName: String? = null,

	@field:SerializedName("RelationOfPic")
	val relationOfPic: Int = 0,

	@field:SerializedName("AdultAssoc")
	val adultAssoc: Any? = null,

	@field:SerializedName("BirthDate")
	val birthDate: Any? = null,

	@field:SerializedName("OtherPhone")
	val otherPhone: Any? = null,

	@field:SerializedName("TripTrainId")
	val tripTrainId: String? = null,

	@field:SerializedName("JobTitleId")
	val jobTitleId: String? = null,

	@field:SerializedName("Passport")
	val passport: Passport? = null,

	@field:SerializedName("PassportOrigin")
	val passportOrigin: Any? = null,

	@field:SerializedName("SegmentBaggages")
	val segmentBaggages: List<Any?>? = null,

	@field:SerializedName("PaxType")
	val paxType: Any? = null,

	@field:SerializedName("EmployeeId")
	val employeeId: String? = null,

	@field:SerializedName("RetSsr")
	val retSsr: List<Any?>? = null,

	@field:SerializedName("FirstName")
	val firstName: String? = null,

	@field:SerializedName("Ssrs")
	val ssrs: List<Any?>? = null,

	@field:SerializedName("PicEmployeeId")
	val picEmployeeId: String? = null,

	@field:SerializedName("FrequentFlyers")
	val frequentFlyers: List<Any?>? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("Nationality")
	val nationality: String? = null,

	@field:SerializedName("CostCenterId")
	val costCenterId: Any? = null,

	@field:SerializedName("DepSsr")
	val depSsr: List<Any?>? = null,

	@field:SerializedName("Type")
	val type: String? = null,

	@field:SerializedName("IsSeniorCitizen")
	val isSeniorCitizen: Boolean = false,

	@field:SerializedName("HomePhone")
	val homePhone: String? = null,

	@field:SerializedName("FullName")
	val fullName: Any? = null,

	@field:SerializedName("SeatNumber")
	val seatNumber: Any? = null,

	@field:SerializedName("TripFlightId")
	val tripFlightId: String? = null,

	@field:SerializedName("CompanyCode")
	val companyCode: Any? = null,

	@field:SerializedName("IdentityNumber")
	val identityNumber: Any? = null
)

data class TripParticipantsItem(

		@field:SerializedName("TotalAllowance")
	val totalAllowance: Int = 0,

		@field:SerializedName("ParentCompanyName")
	val parentCompanyName: Any? = null,

		@field:SerializedName("TotalTripPaidAirline")
	val totalTripPaidAirline: Double = 0.0,

		@field:SerializedName("TripPostClaims")
	val tripPostClaims: List<Any?>? = null,

		@field:SerializedName("IsShowButtonIssuedTrain")
	val isShowButtonIssuedTrain: Boolean = false,

		@field:SerializedName("OtherEmail")
	val otherEmail: Any? = null,

		@field:SerializedName("TotalTripAirline")
	val totalTripAirline: Int = 0,

		@field:SerializedName("DefaultClassAirlineName")
	val defaultClassAirlineName: Any? = null,

		@field:SerializedName("CostCenterName")
	val costCenterName: String? = null,

		@field:SerializedName("CostCodeAlias")
	val costCodeAlias: String? = null,

		@field:SerializedName("isDisabled")
	val isDisabled: String? = null,

		@field:SerializedName("PicEmployeeName")
	val picEmployeeName: Any? = null,

		@field:SerializedName("IsShowButtonPayment")
	val isShowButtonPayment: Boolean = false,

		@field:SerializedName("Status")
	val status: Int = 0,

		@field:SerializedName("TripItemTypes")
	val tripItemTypes: List<TripItemTypesItem?>? = null,

		@field:SerializedName("ApproveDateView")
	val approveDateView: String? = null,

		@field:SerializedName("TrainBookingCodes")
	val trainBookingCodes: List<TrainBookingCodesItem?>? = null,

		@field:SerializedName("LocationCode")
	val locationCode: Any? = null,

		@field:SerializedName("Routes")
	val routes: List<RoutesItem?>? = null,

		@field:SerializedName("PassportFirstName")
	val passportFirstName: Any? = null,

		@field:SerializedName("IsAndOperatorHotel")
	val isAndOperatorHotel: Any? = null,

		@field:SerializedName("ParentCompany")
	val parentCompany: Any? = null,

		@field:SerializedName("LastArrivalTime")
	val lastArrivalTime: String? = null,

		@field:SerializedName("ApproveDate")
	val approveDate: Any? = null,

		@field:SerializedName("ApprovedBy")
	val approvedBy: Any? = null,

		@field:SerializedName("ShowButtonDebitBNI")
	val showButtonDebitBNI: Boolean = false,

		@field:SerializedName("Actual")
	val actual: Int = 0,

		@field:SerializedName("PassportLastName")
	val passportLastName: Any? = null,

		@field:SerializedName("Id")
	val id: String? = null,

		@field:SerializedName("BPDepartmentName")
	val bPDepartmentName: Any? = null,

		@field:SerializedName("BudgetName")
	val budgetName: String? = null,

		@field:SerializedName("PassportMiddleName")
	val passportMiddleName: Any? = null,

		@field:SerializedName("Plafond")
	val plafond: Int = 0,

		@field:SerializedName("DefaultPlafondHotel")
	val defaultPlafondHotel: Any? = null,

		@field:SerializedName("PaymentAirlineTimeLimitView")
	val paymentAirlineTimeLimitView: String? = null,

		@field:SerializedName("InfoCompany")
	val infoCompany: String? = null,

		@field:SerializedName("StatusView")
	val statusView: String? = null,

		@field:SerializedName("JobTitle")
	val jobTitle: String? = null,

		@field:SerializedName("PassportExpMonth")
	val passportExpMonth: Any? = null,

		@field:SerializedName("LocationName")
	val locationName: Any? = null,

		@field:SerializedName("JobTitleId")
	val jobTitleId: String? = null,

		@field:SerializedName("PassportPlace")
	val passportPlace: Any? = null,

		@field:SerializedName("BudgetCode")
	val budgetCode: String? = null,

		@field:SerializedName("EmployeeId")
	val employeeId: String? = null,

		@field:SerializedName("FirstDepartureDate")
	val firstDepartureDate: String? = null,

		@field:SerializedName("PassportExpDay")
	val passportExpDay: Any? = null,

		@field:SerializedName("PassportIssuedDate")
	val passportIssuedDate: Any? = null,

		@field:SerializedName("LicensePrinciple")
	val licensePrinciple: Any? = null,

		@field:SerializedName("EmployeeStr")
	val employeeStr: String? = null,

		@field:SerializedName("FirstName")
	val firstName: String? = null,

		@field:SerializedName("FollowUpPosition")
	val followUpPosition: Any? = null,

		@field:SerializedName("PicEmployeeId")
	val picEmployeeId: String? = null,

		@field:SerializedName("CredentialHash")
	val credentialHash: Any? = null,

		@field:SerializedName("Amount")
	val amount: Double = 0.0,

		@field:SerializedName("LastArrivalDate")
	val lastArrivalDate: String? = null,

		@field:SerializedName("FollowUpName")
	val followUpName: Any? = null,

		@field:SerializedName("Nationality")
	val nationality: String? = null,

		@field:SerializedName("EmergencyContactPhone")
	val emergencyContactPhone: Any? = null,

		@field:SerializedName("BirthDateView")
	val birthDateView: String? = null,

		@field:SerializedName("HomePhone")
	val homePhone: String? = null,

		@field:SerializedName("TripPlanId")
	val tripPlanId: String? = null,

		@field:SerializedName("Company")
	val company: String? = null,

		@field:SerializedName("Email")
	val email: String? = null,

		@field:SerializedName("PassportExpire")
	val passportExpire: Any? = null,

		@field:SerializedName("IdNumber")
	val idNumber: String? = null,

		@field:SerializedName("EmployeeNik")
	val employeeNik: String? = null,

		@field:SerializedName("FlightBookingCodes")
	val flightBookingCodes: List<FlightBookingCodesItem?>? = null,

		@field:SerializedName("Destinations")
	val destinations: String? = null,

		@field:SerializedName("EmergencyContactRelationship")
	val emergencyContactRelationship: Any? = null,

		@field:SerializedName("DefaultStarLevelHotel")
	val defaultStarLevelHotel: Any? = null,

		@field:SerializedName("MobilePhone")
	val mobilePhone: String? = null,

		@field:SerializedName("HotelBookingCodes")
	val hotelBookingCodes: List<HotelBookingCodesItem?>? = null,

		@field:SerializedName("CompanyName")
	val companyName: String? = null,

		@field:SerializedName("TotalTripPaidHotel")
	val totalTripPaidHotel: Int = 0,

		@field:SerializedName("ShowButtonApproveHotel")
	val showButtonApproveHotel: Boolean = false,

		@field:SerializedName("IsProcurement")
	val isProcurement: Boolean = false,

		@field:SerializedName("IsShowButtonIsued")
	val isShowButtonIsued: Boolean = false,

		@field:SerializedName("IncludeDailyAllowance")
	val includeDailyAllowance: Boolean = false,

		@field:SerializedName("BPFunctionName")
	val bPFunctionName: Any? = null,

		@field:SerializedName("BudgetId")
	val budgetId: String? = null,

		@field:SerializedName("SentEmailApproval")
	val sentEmailApproval: Boolean = false,

		@field:SerializedName("PaymentAirlineTimeLimit")
	val paymentAirlineTimeLimit: String? = null,

		@field:SerializedName("ListApprover")
	val listApprover: List<Any?>? = null,

		@field:SerializedName("RelationOfPicView")
	val relationOfPicView: String? = null,

		@field:SerializedName("IsGuest")
	val isGuest: Boolean = false,

		@field:SerializedName("DefaultClassAirline")
	val defaultClassAirline: Any? = null,

		@field:SerializedName("FullNameCompany")
	val fullNameCompany: String? = null,

		@field:SerializedName("DaysDailyAllowance")
	val daysDailyAllowance: Int = 0,

		@field:SerializedName("TripParticipantCustomApprovals")
	val tripParticipantCustomApprovals: ArrayList<TripParticipantCustomApprovalsItem?>? = null,

		@field:SerializedName("PointOfHired")
	val pointOfHired: Any? = null,

		@field:SerializedName("ShowButtonApproveAirline")
	val showButtonApproveAirline: Boolean = false,

		@field:SerializedName("PaymentMethod")
	val paymentMethod: Any? = null,

		@field:SerializedName("PassportNumber")
	val passportNumber: Any? = null,

		@field:SerializedName("LastName")
	val lastName: String? = null,

		@field:SerializedName("PassportExpYear")
	val passportExpYear: Any? = null,

		@field:SerializedName("RelationOfPic")
	val relationOfPic: Int = 0,

		@field:SerializedName("EmployeeName")
	val employeeName: String? = null,

		@field:SerializedName("BirthDate")
	val birthDate: String? = null,

		@field:SerializedName("CompanyView")
	val companyView: String? = null,

		@field:SerializedName("IsCompletelyReviewed")
	val isCompletelyReviewed: Boolean = false,

		@field:SerializedName("FollowUpBy")
	val followUpBy: Any? = null,

		@field:SerializedName("TotalTripPaidTrain")
	val totalTripPaidTrain: Int = 0,

		@field:SerializedName("TripPlanItems")
	val tripPlanItems: List<TripPlanItemsItem?>? = null,

		@field:SerializedName("IncludeOtherAllowance")
	val includeOtherAllowance: Boolean = false,

		@field:SerializedName("ShowButtonApproveTrain")
	val showButtonApproveTrain: Boolean = false,

		@field:SerializedName("PaymentInstruction")
	val paymentInstruction: Any? = null,

		@field:SerializedName("TotalTripTrain")
	val totalTripTrain: Int = 0,

		@field:SerializedName("LocationJobTitle")
	val locationJobTitle: Any? = null,

		@field:SerializedName("LastDestination")
	val lastDestination: String? = null,

		@field:SerializedName("PassportOrigin")
	val passportOrigin: Any? = null,

		@field:SerializedName("IsShowButtonIssuedHotel")
	val isShowButtonIssuedHotel: Boolean = false,

		@field:SerializedName("BPSegmentName")
	val bPSegmentName: Any? = null,

		@field:SerializedName("EmergencyContactEmail")
	val emergencyContactEmail: Any? = null,

		@field:SerializedName("RemarkReject")
	val remarkReject: Any? = null,

		@field:SerializedName("DaysMealAllowance")
	val daysMealAllowance: Int = 0,

		@field:SerializedName("TotalTripHotel")
	val totalTripHotel: Int = 0,

		@field:SerializedName("TripParticipantAllowance")
	val tripParticipantAllowance: List<Any?>? = null,

		@field:SerializedName("CAID")
	val cAID: Any? = null,

		@field:SerializedName("Title")
	val title: String? = null,

		@field:SerializedName("MiddleName")
	val middleName: String? = null,

		@field:SerializedName("CostCenterId")
	val costCenterId: String? = null,

		@field:SerializedName("FollowUpComp")
	val followUpComp: Any? = null,

		@field:SerializedName("isApproveForm")
	val isApproveForm: Boolean = false,

		@field:SerializedName("IncludeMealAllowance")
	val includeMealAllowance: Boolean = false,

		@field:SerializedName("FullName")
	val fullName: String? = null,

		@field:SerializedName("CostCenterCode")
	val costCenterCode: String? = null,

		@field:SerializedName("IsCostCenterUpdated")
	val isCostCenterUpdated: Boolean = false,

		@field:SerializedName("IsHavePaymentMethodAirline")
	val isHavePaymentMethodAirline: Boolean = false,

		@field:SerializedName("CompanyCode")
	val companyCode: Any? = null,

		@field:SerializedName("EmergencyContactName")
	val emergencyContactName: Any? = null,

		@field:SerializedName("IsAnyPNRWithNotCompleted")
	val isAnyPNRWithNotCompleted: Boolean = false
)

data class TripParticipantCustomApprovalsItem(

		@field:SerializedName("IsHaveAirline")
		val isHaveAirline: Boolean = false,

		@field:SerializedName("Company")
		val company: String = "",

		@field:SerializedName("HasHotel")
		val hasHotel: Boolean = false,

		@field:SerializedName("InfoCompany")
		val infoCompany: String = "",

		@field:SerializedName("CompletelyReviewedDate")
		val completelyReviewedDate: String = "",

		@field:SerializedName("IsHaveHotel")
		val isHaveHotel: Boolean = false,

		@field:SerializedName("ParentCompanyName")
		val parentCompanyName: Any? = null,

		@field:SerializedName("FollowUpHotel")
		val followUpHotel: String? = "",

		@field:SerializedName("HasTrain")
		val hasTrain: Boolean = false,

		@field:SerializedName("CompanyName")
		val companyName: String = "",

		@field:SerializedName("ParentCompany")
		val parentCompany: Any? = null,

		@field:SerializedName("FollowUpTrain")
		val followUpTrain: String? = "",

		@field:SerializedName("FollowUpAirline")
		val followUpAirline: String? = "",

		@field:SerializedName("ApproverId")
		val approverId: String = "",

		@field:SerializedName("ApproverName")
		val approverName: String = "",

		@field:SerializedName("Id")
		val id: String = "",

		@field:SerializedName("HasAirline")
		val hasAirline: Boolean = false,

		@field:SerializedName("Layers")
		val layers: Int = 0,

		@field:SerializedName("CompanyCode")
		val companyCode: String = "",

		@field:SerializedName("TripParticipantId")
		val tripParticipantId: String = "",

		@field:SerializedName("IsCompletelyReviewed")
		val isCompletelyReviewed: Boolean = false
)

data class TrainBookingCodesItem(

	@field:SerializedName("BookingCode")
	val bookingCode: String? = null,

	@field:SerializedName("BookingStatus")
	val bookingStatus: String? = null
)

data class FlightsItem(

	@field:SerializedName("Status")
	val status: Int = 0,

	@field:SerializedName("IsSecondaryLevel")
	val isSecondaryLevel: Boolean = false,

	@field:SerializedName("IsRemoved")
	val isRemoved: Boolean = false,

	@field:SerializedName("IsDownloadPnr")
	val isDownloadPnr: Boolean = false,

	@field:SerializedName("TripPlanId")
	val tripPlanId: String? = null,

	@field:SerializedName("IsManual")
	val isManual: Boolean = false,

	@field:SerializedName("TripTrains")
	val tripTrains: List<Any?>? = null,

	@field:SerializedName("TravelAgentAccount")
	val travelAgentAccount: String? = null,

	@field:SerializedName("HasConfirmed")
	val hasConfirmed: Boolean = false,

	@field:SerializedName("ItemType")
	val itemType: Int = 0,

	@field:SerializedName("Amount")
	val amount: Double = 0.0,

	@field:SerializedName("TripMemberId")
	val tripMemberId: String? = null,

	@field:SerializedName("ReasonCode")
	val reasonCode: Any? = null,

	@field:SerializedName("HasCostCenterUpdated")
	val hasCostCenterUpdated: Boolean = false,

	@field:SerializedName("TripHotels")
	val tripHotels: List<Any?>? = null,

	@field:SerializedName("TripFlights")
	val tripFlights: List<TripFlightsItem?>? = null,

	@field:SerializedName("IsViolatedHotelRules")
	val isViolatedHotelRules: Boolean = false,

	@field:SerializedName("FlightType")
	val flightType: Int = 0,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("EmployeeId")
	val employeeId: String? = null,

	@field:SerializedName("EmployeeName")
	val employeeName: String? = null
)

data class SegmentsItem(

	@field:SerializedName("Origin")
	val origin: String? = null,

	@field:SerializedName("IsComply")
	val isComply: Boolean = false,

	@field:SerializedName("ArriveTime")
	val arriveTime: String? = null,

	@field:SerializedName("Airline")
	val airline: Int = 0,

	@field:SerializedName("IsSecuritySensivity")
	val isSecuritySensivity: Boolean = false,

	@field:SerializedName("SequenceBRI")
	val sequenceBRI: Int = 0,

	@field:SerializedName("ArriveDateTime")
	val arriveDateTime: String? = null,

	@field:SerializedName("DescAdvanceBooking")
	val descAdvanceBooking: Any? = null,

	@field:SerializedName("OperatingAirlineName")
	val operatingAirlineName: Any? = null,

	@field:SerializedName("AirportOrigin")
	val airportOrigin: String? = null,

	@field:SerializedName("OperatingAirlineImageUrl")
	val operatingAirlineImageUrl: Any? = null,

	@field:SerializedName("DestinationName")
	val destinationName: String? = null,

	@field:SerializedName("ArriveDate")
	val arriveDate: String? = null,

	@field:SerializedName("DepartureDate")
	val departureDate: Any? = null,

	@field:SerializedName("LowestFare")
	val lowestFare: Int = 0,

	@field:SerializedName("AirlineImageUrl")
	val airlineImageUrl: String? = null,

	@field:SerializedName("DepartDateTime")
	val departDateTime: String? = null,

	@field:SerializedName("IsAirlineCompliance")
	val isAirlineCompliance: Boolean = false,

	@field:SerializedName("Duration")
	val duration: String? = null,

	@field:SerializedName("IsRefund")
	val isRefund: Boolean = false,

	@field:SerializedName("DepartDate")
	val departDate: String? = null,

	@field:SerializedName("ClassCode")
	val classCode: String? = null,

	@field:SerializedName("FlightNumber")
	val flightNumber: String? = null,

	@field:SerializedName("AirportDestination")
	val airportDestination: String? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("DescSecuritySensitivity")
	val descSecuritySensitivity: Any? = null,

	@field:SerializedName("Seq")
	val seq: Int = 0,

	@field:SerializedName("IsSecondaryLevel")
	val isSecondaryLevel: Boolean = false,

	@field:SerializedName("IsAdvanceBooking")
	val isAdvanceBooking: Boolean = false,

	@field:SerializedName("Destination")
	val destination: String? = null,

	@field:SerializedName("Category")
	val category: String? = null,

	@field:SerializedName("AirlineName")
	val airlineName: String? = null,

	@field:SerializedName("IsLowestFare")
	val isLowestFare: Boolean = false,

	@field:SerializedName("DepartTime")
	val departTime: String? = null,

	@field:SerializedName("CategoryCabin")
	val categoryCabin: Any? = null,

	@field:SerializedName("TransitDuration")
	val transitDuration: Any? = null,

	@field:SerializedName("IsReschedule")
	val isReschedule: Boolean = false,

	@field:SerializedName("Num")
	val num: Int = 0,

	@field:SerializedName("CityDestination")
	val cityDestination: String? = null,

	@field:SerializedName("OperatingFlightNumber")
	val operatingFlightNumber: Any? = null,

	@field:SerializedName("OriginName")
	val originName: String? = null,

	@field:SerializedName("ArrivalDate")
	val arrivalDate: Any? = null,

	@field:SerializedName("FlightAvailability")
	val flightAvailability: Any? = null,

	@field:SerializedName("CarrierCode")
	val carrierCode: Any? = null,

	@field:SerializedName("CityOrigin")
	val cityOrigin: String? = null,

	@field:SerializedName("CountryOrigin")
	val countryOrigin: String? = null,

	@field:SerializedName("IsRestrictedDest")
	val isRestrictedDest: Boolean = false,

	@field:SerializedName("TripFlightId")
	val tripFlightId: String? = null,

	@field:SerializedName("CountryDestination")
	val countryDestination: String? = null
)

data class TripFlightsItem(

	@field:SerializedName("Origin")
	val origin: String? = null,

	@field:SerializedName("BookingDate")
	val bookingDate: String? = null,

	@field:SerializedName("TripPlanId")
	val tripPlanId: Any? = null,

	@field:SerializedName("IsRefundable")
	val isRefundable: Boolean = false,

	@field:SerializedName("Airline")
	val airline: Int = 0,

	@field:SerializedName("DestinationCity")
	val destinationCity: Any? = null,

	@field:SerializedName("BookingDateString")
	val bookingDateString: String? = null,

	@field:SerializedName("Segments")
	val segments: List<SegmentsItem?>? = null,

	@field:SerializedName("Payments")
	val payments: List<PaymentsItem?>? = null,

	@field:SerializedName("OriginView")
	val originView: String? = null,

	@field:SerializedName("ActionDate")
	val actionDate: String? = null,

	@field:SerializedName("IsPostedHipchat")
	val isPostedHipchat: Boolean = false,

	@field:SerializedName("Remarks")
	val remarks: List<Any?>? = null,

	@field:SerializedName("TicketNumber")
	val ticketNumber: Any? = null,

	@field:SerializedName("TimeLimitString")
	val timeLimitString: String? = null,

	@field:SerializedName("FlightType")
	val flightType: Int = 0,

	@field:SerializedName("Carrier")
	val carrier: String? = null,

	@field:SerializedName("PrgText")
	val prgText: Any? = null,

	@field:SerializedName("Child")
	val child: Int = 0,

	@field:SerializedName("OpsigoStatus")
	val opsigoStatus: Any? = null,

	@field:SerializedName("PaymentStatus")
	val paymentStatus: Any? = null,

	@field:SerializedName("PaymentSuccess")
	val paymentSuccess: Boolean = false,

	@field:SerializedName("selected")
	val selected: Boolean = false,

	@field:SerializedName("FlightTypeView")
	val flightTypeView: String? = null,

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("PaymentReff")
	val paymentReff: Any? = null,

	@field:SerializedName("PaymentTimeLimit")
	val paymentTimeLimit: String? = null,

	@field:SerializedName("IsEmailSent")
	val isEmailSent: Boolean = false,

	@field:SerializedName("TicketedDate")
	val ticketedDate: Any? = null,

	@field:SerializedName("PoNumber")
	val poNumber: Any? = null,

	@field:SerializedName("TicketDocName")
	val ticketDocName: Any? = null,

	@field:SerializedName("OriginCity")
	val originCity: Any? = null,

	@field:SerializedName("BookingDateView")
	val bookingDateView: String? = null,

	@field:SerializedName("HasCostCenterUpdated")
	val hasCostCenterUpdated: Boolean = false,

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
	val destinationView: String? = null,

	@field:SerializedName("TravelAgent")
	val travelAgent: Any? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("PrgNum")
	val prgNum: Int = 0,

	@field:SerializedName("JobProgress")
	val jobProgress: JobProgress? = null,

	@field:SerializedName("Destination")
	val destination: String? = null,

	@field:SerializedName("IsRemoved")
	val isRemoved: Boolean = false,

	@field:SerializedName("AmountBeforeReserved")
	val amountBeforeReserved: Int = 0,

	@field:SerializedName("OpsigoLastSync")
	val opsigoLastSync: String? = null,

	@field:SerializedName("FreeBaggage")
	val freeBaggage: Any? = null,

	@field:SerializedName("VoidDate")
	val voidDate: String? = null,

	@field:SerializedName("DiffAmountInfo")
	val diffAmountInfo: Any? = null,

	@field:SerializedName("Infant")
	val infant: Int = 0,

	@field:SerializedName("PnrId")
	val pnrId: String? = null,

	@field:SerializedName("LinkReference")
	val linkReference: String? = null,

	@field:SerializedName("ActionDateView")
	val actionDateView: String? = null,

	@field:SerializedName("TicketedDateView")
	val ticketedDateView: Any? = null,

	@field:SerializedName("Adult")
	val adult: Int = 0,

	@field:SerializedName("PrgJobType")
	val prgJobType: String? = null,

	@field:SerializedName("RemarkReject")
	val remarkReject: Any? = null,

	@field:SerializedName("TimeLimit")
	val timeLimit: String? = null,

	@field:SerializedName("AirlineView")
	val airlineView: String? = null,

	@field:SerializedName("Amount")
	val amount: Double = 0.0,

	@field:SerializedName("DestinationAirport")
	val destinationAirport: Any? = null,

	@field:SerializedName("Contact")
	val contact: Contact? = null,

	@field:SerializedName("OriginAirport")
	val originAirport: Any? = null,

	@field:SerializedName("IsVoid")
	val isVoid: Boolean = false,

	@field:SerializedName("TicketDocUrl")
	val ticketDocUrl: Any? = null,

	@field:SerializedName("TripItemId")
	val tripItemId: String? = null
)
