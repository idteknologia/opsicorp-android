package opsigo.com.datalayer.model.accomodation.flight.prefered

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class PreferedAirlineEntity(

	@field:SerializedName("list")
	val list: List<CodeAirlineEntity> = ArrayList()
)