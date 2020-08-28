package opsigo.com.datalayer.model.accomodation.flight.ssr

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SsrResponseEntity(

		@field:SerializedName("data")
	val data: List<SsrDataEntity> = ArrayList(),

		@field:SerializedName("errorMessage")
	val errorMessage: String? = ""
)