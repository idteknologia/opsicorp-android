package opsigo.com.datalayer.model.profile

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ProfileEntity(

	@field:SerializedName("Username")
	val username: String?=null,

	@field:SerializedName("ImageUrl")
	val imageUrl: String?=null,

	@field:SerializedName("IsApprover")
	val IsApprover: Boolean = false,

	@field:SerializedName("CompanyCode")
	val companyCode: String?=null,

	@field:SerializedName("DivisionCode")
	val divisionCode: String?=null,

	@field:SerializedName("FullName")
	val fullName: String?=null,

	@field:SerializedName("FirstName")
	val firstName: String?=null,

	@field:SerializedName("MiddleName")
	val middleName: String?=null,

	@field:SerializedName("LastName")
	val lastName: String?=null,

	@field:SerializedName("HomePhone")
	val homePhone: String?=null,

	@field:SerializedName("MobilePhone")
	val mobilePhone: String?=null,

	@field:SerializedName("Nationality")
	val nationality: String?=null,

	@field:SerializedName("NationalityName")
	val nationalityName: String?=null,

	@field:SerializedName("EmployeeId")
	val employeeId: String?=null,

	@field:SerializedName("NikEmployee")
	val nikEmployee: String?=null,

	@field:SerializedName("CompanyName")
	val companyName: String?=null,

	@field:SerializedName("DivisionName")
	val divisionName: String?=null,

	@field:SerializedName("JobTitleId")
	val jobTitleId: String?=null,

	@field:SerializedName("JobTitleName")
	val jobTitleName: String?=null,

	@field:SerializedName("BirthDate")
	val birthDate: String?=null,

	@field:SerializedName("CompanyType")
	val companyType: String?=null,

	@field:SerializedName("Email")
	val email: String?=null,

	@field:SerializedName("IsDivision")
	val isDivision: Boolean? = null,

	@field:SerializedName("Title")
	val title: String?=null,

	@field:SerializedName("IsAdmin")
	val isAdmin: Boolean? = null,

	@field:SerializedName("TenantId")
	val tenantId: Any? = null,

	@field:SerializedName("DeviceId")
	val deviceId: String?=null,

	@field:SerializedName("LoginSessionId")
	val loginSessionId: String?=null,

	@field:SerializedName("DelegateUsers")
	val delegateUsers: List<Any?>? = null,

	@field:SerializedName("SignedUserIdOutlook")
	val signedUserIdOutlook: Any? = null,

	@field:SerializedName("TokenOutlook")
	val tokenOutlook: Any? = null,

	@field:SerializedName("IdNumber")
	val idNumber: String?=null,

	@field:SerializedName("SimNumber")
	val simNumber: String?=null,

	@field:SerializedName("PassportNumber")
	val passportNumber: String?=null,

	@field:SerializedName("IdentityType")
	val identityType: String?=null,

	@field:SerializedName("IsSuperAdmin")
	val isSuperAdmin: Boolean? = null

)