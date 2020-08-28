package opsigo.com.datalayer.model.approval.list_approval

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ListApprovalEntity(

	@field:SerializedName("total")
	val total: Int = 0,

	@field:SerializedName("data")
	val data: List<DataItemListApprovalEntity> = ArrayList()
)