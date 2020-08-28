package opsigo.com.datalayer.model.accomodation.flight.validation

import com.google.gson.annotations.SerializedName

data class PassengersItem(

	@field:SerializedName("OtherPhone")
	val otherPhone: Any? = null,

	@field:SerializedName("Email")
	val email: String? = null,

	@field:SerializedName("PassportExpire")
	val passportExpire: String? = null,

	@field:SerializedName("IdNumber")
	val idNumber: String? = null,

	@field:SerializedName("EmployeeNik")
	val employeeNik: String? = null,

	@field:SerializedName("Index")
	val index: Int? = null,

	@field:SerializedName("FrequentFlyer")
	val frequentFlyer: Any? = null,

	@field:SerializedName("TripTrainId")
	val tripTrainId: String? = null,

	@field:SerializedName("MobilePhone")
	val mobilePhone: String? = null,

	@field:SerializedName("RemarksPax")
	val remarksPax: List<Any?>? = null,

	@field:SerializedName("JobTitleId")
	val jobTitleId: String? = null,

	@field:SerializedName("Passport")
	val passport: Passport? = null,

	@field:SerializedName("PassportOrigin")
	val passportOrigin: String? = null,

	@field:SerializedName("Remarks")
	val remarks: Any? = null,

	@field:SerializedName("TicketNumber")
	val ticketNumber: Any? = null,

	@field:SerializedName("SeatText")
	val seatText: String? = null,

	@field:SerializedName("SegmentBaggages")
	val segmentBaggages: List<Any?>? = null,

	@field:SerializedName("PaxType")
	val paxType: Any? = null,

	@field:SerializedName("EmployeeId")
	val employeeId: String? = null,

	@field:SerializedName("IdentityType")
	val identityType: Any? = null,

	@field:SerializedName("RetSsr")
	val retSsr: List<Any?>? = null,

	@field:SerializedName("FirstName")
	val firstName: String? = null,

	@field:SerializedName("BudgetId")
	val budgetId: Any? = null,

	@field:SerializedName("Ssrs")
	val ssrs: List<Any?>? = null,

	@field:SerializedName("FrequentFlyers")
	val frequentFlyers: List<Any?>? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("Sequence")
	val sequence: Any? = null,

	@field:SerializedName("PassportFirstName")
	val passportFirstName: Any? = null,

	@field:SerializedName("Nationality")
	val nationality: String? = null,

	@field:SerializedName("CostCenterId")
	val costCenterId: Any? = null,

	@field:SerializedName("DepSsr")
	val depSsr: List<Any?>? = null,

	@field:SerializedName("Seats")
	val seats: List<Any?>? = null,

	@field:SerializedName("Type")
	val type: String? = null,

	@field:SerializedName("IsSeniorCitizen")
	val isSeniorCitizen: Boolean? = null,

	@field:SerializedName("SeatName")
	val seatName: Any? = null,

	@field:SerializedName("HomePhone")
	val homePhone: String? = null,

	@field:SerializedName("PassportNumber")
	val passportNumber: String? = null,

	@field:SerializedName("PassportLastName")
	val passportLastName: Any? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("LastName")
	val lastName: String? = null,

	@field:SerializedName("AdultAssoc")
	val adultAssoc: Any? = null,

	@field:SerializedName("SeatNumber")
	val seatNumber: Any? = null,

	@field:SerializedName("TripFlightId")
	val tripFlightId: String? = null,

	@field:SerializedName("CompanyCode")
	val companyCode: String? = null,

	@field:SerializedName("BirthDate")
	val birthDate: String? = null,

	@field:SerializedName("IdentityNumber")
	val identityNumber: Any? = null
)