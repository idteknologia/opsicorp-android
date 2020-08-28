package opsigo.com.datalayer.model

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class Contact(

	@field:SerializedName("Company")
	val company: String = "",

	@field:SerializedName("Email")
	val email: String = "",

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
	val id: String = "",

	@field:SerializedName("LastName")
	val lastName: String? = null,

	@field:SerializedName("NikEmployee")
	val nikEmployee: Any? = null,

	@field:SerializedName("TripFlightId")
	val tripFlightId: String? = null,

	@field:SerializedName("EmployeeId")
	val employeeId: String = "",

	@field:SerializedName("CompanyCode")
	val companyCode: Any? = null,

	@field:SerializedName("BirthDate")
	val birthDate: Any? = null
)