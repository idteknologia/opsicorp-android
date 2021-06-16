package opsigo.com.datalayer.request_model.reservation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class TripParticipantsItem(

	@field:SerializedName("BudgetId")
	var budgetId: String? = null,

	@field:SerializedName("EmployeeId")
	var employeeId: String? = null,

	@field:SerializedName("CostCenterId")
    var costCenterId: String? = null
)