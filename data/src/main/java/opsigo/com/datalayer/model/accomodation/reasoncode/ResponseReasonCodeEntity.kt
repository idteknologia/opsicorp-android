package opsigo.com.datalayer.model.accomodation.reasoncode

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ResponseReasonCodeEntity(

	@field:SerializedName("total")
	val total: Int = 0,

	@field:SerializedName("data")
	val data: List<ReasonCodeEntity> = ArrayList()
)