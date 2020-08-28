package opsigo.com.datalayer.model.approval.list_approval

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ListParticipantItem(

	@field:SerializedName("MidleName")
	val midleName: String = "",

	@field:SerializedName("FirstName")
	val firstName: String = "",

	@field:SerializedName("EmployeeNik")
	val employeeNik: String = "",

	@field:SerializedName("Title")
	val title: String = "",

	@field:SerializedName("LastName")
	val lastName: String = ""
)