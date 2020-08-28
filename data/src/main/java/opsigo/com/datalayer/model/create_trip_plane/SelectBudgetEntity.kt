package opsigo.com.datalayer.model.create_trip_plane

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.model.BudgetDetailsItem

@Generated("com.robohorse.robopojogenerator")
data class SelectBudgetEntity(

		@field:SerializedName("Type")
	val type: String = "",

		@field:SerializedName("TypeName")
	val typeName: String = "",

		@field:SerializedName("BudgetDetails")
	val budgetDetails: List<BudgetDetailsItem> = ArrayList<BudgetDetailsItem>(),

		@field:SerializedName("Seq")
	val seq: String = ""
)