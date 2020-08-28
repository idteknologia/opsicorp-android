package opsigo.com.datalayer.model

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class UpcomingFlightEntity(

	@field:SerializedName("total")
	val total: Int = 0,

	@field:SerializedName("data")
	val data: List<UpcomingFlightItemEntity?>? = null
)