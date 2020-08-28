package opsigo.com.datalayer.request_model.create_trip_plane

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class TripParticipantsItem(

	@field:SerializedName("BudgetId")
	var budgetId: String = "",

	@field:SerializedName("EmployeeId")
	var employeeId: String = "",

	@field:SerializedName("CostCenterId")
	var costCenterId: String = ""
)