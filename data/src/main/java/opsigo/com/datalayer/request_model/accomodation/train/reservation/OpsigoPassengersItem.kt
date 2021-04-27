package opsigo.com.datalayer.request_model.accomodation.train.reservation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class OpsigoPassengersItem(

		@field:SerializedName("IdentityType")
		var identityType: String = "",

		@field:SerializedName("OtherPhone")
		var otherPhone: String = "",

		@field:SerializedName("Email")
		var email: String = "",

		@field:SerializedName("FirstName")
		var firstName: String = "",

		@field:SerializedName("IdNumber")
		var idNumber: String = "",

		@field:SerializedName("EmployeeNik")
		var employeeNik: String = "",

		@field:SerializedName("Title")
		var title: String = "",

		@field:SerializedName("Index")
		var index: Int = 0,

		@field:SerializedName("MobilePhone")
		var mobilePhone: String = "",

		@field:SerializedName("RemarksPax")
		var remarksPax: List<Any> = ArrayList(),

		@field:SerializedName("JobTitleId")
		var jobTitleId: String = "",

		@field:SerializedName("Type")
		var type: Int = 1,

		@field:SerializedName("HomePhone")
		var homePhone: String = "",

		@field:SerializedName("PaxType")
		var paxType: String = "",

		@field:SerializedName("LastName")
		var lastName: String = "",

		@field:SerializedName("EmployeeId")
		var employeeId: String = "",

		@field:SerializedName("CompanyCode")
		var companyCode: String = "",

		@field:SerializedName("BirthDate")
		var birthDate: String = "",

		@field:SerializedName("IdentityNumber")
		var identityNumber: String = ""
)