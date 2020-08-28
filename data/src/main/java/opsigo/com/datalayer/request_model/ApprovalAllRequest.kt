package opsigo.com.datalayer.request_model

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ApprovalAllRequest(

	@field:SerializedName("ApprovalAction")
	var approvalAction: String = "",

	@field:SerializedName("EmployeeId")
	var employeeId: String = "",

	@field:SerializedName("TripId")
	var tripId: String = ""
)