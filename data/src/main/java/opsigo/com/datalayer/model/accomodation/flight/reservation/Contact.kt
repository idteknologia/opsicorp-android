package opsigo.com.datalayer.model.accomodation.flight.reservation

import com.google.gson.annotations.SerializedName

data class Contact(

	@field:SerializedName("Company")
	val company: Any? = null,

	@field:SerializedName("Email")
	val email: Any? = null,

	@field:SerializedName("JobTitleName")
	val jobTitleName: Any? = null,

	@field:SerializedName("FirstName")
	val firstName: Any? = null,

	@field:SerializedName("Title")
	val title: Any? = null,

	@field:SerializedName("ParentCompanyName")
	val parentCompanyName: Any? = null,

	@field:SerializedName("JobTitle")
	val jobTitle: Any? = null,

	@field:SerializedName("MobilePhone")
	val mobilePhone: Any? = null,

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
	val lastName: Any? = null,

	@field:SerializedName("NikEmployee")
	val nikEmployee: Any? = null,

	@field:SerializedName("TripFlightId")
	val tripFlightId: String? = null,

	@field:SerializedName("CompanyCode")
	val companyCode: Any? = null,

	@field:SerializedName("BirthDate")
	val birthDate: Any? = null
)