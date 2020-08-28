package opsigo.com.datalayer.model

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class TripParticipantsItem(

		@field:SerializedName("TotalAllowance")
	val totalAllowance: Int? = null,

		@field:SerializedName("ParentCompanyName")
	val parentCompanyName: Any? = null,

		@field:SerializedName("TotalTripPaidAirline")
	val totalTripPaidAirline: String = "",

		@field:SerializedName("TripPostClaims")
	val tripPostClaims: List<Any?>? = null,

		@field:SerializedName("OtherEmail")
	val otherEmail: Any? = null,

		@field:SerializedName("TotalTripAirline")
	val totalTripAirline: Int? = null,

		@field:SerializedName("DefaultClassAirlineName")
	val defaultClassAirlineName: Any? = null,

		@field:SerializedName("CostCenterName")
		val costCenterName: String = "",

		@field:SerializedName("CostCodeAlias")
	val costCodeAlias: String? = null,

		@field:SerializedName("isDisabled")
	val isDisabled: String? = null,

		@field:SerializedName("IsShowButtonPayment")
	val isShowButtonPayment: Boolean? = null,

		@field:SerializedName("Status")
	val status: String = "",

		@field:SerializedName("TripItemTypes")
	val tripItemTypes: List<TripItemTypesItem>,

		//val tripItemTypes: List<TripItemTypesItem?>? = null,
//
//	val tripParticipants: List<TripParticipantsItem>,//? = null,
//		//val tripParticipants: List<TripParticipantsItem?>? = null,

		@field:SerializedName("ApproveDateView")
	val approveDateView: String? = null,

		@field:SerializedName("TrainBookingCodes")
	val trainBookingCodes: List<Any?>? = null,

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
	val approveDate: String? = null,

		@field:SerializedName("ApprovedBy")
	val approvedBy: Any? = null,

		@field:SerializedName("Actual")
	val actual: Int? = null,

		@field:SerializedName("PassportLastName")
	val passportLastName: Any? = null,

		@field:SerializedName("Id")
	val id: String = "",

		@field:SerializedName("BudgetName")
	val budgetName: String = "",

		@field:SerializedName("PassportMiddleName")
	val passportMiddleName: Any? = null,

		@field:SerializedName("Plafond")
	val plafond: Int? = null,

		@field:SerializedName("DefaultPlafondHotel")
	val defaultPlafondHotel: Any? = null,

		@field:SerializedName("PaymentAirlineTimeLimitView")
	val paymentAirlineTimeLimitView: String? = null,

		@field:SerializedName("InfoCompany")
	val infoCompany: String? = null,

		@field:SerializedName("StatusView")
	val statusView: String = "",

		@field:SerializedName("JobTitle")
	val jobTitle: String = "",

		@field:SerializedName("LocationName")
	val locationName: Any? = null,

		@field:SerializedName("JobTitleId")
	val jobTitleId: String? = null,

		@field:SerializedName("PassportPlace")
	val passportPlace: Any? = null,

		@field:SerializedName("BudgetCode")
	val budgetCode: String = "",

		@field:SerializedName("EmployeeId")
	val employeeId: String = "",

		@field:SerializedName("FirstDepartureDate")
	val firstDepartureDate: String? = null,

		@field:SerializedName("PassportIssuedDate")
	val passportIssuedDate: Any? = null,

		@field:SerializedName("EmployeeStr")
	val employeeStr: String? = null,

		@field:SerializedName("FirstName")
	val firstName: String? = null,

		@field:SerializedName("FollowUpPosition")
	val followUpPosition: Any? = null,

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
	val idNumber: Any? = null,

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
	val hotelBookingCodes: List<Any?>? = null,

		@field:SerializedName("CompanyName")
	val companyName: String? = null,

		@field:SerializedName("TotalTripPaidHotel")
	val totalTripPaidHotel: String = "",

		@field:SerializedName("ShowButtonApproveHotel")
	val showButtonApproveHotel: Boolean? = null,

		@field:SerializedName("IsProcurement")
	val isProcurement: Boolean? = null,

		@field:SerializedName("IsShowButtonIsued")
	val isShowButtonIsued: Boolean? = null,

		@field:SerializedName("IncludeDailyAllowance")
	val includeDailyAllowance: Boolean? = null,

		@field:SerializedName("BudgetId")
	val budgetId: String = "",

		@field:SerializedName("PaymentAirlineTimeLimit")
	val paymentAirlineTimeLimit: Any? = null,

		@field:SerializedName("ListApprover")
	val listApprover: List<String?>? = null,

		@field:SerializedName("DefaultClassAirline")
	val defaultClassAirline: Any? = null,

		@field:SerializedName("FullNameCompany")
	val fullNameCompany: String? = null,

		@field:SerializedName("DaysDailyAllowance")
	val daysDailyAllowance: Int? = null,

		@field:SerializedName("TripParticipantCustomApprovals")
	val tripParticipantCustomApprovals: ArrayList<TripParticipantCustomApprovalsItem> = ArrayList(),

		@field:SerializedName("PointOfHired")
	val pointOfHired: String? = null,

		@field:SerializedName("ShowButtonApproveAirline")
	val showButtonApproveAirline: Boolean? = null,

		@field:SerializedName("PaymentMethod")
	val paymentMethod: Any? = null,

		@field:SerializedName("LastName")
	val lastName: String? = null,

		@field:SerializedName("EmployeeName")
	val employeeName: String? = null,

		@field:SerializedName("BirthDate")
	val birthDate: String? = null,

		@field:SerializedName("IsCompletelyReviewed")
	val isCompletelyReviewed: Boolean = false,

		@field:SerializedName("FollowUpBy")
	val followUpBy: String? = null,

		@field:SerializedName("TotalTripPaidTrain")
	val totalTripPaidTrain: String = "",

		@field:SerializedName("TripPlanItems")
	val tripPlanItems: List<TripPlanItemsItem?>? = null,

		@field:SerializedName("IncludeOtherAllowance")
	val includeOtherAllowance: Boolean? = null,

		@field:SerializedName("ShowButtonApproveTrain")
	val showButtonApproveTrain: Boolean? = null,

		@field:SerializedName("PaymentInstruction")
	val paymentInstruction: Any? = null,

		@field:SerializedName("TotalTripTrain")
	val totalTripTrain: Int? = null,

		@field:SerializedName("LocationJobTitle")
	val locationJobTitle: Any? = null,

		@field:SerializedName("LastDestination")
	val lastDestination: String? = null,

		@field:SerializedName("PassportOrigin")
	val passportOrigin: Any? = null,

		@field:SerializedName("EmergencyContactEmail")
	val emergencyContactEmail: Any? = null,

		@field:SerializedName("RemarkReject")
	val remarkReject: Any? = null,

		@field:SerializedName("DaysMealAllowance")
	val daysMealAllowance: Int? = null,

		@field:SerializedName("TotalTripHotel")
	val totalTripHotel: Int? = null,

		@field:SerializedName("TripParticipantAllowance")
	val tripParticipantAllowance: List<Any?>? = null,

		@field:SerializedName("CAID")
	val cAID: Any? = null,

		@field:SerializedName("Title")
	val title: String? = null,

		@field:SerializedName("MiddleName")
	val middleName: String? = null,

		@field:SerializedName("CostCenterId")
	val costCenterId: String = "",

		@field:SerializedName("FollowUpComp")
	val followUpComp: Any? = null,

		@field:SerializedName("isApproveForm")
	val isApproveForm: Boolean = false,

		@field:SerializedName("IncludeMealAllowance")
	val includeMealAllowance: Boolean? = null,

		@field:SerializedName("FullName")
	val fullName: String,

		@field:SerializedName("CostCenterCode")
	val costCenterCode: String = "",

		@field:SerializedName("IsCostCenterUpdated")
	val isCostCenterUpdated: Boolean? = null,

		@field:SerializedName("IsHavePaymentMethodAirline")
	val isHavePaymentMethodAirline: Boolean? = null,

		@field:SerializedName("CompanyCode")
	val companyCode: Any? = null,

		@field:SerializedName("EmergencyContactName")
	val emergencyContactName: Any? = null,

		@field:SerializedName("IsAnyPNRWithNotCompleted")
	val isAnyPNRWithNotCompleted: Boolean? = null
)