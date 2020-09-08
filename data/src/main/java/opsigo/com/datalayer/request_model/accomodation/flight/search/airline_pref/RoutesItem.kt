package opsigo.com.datalayer.request_model.accomodation.flight.search.airline_pref

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class RoutesItem(

	@field:SerializedName("Origin")
    var origin: String? = null,

	@field:SerializedName("Destination")
	var destination: String? = null,

	@field:SerializedName("DepartDate")
	var departDate: String? = null
)