package opsigo.com.datalayer.model.approval.list_approval

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class DataItemListApprovalEntity(

		@field:SerializedName("Status")
		val status: String = "",

		@field:SerializedName("StartDate")
		val startDate: String ="",

		@field:SerializedName("ReturnDate")
		val returnDate: String ="",

		@field:SerializedName("Destination")
		val destination: String ="",

		@field:SerializedName("DestinationName")
		val destinationName: String ="",

		@field:SerializedName("IsApprover")
		val isApprover: Boolean =false,

		@field:SerializedName("ListParticipant")
		val listParticipant : List<ListApproverItem> = ArrayList(),

		@field:SerializedName("IsParticipant")
		val isParticipant: Boolean =false,

		@field:SerializedName("ListApprover")
		val listApprover: List<ListApproverItem> = ArrayList(),

		@field:SerializedName("Purpose")
		val purpose: String ="",

		@field:SerializedName("Id")
		val id: String ="",

		@field:SerializedName("StatusView")
		val statusView: String ="",

		@field:SerializedName("Code")
		val code: String =""
)