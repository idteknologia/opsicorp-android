package opsigo.com.datalayer.model.profile

import com.google.gson.annotations.SerializedName

data class ProfileNewEntity(

	@field:SerializedName("IsApprover")
	val isApprover: Boolean = false,

	@field:SerializedName("IsAdmin")
	val isAdmin: Boolean? = null,

	@field:SerializedName("Email")
	val email: String? = null,

	@field:SerializedName("IdNumber")
	val idNumber: Any? = null,

	@field:SerializedName("MobilePhone")
	val mobilePhone: String? = null,

	@field:SerializedName("CompanyName")
	val companyName: String? = null,

	@field:SerializedName("ExternalUsername")
	val externalUsername: Any? = null,

	@field:SerializedName("Approval")
	val approval: Approval? = null,

	@field:SerializedName("DelegateUsers")
	val delegateUsers: List<Any?>? = null,

	@field:SerializedName("LoginSessionId")
	val loginSessionId: String? = null,

	@field:SerializedName("IdentityType")
	val identityType: String? = null,

	@field:SerializedName("IsExecutive")
	val isExecutive: Boolean? = null,

	@field:SerializedName("JobTitleName")
	val jobTitleName: String? = null,

	@field:SerializedName("SignedUserIdOutlook")
	val signedUserIdOutlook: Any? = null,

	@field:SerializedName("ImageUrl")
	val imageUrl: Any? = null,

	@field:SerializedName("CompanyType")
	val companyType: Int? = null,

	@field:SerializedName("IsAdminVoid")
	val isAdminVoid: Boolean? = null,

	@field:SerializedName("SimNumber")
	val simNumber: String? = null,

	@field:SerializedName("NationalityName")
	val nationalityName: String? = null,

	@field:SerializedName("PassportNumber")
	val passportNumber: String? = null,

	@field:SerializedName("LastName")
	val lastName: String? = null,

	@field:SerializedName("BirthDate")
	val birthDate: String? = null,

	@field:SerializedName("CostCenterDefaultText")
	val costCenterDefaultText: String? = null,

	@field:SerializedName("DeviceId")
	val deviceId: String? = null,

	@field:SerializedName("DivisionCode")
	val divisionCode: String? = null,

	@field:SerializedName("IsLoginBySso")
	val isLoginBySso: Boolean? = null,

	@field:SerializedName("JobTitleId")
	val jobTitleId: String? = null,

	@field:SerializedName("IsDivision")
	val isDivision: Boolean? = null,

	@field:SerializedName("GolperInternational")
	val golperInternational: String? = null,

	@field:SerializedName("GolperDomestic")
	val golperDomestic: String? = null,

	@field:SerializedName("NikEmployee")
	val nikEmployee: String? = null,

	@field:SerializedName("TokenOutlookExpires")
	val tokenOutlookExpires: String? = null,

	@field:SerializedName("EmployeeId")
	val employeeId: String? = null,

	@field:SerializedName("Age")
	val age: Double? = null,

	@field:SerializedName("IsSuperAdmin")
	val isSuperAdmin: Boolean? = null,

	@field:SerializedName("DivisionName")
	val divisionName: String? = null,

	@field:SerializedName("FirstName")
	val firstName: String? = null,

	@field:SerializedName("PositionId")
	val positionId: String? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("MiddleName")
	val middleName: Any? = null,

	@field:SerializedName("Nationality")
	val nationality: String? = null,

	@field:SerializedName("TenantId")
	val tenantId: Any? = null,

	@field:SerializedName("Username")
	val username: String? = null,

	@field:SerializedName("HomePhone")
	val homePhone: String? = null,

	@field:SerializedName("JobTitleLevel")
	val jobTitleLevel: Int? = null,

	@field:SerializedName("UserId")
	val userId: String? = null,

	@field:SerializedName("FullName")
	val fullName: String? = null,

	@field:SerializedName("TokenOutlook")
	val tokenOutlook: Any? = null,

	@field:SerializedName("CostCenterDefault")
	val costCenterDefault: String? = null,

	@field:SerializedName("CompanyCode")
	val companyCode: String? = null
)

data class Approval(

	@field:SerializedName("RequestorPersonId")
	val requestorPersonId: String? = null,

	@field:SerializedName("TravelRequestApprovalDomestic")
	val travelRequestApprovalDomestic: List<TravelRequestApprovalDomesticItem?>? = null,

	@field:SerializedName("RequestorPositionName")
	val requestorPositionName: String? = null,

	@field:SerializedName("ManualnputDomestic")
	val manualnputDomestic: List<Any?>? = null,

	@field:SerializedName("EnableInputApprovalInternational")
	val enableInputApprovalInternational: Boolean? = null,

	@field:SerializedName("RequestorEmail")
	val requestorEmail: String? = null,

	@field:SerializedName("RequestorPositionId")
	val requestorPositionId: String? = null,

	@field:SerializedName("ManualnputInternational")
	val manualnputInternational: List<Any?>? = null,

	@field:SerializedName("TravelRequestApprovalInternational")
	val travelRequestApprovalInternational: List<TravelRequestApprovalInternationalItem?>? = null,

	@field:SerializedName("EnableInputApprovalDomestic")
	val enableInputApprovalDomestic: Boolean? = null,

	@field:SerializedName("SettlementApprovalInternational")
	val settlementApprovalInternational: List<SettlementApprovalInternationalItem?>? = null,

	@field:SerializedName("RequestorName")
	val requestorName: String? = null,

	@field:SerializedName("SettlementApprovalDomestic")
	val settlementApprovalDomestic: List<SettlementApprovalDomesticItem?>? = null
)

data class TravelRequestApprovalDomesticItem(

	@field:SerializedName("IsManualInput")
	val isManualInput: Boolean? = null,

	@field:SerializedName("IsPjs")
	val isPjs: Boolean = false,

	@field:SerializedName("EmployeeId")
	val employeeId: String? = null,

	@field:SerializedName("layer")
	val layer: Int? = null,

	@field:SerializedName("Profile")
	val profile: Profile? = null
)

data class TravelRequestApprovalInternationalItem(

	@field:SerializedName("IsManualInput")
	val isManualInput: Boolean? = null,

	@field:SerializedName("IsPjs")
	val isPjs: Boolean = false,

	@field:SerializedName("EmployeeId")
	val employeeId: String? = null,

	@field:SerializedName("layer")
	val layer: Int? = null,

	@field:SerializedName("Profile")
	val profile: Profile? = null
)

data class SettlementApprovalInternationalItem(

	@field:SerializedName("IsManualInput")
	val isManualInput: Boolean? = null,

	@field:SerializedName("IsPjs")
	val isPjs: Boolean? = null,

	@field:SerializedName("EmployeeId")
	val employeeId: String? = null,

	@field:SerializedName("layer")
	val layer: Int? = null,

	@field:SerializedName("Profile")
	val profile: Profile? = null
)

data class SettlementApprovalDomesticItem(

	@field:SerializedName("IsManualInput")
	val isManualInput: Boolean? = null,

	@field:SerializedName("IsPjs")
	val isPjs: Boolean? = null,

	@field:SerializedName("EmployeeId")
	val employeeId: String? = null,

	@field:SerializedName("layer")
	val layer: Int? = null,

	@field:SerializedName("Profile")
	val profile: Profile? = null
)

data class Profile(

	@field:SerializedName("FunctionID")
	val functionID: Int? = null,

	@field:SerializedName("PersonalSubareaText")
	val personalSubareaText: String? = null,

	@field:SerializedName("Email")
	val email: String? = null,

	@field:SerializedName("CostCenterText")
	val costCenterText: String? = null,

	@field:SerializedName("PositionText")
	val positionText: String? = null,

	@field:SerializedName("DirectorateID")
	val directorateID: Int? = null,

	@field:SerializedName("PositionID")
	val positionID: Int? = null,

	@field:SerializedName("DirectorateText")
	val directorateText: String? = null,

	@field:SerializedName("Name")
	val name: String? = null,

	@field:SerializedName("GolPerDomestic")
	val golPerDomestic: String? = null,

	@field:SerializedName("PersonalSubarea")
	val personalSubarea: String? = null,

	@field:SerializedName("FunctionText")
	val functionText: String? = null,

	@field:SerializedName("GolPerInternational")
	val golPerInternational: String? = null,

	@field:SerializedName("PersonID")
	val personID: Int? = null,

	@field:SerializedName("CostCenter")
	val costCenter: String? = null,

	@field:SerializedName("PersonalAreaText")
	val personalAreaText: String? = null,

	@field:SerializedName("CompanyText")
	val companyText: String? = null,

	@field:SerializedName("AssignmentNumber")
	val assignmentNumber: Int? = null,

	@field:SerializedName("KBO")
	val kBO: String? = null,

	@field:SerializedName("PersonalArea")
	val personalArea: String? = null,

	@field:SerializedName("CompanyCode")
	val companyCode: String? = null,

	@field:SerializedName("Age")
	val age: Double? = null
)
