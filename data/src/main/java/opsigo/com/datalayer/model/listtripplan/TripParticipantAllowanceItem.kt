package opsigo.com.datalayer.model.listtripplan

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class TripParticipantAllowanceItem(

	@field:SerializedName("AllowanceItem")
	val allowanceItem: String = "",

	@field:SerializedName("Type")
	val type: String = "",

	@field:SerializedName("AllowanceItemName")
	val allowanceItemName: String = "",

	@field:SerializedName("Amount")
	val amount: String = "",

	@field:SerializedName("Total")
	val total: String = "",

	@field:SerializedName("Id")
	val id: String = "",

	@field:SerializedName("TripParticipantId")
	val tripParticipantId: String = ""
)