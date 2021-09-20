package opsigo.com.datalayer.model.travel_request

import com.google.gson.annotations.SerializedName

data class ChangeTripEntity(

	@field:SerializedName("model")
	val model: Model? = null,

	@field:SerializedName("isDisableCbt")
	val isDisableCbt: Boolean = false
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
	val jobTitle: String? = null,

	@field:SerializedName("MobilePhone")
	val mobilePhone: String? = null,

	@field:SerializedName("CompanyName")
	val companyName: String? = null,

	@field:SerializedName("JobTitleId")
	val jobTitleId: String? = null,

	@field:SerializedName("NikEmployee")
	val nikEmployee: Any? = null,

	@field:SerializedName("EmployeeId")
	val employeeId: Any? = null,

	@field:SerializedName("JobTitleName")
	val jobTitleName: String? = null,

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
	val birthDate: Any? = null
)

data class TripParticipantsItem(

	@field:SerializedName("FunctionID")
	val functionID: String? = null,

	@field:SerializedName("CashAdvance")
	val cashAdvance: Double? = null,

	@field:SerializedName("PositionID")
	val positionID: String? = null,

	@field:SerializedName("TotalAllowance")
	val totalAllowance: Double? = null,

	@field:SerializedName("ParentCompanyName")
	val parentCompanyName: Any? = null,

	@field:SerializedName("TotalTripPaidAirline")
	val totalTripPaidAirline: Double? = null,

	@field:SerializedName("TripPostClaims")
	val tripPostClaims: List<Any?>? = null,

	@field:SerializedName("IsShowButtonIssuedTrain")
	val isShowButtonIssuedTrain: Boolean? = null,

	@field:SerializedName("OtherEmail")
	val otherEmail: Any? = null,

	@field:SerializedName("TotalTripAirline")
	val totalTripAirline: Int? = null,

	@field:SerializedName("DefaultClassAirlineName")
	val defaultClassAirlineName: String? = null,

	@field:SerializedName("CostCenterName")
	val costCenterName: Any? = null,

	@field:SerializedName("CostCodeAlias")
	val costCodeAlias: Any? = null,

	@field:SerializedName("isDisabled")
	val isDisabled: String? = null,

	@field:SerializedName("PicEmployeeName")
	val picEmployeeName: Any? = null,

	@field:SerializedName("Status")
	val status: Int? = null,

	@field:SerializedName("IsShowButtonCancel")
	val isShowButtonCancel: Boolean? = null,

	@field:SerializedName("EstFlightCurrency")
	val estFlightCurrency: String? = null,

	@field:SerializedName("CostCenterUsed")
	val costCenterUsed: Any? = null,

	@field:SerializedName("TripItemTypes")
	val tripItemTypes: List<Any?>? = null,

	@field:SerializedName("ApproveDateView")
	val approveDateView: String? = null,

	@field:SerializedName("TrainBookingCodes")
	val trainBookingCodes: List<Any?>? = null,

	@field:SerializedName("UseCostCenterOther")
	val useCostCenterOther: Boolean? = null,

	@field:SerializedName("LocationCode")
	val locationCode: Any? = null,

	@field:SerializedName("Routes")
	val routes: List<Any?>? = null,

	@field:SerializedName("PassportFirstName")
	val passportFirstName: Any? = null,

	@field:SerializedName("IsAndOperatorHotel")
	val isAndOperatorHotel: Boolean? = null,

	@field:SerializedName("ParentCompany")
	val parentCompany: Any? = null,

	@field:SerializedName("EstAllowanceCurrency")
	val estAllowanceCurrency: String? = null,

	@field:SerializedName("LastArrivalTime")
	val lastArrivalTime: String? = null,

	@field:SerializedName("GolPerInternational")
	val golPerInternational: String? = null,

	@field:SerializedName("ApproveDate")
	val approveDate: Any? = null,

	@field:SerializedName("ApprovedBy")
	val approvedBy: Any? = null,

	@field:SerializedName("ShowButtonDebitBNI")
	val showButtonDebitBNI: Boolean? = null,

	@field:SerializedName("Actual")
	val actual: Double? = null,

	@field:SerializedName("PassportLastName")
	val passportLastName: Any? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("BPDepartmentName")
	val bPDepartmentName: Any? = null,

	@field:SerializedName("BudgetName")
	val budgetName: Any? = null,

	@field:SerializedName("PassportMiddleName")
	val passportMiddleName: Any? = null,

	@field:SerializedName("Plafond")
	val plafond: Double? = null,

	@field:SerializedName("DefaultPlafondHotel")
	val defaultPlafondHotel: Double? = null,

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

	@field:SerializedName("AssignmentNumber")
	val assignmentNumber: String? = null,

	@field:SerializedName("PersonalArea")
	val personalArea: String? = null,

	@field:SerializedName("BudgetCode")
	val budgetCode: Any? = null,

	@field:SerializedName("EmployeeId")
	val employeeId: String? = null,

	@field:SerializedName("CostCenterPicEmail")
	val costCenterPicEmail: String? = null,

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
	val amount: Double? = null,

	@field:SerializedName("LastArrivalDate")
	val lastArrivalDate: String? = null,

	@field:SerializedName("FollowUpName")
	val followUpName: Any? = null,

	@field:SerializedName("Nationality")
	val nationality: String? = null,

	@field:SerializedName("EstTotalCurrency")
	val estTotalCurrency: String? = null,

	@field:SerializedName("EmergencyContactPhone")
	val emergencyContactPhone: Any? = null,

	@field:SerializedName("FunctionText")
	val functionText: String? = null,

	@field:SerializedName("BirthDateView")
	val birthDateView: String? = null,

	@field:SerializedName("HomePhone")
	val homePhone: String? = null,

	@field:SerializedName("EstTransportation")
	val estTransportation: Double? = null,

	@field:SerializedName("CostCenterDefault")
	val costCenterDefault: String? = null,

	@field:SerializedName("TripPlanId")
	val tripPlanId: String? = null,

	@field:SerializedName("Company")
	val company: String? = null,

	@field:SerializedName("PersonalSubareaText")
	val personalSubareaText: String? = null,

	@field:SerializedName("Email")
	val email: String? = null,

	@field:SerializedName("PassportExpire")
	val passportExpire: Any? = null,

	@field:SerializedName("EstMiscellaneous")
	val estMiscellaneous: Double? = null,

	@field:SerializedName("IdNumber")
	val idNumber: Any? = null,

	@field:SerializedName("EmployeeNik")
	val employeeNik: String? = null,

	@field:SerializedName("FlightBookingCodes")
	val flightBookingCodes: List<Any?>? = null,

	@field:SerializedName("Destinations")
	val destinations: String? = null,

	@field:SerializedName("EmergencyContactRelationship")
	val emergencyContactRelationship: Any? = null,

	@field:SerializedName("DirectorateText")
	val directorateText: String? = null,

	@field:SerializedName("DefaultStarLevelHotel")
	val defaultStarLevelHotel: Int? = null,

	@field:SerializedName("MobilePhone")
	val mobilePhone: String? = null,

	@field:SerializedName("HotelBookingCodes")
	val hotelBookingCodes: List<Any?>? = null,

	@field:SerializedName("CompanyName")
	val companyName: String? = null,

	@field:SerializedName("TotalTripPaidHotel")
	val totalTripPaidHotel: Double? = null,

	@field:SerializedName("PersonalAreaText")
	val personalAreaText: String? = null,

	@field:SerializedName("EstMiscellaneousCurrency")
	val estMiscellaneousCurrency: Any? = null,

	@field:SerializedName("ShowButtonApproveHotel")
	val showButtonApproveHotel: Boolean? = null,

	@field:SerializedName("IsShowButtonPayment")
	val isShowButtonPayment: Boolean? = null,

	@field:SerializedName("CashAdvancePaidCurrency")
	val cashAdvancePaidCurrency: Any? = null,

	@field:SerializedName("IsProcurement")
	val isProcurement: Boolean? = null,

	@field:SerializedName("EstTotal")
	val estTotal: Double? = null,

	@field:SerializedName("IsShowButtonIsued")
	val isShowButtonIsued: Boolean? = null,

	@field:SerializedName("IncludeDailyAllowance")
	val includeDailyAllowance: Boolean? = null,

	@field:SerializedName("CostCenterDefaultName")
	val costCenterDefaultName: String? = null,

	@field:SerializedName("BPFunctionName")
	val bPFunctionName: Any? = null,

	@field:SerializedName("PositionName")
	val positionName: String? = null,

	@field:SerializedName("BudgetId")
	val budgetId: Any? = null,

	@field:SerializedName("SentEmailApproval")
	val sentEmailApproval: Boolean? = null,

	@field:SerializedName("PaymentAirlineTimeLimit")
	val paymentAirlineTimeLimit: Any? = null,

	@field:SerializedName("ListApprover")
	val listApprover: List<Any?>? = null,

	@field:SerializedName("RelationOfPicView")
	val relationOfPicView: String? = null,

	@field:SerializedName("UseCashAdvance")
	val useCashAdvance: Boolean? = null,

	@field:SerializedName("IsGuest")
	val isGuest: Boolean? = null,

	@field:SerializedName("CashAdvanceCurrency")
	val cashAdvanceCurrency: Any? = null,

	@field:SerializedName("EstLaundryCurrency")
	val estLaundryCurrency: String? = null,

	@field:SerializedName("DefaultClassAirline")
	val defaultClassAirline: Int? = null,

	@field:SerializedName("FullNameCompany")
	val fullNameCompany: Any? = null,

	@field:SerializedName("DaysDailyAllowance")
	val daysDailyAllowance: Int? = null,

	@field:SerializedName("CashAdvancePaid")
	val cashAdvancePaid: Double? = null,

	@field:SerializedName("TripParticipantCustomApprovals")
	val tripParticipantCustomApprovals: List<Any?>? = null,

	@field:SerializedName("PointOfHired")
	val pointOfHired: Any? = null,

	@field:SerializedName("ItineraryUrl")
	val itineraryUrl: Any? = null,

	@field:SerializedName("EstAllowanceEventCurrency")
	val estAllowanceEventCurrency: Any? = null,

	@field:SerializedName("ShowButtonApproveAirline")
	val showButtonApproveAirline: Boolean? = null,

	@field:SerializedName("PaymentMethod")
	val paymentMethod: Any? = null,

	@field:SerializedName("PassportNumber")
	val passportNumber: Any? = null,

	@field:SerializedName("LastName")
	val lastName: String? = null,

	@field:SerializedName("PassportExpYear")
	val passportExpYear: Any? = null,

	@field:SerializedName("RelationOfPic")
	val relationOfPic: Int? = null,

	@field:SerializedName("EmployeeName")
	val employeeName: String? = null,

	@field:SerializedName("BirthDate")
	val birthDate: String? = null,

	@field:SerializedName("CompanyView")
	val companyView: String? = null,

	@field:SerializedName("IsCompletelyReviewed")
	val isCompletelyReviewed: Boolean? = null,

	@field:SerializedName("PersonalSubArea")
	val personalSubArea: String? = null,

	@field:SerializedName("EstLaundry")
	val estLaundry: Double? = null,

	@field:SerializedName("FollowUpBy")
	val followUpBy: String? = null,

	@field:SerializedName("TotalTripPaidTrain")
	val totalTripPaidTrain: Double? = null,

	@field:SerializedName("TripPlanItems")
	val tripPlanItems: List<Any?>? = null,

	@field:SerializedName("IncludeOtherAllowance")
	val includeOtherAllowance: Boolean? = null,

	@field:SerializedName("ShowButtonApproveTrain")
	val showButtonApproveTrain: Boolean? = null,

	@field:SerializedName("PaymentInstruction")
	val paymentInstruction: Any? = null,

	@field:SerializedName("TotalTripTrain")
	val totalTripTrain: Int? = null,

	@field:SerializedName("EstAllowanceEvent")
	val estAllowanceEvent: Double? = null,

	@field:SerializedName("LocationJobTitle")
	val locationJobTitle: Any? = null,

	@field:SerializedName("GolPerDomestic")
	val golPerDomestic: String? = null,

	@field:SerializedName("LastDestination")
	val lastDestination: String? = null,

	@field:SerializedName("PassportOrigin")
	val passportOrigin: Any? = null,

	@field:SerializedName("EstHotel")
	val estHotel: Double? = null,

	@field:SerializedName("IsShowButtonIssuedHotel")
	val isShowButtonIssuedHotel: Boolean? = null,

	@field:SerializedName("BPSegmentName")
	val bPSegmentName: Any? = null,

	@field:SerializedName("KBO")
	val kBO: String? = null,

	@field:SerializedName("EstAllowance")
	val estAllowance: Double? = null,

	@field:SerializedName("EmergencyContactEmail")
	val emergencyContactEmail: Any? = null,

	@field:SerializedName("RemarkReject")
	val remarkReject: Any? = null,

	@field:SerializedName("DaysMealAllowance")
	val daysMealAllowance: Int? = null,

	@field:SerializedName("Age")
	val age: Double? = null,

	@field:SerializedName("CashAdvanceTransfer")
	val cashAdvanceTransfer: String? = null,

	@field:SerializedName("TotalTripHotel")
	val totalTripHotel: Int? = null,

	@field:SerializedName("TripParticipantAllowance")
	val tripParticipantAllowance: List<Any?>? = null,

	@field:SerializedName("DirectorateID")
	val directorateID: String? = null,

	@field:SerializedName("CAID")
	val cAID: Any? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("MiddleName")
	val middleName: Any? = null,

	@field:SerializedName("CostCenterId")
	val costCenterId: String? = null,

	@field:SerializedName("FollowUpComp")
	val followUpComp: Any? = null,

	@field:SerializedName("isApproveForm")
	val isApproveForm: Boolean? = null,

	@field:SerializedName("EstFlight")
	val estFlight: Double? = null,

	@field:SerializedName("IncludeMealAllowance")
	val includeMealAllowance: Boolean? = null,

	@field:SerializedName("FullName")
	val fullName: String? = null,

	@field:SerializedName("CostCenterCode")
	val costCenterCode: String? = null,

	@field:SerializedName("IsCostCenterUpdated")
	val isCostCenterUpdated: Boolean? = null,

	@field:SerializedName("IsHavePaymentMethodAirline")
	val isHavePaymentMethodAirline: Boolean? = null,

	@field:SerializedName("CompanyCode")
	val companyCode: Any? = null,

	@field:SerializedName("EstHotelCurrency")
	val estHotelCurrency: String? = null,

	@field:SerializedName("EstTransportationCurrency")
	val estTransportationCurrency: String? = null,

	@field:SerializedName("EmergencyContactName")
	val emergencyContactName: Any? = null,

	@field:SerializedName("IsAnyPNRWithNotCompleted")
	val isAnyPNRWithNotCompleted: Boolean? = null
)

data class RoutesItem(

	@field:SerializedName("Origin")
	val origin: String? = null,

	@field:SerializedName("Destination")
	val destination: String? = null,

	@field:SerializedName("DestinationCountry")
	val destinationCountry: Any? = null,

	@field:SerializedName("TripPlanId")
	val tripPlanId: String? = null,

	@field:SerializedName("Transportation")
	val transportation: Int = 0,

	@field:SerializedName("DepartureDateShort")
	val departureDateShort: String? = null,

	@field:SerializedName("Region")
	val region: String? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("DepartureDate")
	val departureDate: String? = null,

	@field:SerializedName("DepartureDateView")
	val departureDateView: String? = null,

	@field:SerializedName("TransportationView")
	val transportationView: String? = null,

	@field:SerializedName("Seq")
	val seq: Int? = null,

	@field:SerializedName("ValidateRange")
	val validateRange: Boolean? = null,
)

data class Model(

	@field:SerializedName("Origin")
	val origin: String? = null,

	@field:SerializedName("CreationDate")
	val creationDate: String? = null,

	@field:SerializedName("IsShowPolicy")
	val isShowPolicy: Boolean? = null,

	@field:SerializedName("TimeLimitRemaining")
	val timeLimitRemaining: String? = null,

	@field:SerializedName("TotalAllowance")
	val totalAllowance: Double? = null,

	@field:SerializedName("PartnerLastName")
	val partnerLastName: String? = null,

	@field:SerializedName("TotalExpenditureAirline")
	val totalExpenditureAirline: Double? = null,

	@field:SerializedName("TripMembers")
	val tripMembers: List<Any?>? = null,

	@field:SerializedName("Remark")
	val remark: String? = null,

	@field:SerializedName("BudgetSourceName")
	val budgetSourceName: Any? = null,

	@field:SerializedName("LastModified")
	val lastModified: String? = null,

	@field:SerializedName("UrlCoverLetter")
	val urlCoverLetter: Any? = null,

	@field:SerializedName("SeqC4")
	val seqC4: Int? = null,

	@field:SerializedName("Currency")
	val currency: String? = null,

	@field:SerializedName("TotalPaid")
	val totalPaid: Double? = null,

	@field:SerializedName("BudgetView")
	val budgetView: Any? = null,

	@field:SerializedName("DestinationName")
	val destinationName: String? = null,

	@field:SerializedName("TotalBudget")
	val totalBudget: Double? = null,

	@field:SerializedName("Status")
	val status: Int? = null,

	@field:SerializedName("IsCreatedSettlement")
	val isCreatedSettlement: Boolean? = null,

	@field:SerializedName("TripAirlines")
	val tripAirlines: List<Any?>? = null,

	@field:SerializedName("TripParticipants")
	val tripParticipants: List<TripParticipantsItem?>? = null,

	@field:SerializedName("CreatedBy")
	val createdBy: String? = null,

	@field:SerializedName("IsRequestingChange")
	val isRequestingChange: Boolean? = null,

	@field:SerializedName("CreationDateRelativeView")
	val creationDateRelativeView: String? = null,

	@field:SerializedName("Routes")
	val routes: List<RoutesItem?>? = null,

	@field:SerializedName("ReffIdHotel")
	val reffIdHotel: Any? = null,

	@field:SerializedName("PartnerName")
	val partnerName: String? = null,

	@field:SerializedName("Code")
	val code: String? = null,

	@field:SerializedName("CreationDateString")
	val creationDateString: String? = null,

	@field:SerializedName("SubmitedDate")
	val submitedDate: String? = null,

	@field:SerializedName("TripDateView")
	val tripDateView: String? = null,

	@field:SerializedName("WbsNo")
	val wbsNo: String? = null,

	@field:SerializedName("ApproveDate")
	val approveDate: Any? = null,

	@field:SerializedName("TripHotels")
	val tripHotels: List<Any?>? = null,

	@field:SerializedName("TripExpenditures")
	val tripExpenditures: Any? = null,

	@field:SerializedName("DestinationView")
	val destinationView: String? = null,

	@field:SerializedName("TimeLimitShorted")
	val timeLimitShorted: String? = null,

	@field:SerializedName("IsMultiTripForm")
	val isMultiTripForm: Boolean? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("TripHistories")
	val tripHistories: List<Any?>? = null,

	@field:SerializedName("BudgetDesc")
	val budgetDesc: Any? = null,

	@field:SerializedName("Trains")
	val trains: List<Any?>? = null,

	@field:SerializedName("TripClaims")
	val tripClaims: List<Any?>? = null,

	@field:SerializedName("LastModifiedBy")
	val lastModifiedBy: String? = null,

	@field:SerializedName("WithPartner")
	val withPartner: Boolean = false,

	@field:SerializedName("GolperTitle")
	val golperTitle: String? = null,

	@field:SerializedName("IsCompletedSettlement")
	val isCompletedSettlement: Boolean? = null,

	@field:SerializedName("TripApprovals")
	val tripApprovals: List<Any?>? = null,

	@field:SerializedName("StatusView")
	val statusView: String? = null,

	@field:SerializedName("RemarkForBNI")
	val remarkForBNI: String? = null,

	@field:SerializedName("Golper")
	val golper: Int? = null,

	@field:SerializedName("CoverLetterUrl")
	val coverLetterUrl: Any? = null,

	@field:SerializedName("ExpiredIn")
	val expiredIn: String? = null,

	@field:SerializedName("TripCodeOld")
	val tripCodeOld: String? = null,

	@field:SerializedName("TimeLimit")
	val timeLimit: String? = null,

	@field:SerializedName("ReturnDate")
	val returnDate: String? = null,

	@field:SerializedName("NonCbt")
	val nonCbt: Boolean = false,

	@field:SerializedName("TripTrains")
	val tripTrains: List<Any?>? = null,

	@field:SerializedName("FlightEstimatedCostView")
	val flightEstimatedCostView: String? = null,

	@field:SerializedName("ReturnDateView")
	val returnDateView: String? = null,

	@field:SerializedName("OriginName")
	val originName: String? = null,

	@field:SerializedName("Type")
	val type: Int? = null,

	@field:SerializedName("PolicyComplianceStatus")
	val policyComplianceStatus: Int? = null,

	@field:SerializedName("PccId")
	val pccId: String? = null,

	@field:SerializedName("HotelEstimatedCost")
	val hotelEstimatedCost: Double? = null,

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
	val businessTripType: String? = null,

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
	val trainEstimatedCost: Double? = null,

	@field:SerializedName("IsPersonalTrip")
	val isPersonalTrip: Boolean = false,

	@field:SerializedName("TravelAgentUrl")
	val travelAgentUrl: String? = null,

	@field:SerializedName("TimeLimitString")
	val timeLimitString: String? = null,

	@field:SerializedName("TripBudgetComponents")
	val tripBudgetComponents: Any? = null,

	@field:SerializedName("TrnNumber")
	val trnNumber: String? = null,

	@field:SerializedName("IsFromMobile")
	val isFromMobile: Boolean? = null,

	@field:SerializedName("PaymentStatus")
	val paymentStatus: Int? = null,

	@field:SerializedName("TotalExpenditureHotel")
	val totalExpenditureHotel: Double? = null,

	@field:SerializedName("DurationDay")
	val durationDay: Double? = null,

	@field:SerializedName("OriRoutes")
	val oriRoutes: Any? = null,

	@field:SerializedName("IsWithoutSPJ")
	val isWithoutSPJ: Boolean? = null,

	@field:SerializedName("BudgetId")
	val budgetId: String? = null,

	@field:SerializedName("EstimatedCost")
	val estimatedCost: Double? = null,

	@field:SerializedName("LastGeneratedToC4")
	val lastGeneratedToC4: String? = null,

	@field:SerializedName("OtherPurpose")
	val otherPurpose: Any? = null,

	@field:SerializedName("ReasonCode")
	val reasonCode: Any? = null,

	@field:SerializedName("PostTripClaims")
	val postTripClaims: List<Any?>? = null,

	@field:SerializedName("FlightEstimatedCost")
	val flightEstimatedCost: Double? = null,

	@field:SerializedName("TripIdOld")
	val tripIdOld: String? = null,

	@field:SerializedName("TripAttachments")
	val tripAttachments: List<TripAttachmentsItem?>? = null,

	@field:SerializedName("Destination")
	val destination: String? = null,

	@field:SerializedName("CityNameApi")
	val cityNameApi: Any? = null,

	@field:SerializedName("TotalExpenditure")
	val totalExpenditure: Double? = null,

	@field:SerializedName("TransportationType")
	val transportationType: Any? = null,

	@field:SerializedName("SubmitDateView")
	val submitDateView: String? = null,

	@field:SerializedName("PurposeCode")
	val purposeCode: Any? = null,

	@field:SerializedName("isApproval")
	val isApproval: Boolean = false,

	@field:SerializedName("IsHavePayment")
	val isHavePayment: Boolean? = null,

	@field:SerializedName("CostCenter")
	val costCenter: Any? = null,

	@field:SerializedName("Flights")
	val flights: List<Any?>? = null,

	@field:SerializedName("CreationDateView")
	val creationDateView: String? = null,

	@field:SerializedName("RemarkSKPD")
	val remarkSKPD: Any? = null,

	@field:SerializedName("PaymentType")
	val paymentType: Int? = null,

	@field:SerializedName("StartDateView")
	val startDateView: String? = null,

	@field:SerializedName("PolicyCause")
	val policyCause: Any? = null,

	@field:SerializedName("TotalExpenditureTrain")
	val totalExpenditureTrain: Double? = null,

	@field:SerializedName("TrainEstimatedCostView")
	val trainEstimatedCostView: String? = null,

	@field:SerializedName("Purpose")
	val purpose: String? = null,

	@field:SerializedName("PolicyReasonCode")
	val policyReasonCode: Int? = null,

	@field:SerializedName("ApprovalCode")
	val approvalCode: Any? = null,

	@field:SerializedName("CostCenterId")
	val costCenterId: Any? = null,

	@field:SerializedName("IsGeneratedToC4")
	val isGeneratedToC4: Boolean? = null,

	@field:SerializedName("PartnerFirstName")
	val partnerFirstName: String? = null,

	@field:SerializedName("Contact")
	val contact: Contact? = null,

	@field:SerializedName("SettlementId")
	val settlementId: String? = null,

	@field:SerializedName("DueDateApproval")
	val dueDateApproval: Any? = null,

	@field:SerializedName("TripItems")
	val tripItems: List<Any?>? = null,

	@field:SerializedName("IsChangeTrip")
	val isChangeTrip: Boolean = false,

	@field:SerializedName("PaymentStatusView")
	val paymentStatusView: String? = null,

	@field:SerializedName("SubmitDateString")
	val submitDateString: String? = null
)

data class TripAttachmentsItem(

	@field:SerializedName("TripPlanId")
	val tripPlanId: String? = null,

	@field:SerializedName("Description")
	val description: String? = null,

	@field:SerializedName("HasScanned")
	val hasScanned: Boolean? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("Info")
	val info: Any? = null,

	@field:SerializedName("Url")
	val url: String? = null
)
