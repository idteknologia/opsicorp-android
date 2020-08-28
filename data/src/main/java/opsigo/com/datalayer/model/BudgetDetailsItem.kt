package opsigo.com.datalayer.model

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class BudgetDetailsItem(

	@field:SerializedName("Type")
	val type: String = "",

	@field:SerializedName("TypeName")
	val typeName: String = "",

	@field:SerializedName("Description")
	val description: String = "",

	@field:SerializedName("Amount")
	val amount: String = "",

	@field:SerializedName("Id")
	val id: String = "",

	@field:SerializedName("CurrentAmount")
	val currentAmount: String = "",

	@field:SerializedName("Code")
	val code: String = "",

	@field:SerializedName("TravelAgentName")
	val travelAgentName: String = "",

	@field:SerializedName("Seq")
	val seq: String = "",

	@field:SerializedName("Name")
	val name: String = "",

	@field:SerializedName("TravelAgentCode")
	val travelAgentCode: String = ""
)