package opsigo.com.datalayer.model.accomodation.flight.reservation

import com.google.gson.annotations.SerializedName

data class PassengersItem(

	@field:SerializedName("OtherPhone")
	val otherPhone: String = "",

	@field:SerializedName("Email")
	val email: String = "",

	@field:SerializedName("PassportExpire")
	val passportExpire: String = "",

	@field:SerializedName("IdNumber")
	val idNumber: String = "",

	@field:SerializedName("EmployeeNik")
	val employeeNik: String = "",

	@field:SerializedName("Index")
	val index: Int = 0,

	@field:SerializedName("FrequentFlyer")
	val frequentFlyer: String = "",

	@field:SerializedName("TripTrainId")
	val tripTrainId: String = "",

	@field:SerializedName("MobilePhone")
	val mobilePhone: String = "",

	@field:SerializedName("RemarksPax")
	val remarksPax: List<RemarksPaxItem?>? = null,

	@field:SerializedName("JobTitleId")
	val jobTitleId: String = "",

	@field:SerializedName("Passport")
	val passport: Passport? = null,

	@field:SerializedName("PassportOrigin")
	val passportOrigin: String = "",

	@field:SerializedName("Remarks")
	val remarks: String = "",

	@field:SerializedName("TicketNumber")
	val ticketNumber: String = "",

	@field:SerializedName("SeatText")
	val seatText: String = "",

	@field:SerializedName("SegmentBaggages")
	val segmentBaggages: List<Any?>? = null,

	@field:SerializedName("PaxType")
	val paxType: String = "",

	@field:SerializedName("EmployeeId")
	val employeeId: String = "",

	@field:SerializedName("IdentityType")
	val identityType: String = "",

	@field:SerializedName("RetSsr")
	val retSsr: List<Any?>? = null,

	@field:SerializedName("FirstName")
	val firstName: String = "",

	@field:SerializedName("BudgetId")
	val budgetId: String = "",

	@field:SerializedName("Ssrs")
	val ssrs: List<Any?>? = null,

	@field:SerializedName("FrequentFlyers")
	val frequentFlyers: List<Any?>? = null,

	@field:SerializedName("Title")
	val title: String = "",

	@field:SerializedName("Sequence")
	val sequence: String = "",

	@field:SerializedName("PassportFirstName")
	val passportFirstName: String = "",

	@field:SerializedName("Nationality")
	val nationality: String = "",

	@field:SerializedName("CostCenterId")
	val costCenterId: String = "",

	@field:SerializedName("DepSsr")
	val depSsr: List<Any?>? = null,

	@field:SerializedName("Seats")
	val seats: List<Any?>? = null,

	@field:SerializedName("Type")
	val type: String = "",

	@field:SerializedName("IsSeniorCitizen")
	val isSeniorCitizen: Boolean? = null,

	@field:SerializedName("SeatName")
	val seatName: String = "",

	@field:SerializedName("HomePhone")
	val homePhone: String = "",

	@field:SerializedName("PassportNumber")
	val passportNumber: String = "",

	@field:SerializedName("PassportLastName")
	val passportLastName: String = "",

	@field:SerializedName("Id")
	val id: String = "",

	@field:SerializedName("LastName")
	val lastName: String = "",

	@field:SerializedName("AdultAssoc")
	val adultAssoc: String = "",

	@field:SerializedName("SeatNumber")
	val seatNumber: String = "",

	@field:SerializedName("TripFlightId")
	val tripFlightId: String = "",

	@field:SerializedName("CompanyCode")
	val companyCode: String = "",

	@field:SerializedName("BirthDate")
	val birthDate: String = "",

	@field:SerializedName("IdentityNumber")
	val identityNumber: String = ""
)