package opsigo.com.datalayer.model.approval.list_approval

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class TotalApprovalEntity(

	@field:SerializedName("Status")
	val status: String= "",

	@field:SerializedName("Total")
	val total: String = "",

	@field:SerializedName("StatusView")
	val statusView: String = ""
)