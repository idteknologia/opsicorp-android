package opsigo.com.datalayer.request_model.accomodation.flight.reservation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.request_model.accomodation.flight.reservation.seat.SeatFlightRequest
import opsigo.com.datalayer.request_model.accomodation.flight.reservation.ssr.BagageFlightRequest

@Generated("com.robohorse.robopojogenerator")
data class PassangersFlightRequest(

		@field:SerializedName("RetSsr")
	var retSsr: ArrayList<BagageFlightRequest> = ArrayList(),

		@field:SerializedName("EmployeeNik")
	var employeeNik: String? = null,

		@field:SerializedName("Title")
	var title: String? = null,

		@field:SerializedName("Index")
	var index: Int? = null,

		@field:SerializedName("Seats")
	var seats: ArrayList<SeatFlightRequest> = ArrayList(),

		@field:SerializedName("DepSsr")
	var depSsr: ArrayList<BagageFlightRequest> = ArrayList(),

		@field:SerializedName("RemarksPax")
	var remarksPax: List<RemarksPaxItem?>? = null,

		@field:SerializedName("JobTitleId")
	var jobTitleId: String? = null,

		@field:SerializedName("Type")
	var type: String? = null,

		@field:SerializedName("PaxType")
	var paxType: String? = null,

		@field:SerializedName("Id")
	var id: String? = null,

		@field:SerializedName("EmployeeId")
	var employeeId: String? = null,

		@field:SerializedName("CompanyCode")
	var companyCode: String? = null,

		@field:SerializedName("LastName")
		var lastName: String? = null,

		@field:SerializedName("HomePhone")
		var homePhone: String? = null,

		@field:SerializedName("MobilePhone")
		var mobilePhone: String? = null,

		@field:SerializedName("FirstName")
		var firstName: String? = null,

		@field:SerializedName("IdNumber")
		var idNumber: String? = null,

		@field:SerializedName("IdentityType")
		var identityType: String? = null,

		@field:SerializedName("OtherPhone")
		var otherPhone: Any? = null,

		@field:SerializedName("Email")
		var email: String? = null,

		@field:SerializedName("BirthDate")
	    var birthDate: String? = null,

		@field:SerializedName("IdentityNumber")
	    var identityNumber: String? = null
)