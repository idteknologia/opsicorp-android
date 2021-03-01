package opsigo.com.datalayer.model.accomodation.hotel.booking

import com.google.gson.annotations.SerializedName

data class BookingHotelEntity(

		@field:SerializedName("errorMessage")
	val errorMessage: String? = null,

		@field:SerializedName("model")
	val model: ModelHotelEntity? = null,

		@field:SerializedName("currBudget")
	val currBudget: Double? = null,

		@field:SerializedName("isSuccess")
	val isSuccess: Boolean? = null
)

data class PassengersHotelEntity(

		@field:SerializedName("Email")
	val email: String? = null,

		@field:SerializedName("PassportExpire")
	val passportExpire: Any? = null,

		@field:SerializedName("IdNumber")
	val idNumber: Any? = null,

		@field:SerializedName("EmployeeNik")
	val employeeNik: String? = null,

		@field:SerializedName("Index")
	val index: Int? = null,

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
	val isGuest: Boolean? = null,

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
	val relationOfPic: Int? = null,

		@field:SerializedName("AdultAssoc")
	val adultAssoc: Any? = null,

		@field:SerializedName("BirthDate")
	val birthDate: String? = null,

		@field:SerializedName("OtherPhone")
	val otherPhone: Any? = null,

		@field:SerializedName("TripTrainId")
	val tripTrainId: String? = null,

		@field:SerializedName("JobTitleId")
	val jobTitleId: String? = null,

		@field:SerializedName("Passport")
	val passport: PassportHotelEntity? = null,

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
	val isSeniorCitizen: Boolean? = null,

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

data class GuestItemHotelEntity(

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
	val passport: Any? = null,

	@field:SerializedName("HomePhone")
	val homePhone: String? = null,

	@field:SerializedName("TicketNumber")
	val ticketNumber: Any? = null,

	@field:SerializedName("Remarks")
	val remarks: List<String?>? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("LastName")
	val lastName: String? = null,

	@field:SerializedName("Seq")
	val seq: Int? = null,

	@field:SerializedName("BirthDate")
	val birthDate: String? = null
)

data class ContactHotelEntity(

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
	val employeeId: Any? = null,

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
	val homePhone: Any? = null,

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

data class PaymentsItemHotelEntity(

	@field:SerializedName("Amount")
	val amount: Double? = null,

	@field:SerializedName("Currency")
	val currency: String? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("TripHotelId")
	val tripHotelId: String? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("Code")
	val code: String? = null,

	@field:SerializedName("Seq")
	val seq: Int? = null
)

data class PassportHotelEntity(

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

data class RemarksItemHotelEntity(

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

data class ModelHotelEntity(

		@field:SerializedName("Origin")
	val origin: String? = null,

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
	val remarks: Any? = null,

		@field:SerializedName("NotComplyDetails")
	val notComplyDetails: List<Any?>? = null,

		@field:SerializedName("FlightType")
	val flightType: Int? = null,

		@field:SerializedName("UseWizardSeatSelection")
	val useWizardSeatSelection: Boolean? = null,

		@field:SerializedName("CallbackUri")
	val callbackUri: Any? = null,

		@field:SerializedName("Child")
	val child: Int? = null,

		@field:SerializedName("Status")
	val status: Any? = null,

		@field:SerializedName("FlightTripType")
	val flightTripType: Int? = null,

		@field:SerializedName("IsManual")
	val isManual: Boolean? = null,

		@field:SerializedName("IsWithoutSpj")
	val isWithoutSpj: Boolean? = null,

		@field:SerializedName("TicketedDate")
	val ticketedDate: Any? = null,

		@field:SerializedName("OpsigoPassengers")
	val opsigoPassengers: List<Any?>? = null,

		@field:SerializedName("BookingDateView")
	val bookingDateView: Any? = null,

		@field:SerializedName("Code")
	val code: String? = null,

		@field:SerializedName("ReasonCode")
	val reasonCode: String? = null,

		@field:SerializedName("IsUpdatePassport")
	val isUpdatePassport: Boolean? = null,

		@field:SerializedName("TimeLimitView")
	val timeLimitView: String? = null,

		@field:SerializedName("Passengers")
	val passengers: List<PassengersHotelEntity?>? = null,

		@field:SerializedName("CreatedView")
	val createdView: Any? = null,

		@field:SerializedName("Id")
	val id: String? = null,

		@field:SerializedName("Journey")
	val journey: Any? = null,

		@field:SerializedName("Members")
	val members: List<String?>? = null,

		@field:SerializedName("Hotel")
	val hotel: HotelHotelEntity? = null,

		@field:SerializedName("Reserved")
	val reserved: String? = null,

		@field:SerializedName("BookingCode")
	val bookingCode: Any? = null,

		@field:SerializedName("Destination")
	val destination: String? = null,

		@field:SerializedName("IsRemoved")
	val isRemoved: Boolean? = null,

		@field:SerializedName("TrainName")
	val trainName: Any? = null,

		@field:SerializedName("Infant")
	val infant: Int? = null,

		@field:SerializedName("Created")
	val created: String? = null,

		@field:SerializedName("PaymentType")
	val paymentType: Int? = null,

		@field:SerializedName("BookingProgress")
	val bookingProgress: List<Any?>? = null,

		@field:SerializedName("Adult")
	val adult: Int? = null,

		@field:SerializedName("Supplier")
	val supplier: Int? = null,

		@field:SerializedName("EmployeeId")
	val employeeId: String? = null,

		@field:SerializedName("TimeLimit")
	val timeLimit: String? = null,

		@field:SerializedName("ListConflict")
	val listConflict: List<Any?>? = null,

		@field:SerializedName("Amount")
	val amount: Double? = null,

		@field:SerializedName("Purpose")
	val purpose: Any? = null,

		@field:SerializedName("TotalAmount")
	val totalAmount: Double? = null,

		@field:SerializedName("ApprovalCode")
	val approvalCode: Any? = null,

		@field:SerializedName("Contact")
	val contact: ContactHotelEntity? = null,

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

data class HotelHotelEntity(

		@field:SerializedName("RemarkHotel")
	val remarkHotel: String? = null,

		@field:SerializedName("Address")
	val address: String? = null,

		@field:SerializedName("IsSync")
	val isSync: Boolean? = null,

		@field:SerializedName("Payments")
	val payments: List<PaymentsItemHotelEntity?>? = null,

		@field:SerializedName("Hash")
	val hash: Any? = null,

		@field:SerializedName("Image")
	val image: String? = null,

		@field:SerializedName("CancellationPolicies")
	val cancellationPolicies: List<Any?>? = null,

		@field:SerializedName("ActionDate")
	val actionDate: String? = null,

		@field:SerializedName("IsFullCharge")
	val isFullCharge: Boolean? = null,

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
	val isEmailSent: Boolean? = null,

		@field:SerializedName("TicketedDate")
	val ticketedDate: Any? = null,

		@field:SerializedName("StatusName")
	val statusName: String? = null,

		@field:SerializedName("IsRefund")
	val isRefund: Boolean? = null,

		@field:SerializedName("DiscountAmount")
	val discountAmount: Double? = null,

		@field:SerializedName("CheckinView")
	val checkinView: String? = null,

		@field:SerializedName("TripCode")
	val tripCode: Any? = null,

		@field:SerializedName("TimeLimitView")
	val timeLimitView: String? = null,

		@field:SerializedName("IsViolatedHotelRules")
	val isViolatedHotelRules: Boolean? = null,

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
	val tourismTax: Double? = null,

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
	val amount: Double? = null,

		@field:SerializedName("TotalAmountApi")
	val totalAmountApi: Double? = null,

		@field:SerializedName("Facilities")
	val facilities: Any? = null,

		@field:SerializedName("Rooms")
	val rooms: Int? = null,

		@field:SerializedName("AmountWithSf")
	val amountWithSf: Double? = null,

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
	val isForwardedBooking: Boolean? = null,

		@field:SerializedName("FormBookingUrl")
	val formBookingUrl: String? = null,

		@field:SerializedName("CancelReff")
	val cancelReff: Any? = null,

		@field:SerializedName("Name")
	val name: String? = null,

		@field:SerializedName("Remarks")
	val remarks: List<RemarksItemHotelEntity?>? = null,

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
	val paymentSuccess: Boolean? = null,

		@field:SerializedName("IsSentEmail")
	val isSentEmail: Boolean? = null,

		@field:SerializedName("PaymentReff")
	val paymentReff: Any? = null,

		@field:SerializedName("ConfirmationId")
	val confirmationId: String? = null,

		@field:SerializedName("CitiNameApi")
	val citiNameApi: String? = null,

		@field:SerializedName("Guest")
	val guest: List<GuestItemHotelEntity?>? = null,

		@field:SerializedName("ConfirmationUrl")
	val confirmationUrl: String? = null,

		@field:SerializedName("Duration")
	val duration: Int? = null,

		@field:SerializedName("City")
	val city: Any? = null,

		@field:SerializedName("CityName")
	val cityName: String? = null,

		@field:SerializedName("EmailFD")
	val emailFD: Any? = null,

		@field:SerializedName("ServiceFee")
	val serviceFee: Double? = null,

		@field:SerializedName("Area")
	val area: String? = null,

		@field:SerializedName("IsFullDeduct")
	val isFullDeduct: Boolean? = null,

		@field:SerializedName("DiscountType")
	val discountType: Int? = null,

		@field:SerializedName("PlafondAmount")
	val plafondAmount: Double? = null,

		@field:SerializedName("TravelAgent")
	val travelAgent: Any? = null,

		@field:SerializedName("CreatedDate")
	val createdDate: String? = null,

		@field:SerializedName("Message")
	val message: Any? = null,

		@field:SerializedName("Rating")
	val rating: Int? = null,

		@field:SerializedName("IsTourism")
	val isTourism: Boolean? = null,

		@field:SerializedName("CancellationPoliciesView")
	val cancellationPoliciesView: List<String?>? = null,

		@field:SerializedName("CheckoutView")
	val checkoutView: String? = null,

		@field:SerializedName("EmailFO")
	val emailFO: Any? = null,

		@field:SerializedName("PnrId")
	val pnrId: String? = null,

		@field:SerializedName("Phone")
	val phone: String? = null,

		@field:SerializedName("IsGuarantedBooking")
	val isGuarantedBooking: Boolean? = null,

		@field:SerializedName("RemarkReject")
	val remarkReject: Any? = null,

		@field:SerializedName("IsHsre")
	val isHsre: Boolean? = null,

		@field:SerializedName("CheckOutFormatString")
	val checkOutFormatString: String? = null,

		@field:SerializedName("IsReschedule")
	val isReschedule: Boolean? = null,

		@field:SerializedName("TotalAmount")
	val totalAmount: Double? = null,

		@field:SerializedName("IsWithBreakfast")
	val isWithBreakfast: Boolean? = null,

		@field:SerializedName("BookingCodeNew")
	val bookingCodeNew: Any? = null,

		@field:SerializedName("Contact")
	val contact: ContactHotelEntity? = null,

		@field:SerializedName("HotelKey")
	val hotelKey: String? = null,

		@field:SerializedName("RoomCategory")
	val roomCategory: String? = null,

		@field:SerializedName("TimeLimitFormatString")
	val timeLimitFormatString: String? = null,

		@field:SerializedName("TripItemId")
	val tripItemId: String? = null,

		@field:SerializedName("AmountPerNight")
	val amountPerNight: Double? = null
)
