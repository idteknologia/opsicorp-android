package opsigo.com.datalayer.model

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.model.approval.hotel.TripHotelEntity
import opsigo.com.datalayer.model.accomodation.train.TripTrainEntity
import opsigo.com.datalayer.model.create_trip_plane.save_as_daft.TripAttachmentsItem

@Generated("com.robohorse.robopojogenerator")
data class SummaryEntity(

		@field:SerializedName("Origin")
	val origin: String = "",

		@field:SerializedName("FileC4")
	val fileC4: String = "",

		@field:SerializedName("CreationDate")
	val creationDate: String = "",

		@field:SerializedName("TravelAgentAccount")
	val travelAgentAccount: String = "",

		@field:SerializedName("TimeLimitRemaining")
	val timeLimitRemaining: String = "",

		@field:SerializedName("Destinations")
	val destinations: List<String?>? = null,

		@field:SerializedName("TotalAllowance")
	val totalAllowance: String = "",

		@field:SerializedName("SharingCostCenters")
	val sharingCostCenters: List<Any?>? = null,

		@field:SerializedName("IsBookAfterApprove")
	val isBookAfterApprove: Boolean = false,

		@field:SerializedName("BudgetType")
	val budgetType: String = "",

		@field:SerializedName("TotalExpenditureAirline")
	val totalExpenditureAirline: String = "",

		@field:SerializedName("TripMembers")
	val tripMembers: List<String> = ArrayList<String>(),
//	val tripMembers: List<Any?>? = null,

		@field:SerializedName("Remark")
	val remark: String = "",

		@field:SerializedName("OriginView")
	val originView: String = "",

		@field:SerializedName("BudgetSourceName")
	val budgetSourceName: String = "",

		@field:SerializedName("StartDate")
	val startDate: String = "",

		@field:SerializedName("LastModified")
	val lastModified: String = "",

		@field:SerializedName("SeqC4")
	val seqC4: String = "",

		@field:SerializedName("IsPersonalTrip")
	val isPersonalTrip: Boolean = false,

		@field:SerializedName("Currency")
	val currency: String = "",

		@field:SerializedName("TotalPaid")
	val totalPaid: String = "",

		@field:SerializedName("BudgetView")
	val budgetView: String = "",

		@field:SerializedName("DestinationName")
	val destinationName: String = "",

		@field:SerializedName("TravelAgentUrl")
	val travelAgentUrl: String = "",

		@field:SerializedName("TripBudgetComponents")
	val tripBudgetComponents: String = "",

		@field:SerializedName("TotalBudget")
	val totalBudget: String = "",

		@field:SerializedName("TotalExpenditureHotel")
	val totalExpenditureHotel: String = "",

		@field:SerializedName("Status")
	val status: String = "",

		@field:SerializedName("TripAirlines")
	val tripAirlines: List<String> = ArrayList(),

		@field:SerializedName("TripParticipants")
	val tripParticipants: List<TripParticipantsItem> = ArrayList(),//? = null,
	//val tripParticipants: List<TripParticipantsItem?>? = null,

		@field:SerializedName("CreatedBy")
	val createdBy: String = "",

		@field:SerializedName("BudgetId")
	val budgetId: String = "",

		@field:SerializedName("CreationDateRelativeView")
	val creationDateRelativeView: String = "",

		@field:SerializedName("LastGeneratedToC4")
	val lastGeneratedToC4: String = "",

		@field:SerializedName("Routes")
	val routes: List<String> = ArrayList(),

		@field:SerializedName("ReffIdHotel")
	val reffIdHotel: String = "",

		@field:SerializedName("Code")
	val code: String = "",

		@field:SerializedName("ReasonCode")
	val reasonCode: String = "",

		@field:SerializedName("SubmitedDate")
	val submitedDate: String = "",

		@field:SerializedName("TripDateView")
	val tripDateView: String = "",

		@field:SerializedName("ApproveDate")
	val approveDate: String = "",

		@field:SerializedName("TripHotels")
	val tripHotels: List<TripHotelEntity> = ArrayList(),

		@field:SerializedName("TripExpenditures")
	val tripExpenditures: List<Any?>? = null,

		@field:SerializedName("PostTripClaims")
	val postTripClaims: List<Any?>? = null,

		@field:SerializedName("DestinationView")
	val destinationView: String = "",

		@field:SerializedName("IsMultiTripForm")
	val isMultiTripForm: Boolean = false,

		@field:SerializedName("Id")
	val id: String = "",

		@field:SerializedName("TripAttachments")
	val tripAttachments: List<TripAttachmentsItem> = ArrayList(),

		@field:SerializedName("TripHistories")
	val tripHistories: List<TripHistoriesItem> = ArrayList(),

		@field:SerializedName("BudgetDesc")
	val budgetDesc: String = "",

		@field:SerializedName("Trains")
	val trains: List<TripTrainEntity> = ArrayList(),

		@field:SerializedName("TripClaims")
	val tripClaims: List<Any?>? = null,

		@field:SerializedName("Destination")
	val destination: String = "",

		@field:SerializedName("LastModifiedBy")
	val lastModifiedBy: String = "",

		@field:SerializedName("CityNameApi")
	val cityNameApi: String = "",

		@field:SerializedName("TotalExpenditure")
	val totalExpenditure: String = "",

		@field:SerializedName("SubmitDateView")
	val submitDateView: String = "",

		@field:SerializedName("TripApprovals")
	val tripApprovals: List<Any?>? = null,

		@field:SerializedName("StatusView")
	val statusView: String = "",

		@field:SerializedName("PurposeCode")
	val purposeCode: String = "",

		@field:SerializedName("isApproval")
	val isApproval: Boolean = false,

		@field:SerializedName("ExpiredIn")
	val expiredIn: String = "",

		@field:SerializedName("CostCenter")
	val costCenter: String = "",

		@field:SerializedName("Flights")
	val flights: List<FlightsItem> = ArrayList(),

		@field:SerializedName("CreationDateView")
	val creationDateView: String = "",

		@field:SerializedName("StartDateView")
	val startDateView: String = "",

		@field:SerializedName("PolicyCause")
	val policyCause: String = "",

		@field:SerializedName("TotalExpenditureTrain")
	val totalExpenditureTrain: String = "",

		@field:SerializedName("TimeLimit")
	val timeLimit: String = "",

		@field:SerializedName("ReturnDate")
	val returnDate: String = "",

		@field:SerializedName("TripTrains")
	val tripTrains: List<Any?>? = null,

		@field:SerializedName("Purpose")
	val purpose: String = "",

		@field:SerializedName("PolicyReasonCode")
	val policyReasonCode: String = "",

		@field:SerializedName("ApprovalCode")
	val approvalCode: String = "",

		@field:SerializedName("CostCenterId")
	val costCenterId: String = "",

		@field:SerializedName("IsGeneratedToC4")
	val isGeneratedToC4: Boolean = false,

		@field:SerializedName("ContactRequest")
	val contactRequest: Contact = Contact(),

		@field:SerializedName("Contact")
		val contact: Contact = Contact(),

		@field:SerializedName("DueDateApproval")
	val dueDateApproval: String = "",

		@field:SerializedName("ReturnDateView")
	val returnDateView: String = "",

		@field:SerializedName("OriginName")
	val originName: String = "",

		@field:SerializedName("Type")
	val type: String = "",

		@field:SerializedName("TripItems")
	val tripItems: List<Any> = ArrayList(),

		@field:SerializedName("PolicyComplianceStatus")
	val policyComplianceStatus: String = "",

		@field:SerializedName("PccId")
	val pccId: String = ""
)