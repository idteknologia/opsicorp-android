package opsigo.com.datalayer.model.listtripplan

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class TripPlanEntity(

	@field:SerializedName("total")
	val total: String = "",

	@field:SerializedName("data")
	val data: List<DataItem> = ArrayList<DataItem>()
)