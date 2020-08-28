package opsigo.com.datalayer.model

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class BudgetEntity(

		@field:SerializedName("Type")
	val type: String = "",

		@field:SerializedName("TypeName")
	val typeName: String = "",

		@field:SerializedName("BudgetDetails")
	val budgetDetails: List<BudgetDetailsItem> = ArrayList<BudgetDetailsItem>(),

		@field:SerializedName("Seq")
	val seq: String = ""
)