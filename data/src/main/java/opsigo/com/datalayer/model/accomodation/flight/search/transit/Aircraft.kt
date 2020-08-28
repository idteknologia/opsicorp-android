package opsigo.com.datalayer.model.accomodation.flight.search.transit

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class Aircraft(

	@field:SerializedName("LongName")
	val longName: String? = null,

	@field:SerializedName("IataCode")
	val iataCode: Any? = null,

	@field:SerializedName("ShortName")
	val shortName: String? = null
)