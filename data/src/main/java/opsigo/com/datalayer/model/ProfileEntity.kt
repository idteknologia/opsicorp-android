package opsigo.com.datalayer.model

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ProfileEntity(

	@field:SerializedName("Username")
	val username: String = "",

	@field:SerializedName("ImageUrl")
	val imageUrl: String = "",

	@field:SerializedName("IsApprover")
	val IsApprover: Boolean = false,

	@field:SerializedName("CompanyCode")
	val companyCode: String = "",

	@field:SerializedName("DivisionCode")
	val divisionCode: String = "",

	@field:SerializedName("FullName")
	val fullName: String = "",

	@field:SerializedName("FirstName")
	val firstName: String = "",

	@field:SerializedName("MiddleName")
	val middleName: String = "",

	@field:SerializedName("LastName")
	val lastName: String = "",

	@field:SerializedName("HomePhone")
	val homePhone: String = "",

	@field:SerializedName("MobilePhone")
	val mobilePhone: String = "",

	@field:SerializedName("Nationality")
	val nationality: String = "",

	@field:SerializedName("NationalityName")
	val nationalityName: String = "",

	@field:SerializedName("EmployeeId")
	val employeeId: String = "",

	@field:SerializedName("NikEmployee")
	val nikEmployee: String = "",

	@field:SerializedName("CompanyName")
	val companyName: String = "",

	@field:SerializedName("DivisionName")
	val divisionName: String = "",

	@field:SerializedName("JobTitleId")
	val jobTitleId: String = "",

	@field:SerializedName("JobTitleName")
	val jobTitleName: String = "",

	@field:SerializedName("BirthDate")
	val birthDate: String = "",

	@field:SerializedName("CompanyType")
	val companyType: String = "",

	@field:SerializedName("Email")
	val email: String = "",

	@field:SerializedName("IsDivision")
	val isDivision: Boolean? = null,

	@field:SerializedName("Title")
	val title: String = "",

	@field:SerializedName("IsAdmin")
	val isAdmin: Boolean? = null,

	@field:SerializedName("TenantId")
	val tenantId: Any? = null,

	@field:SerializedName("DeviceId")
	val deviceId: String = "",

	@field:SerializedName("LoginSessionId")
	val loginSessionId: String = "",

	@field:SerializedName("DelegateUsers")
	val delegateUsers: List<Any?>? = null,

	@field:SerializedName("SignedUserIdOutlook")
	val signedUserIdOutlook: Any? = null,

	@field:SerializedName("TokenOutlook")
	val tokenOutlook: Any? = null,

	@field:SerializedName("IdNumber")
	val idNumber: String = "",

	@field:SerializedName("SimNumber")
	val simNumber: String = "",

	@field:SerializedName("PassportNumber")
	val passportNumber: String = "",

	@field:SerializedName("IdentityType")
	val identityType: String = "",

	@field:SerializedName("IsSuperAdmin")
	val isSuperAdmin: Boolean? = null

)