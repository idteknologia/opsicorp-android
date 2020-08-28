package opsigo.com.datalayer.model.accomodation.flight.search

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SearchFlightResultEntity(

	@field:SerializedName("result")
	val result: ResultSearchFlightEntity = ResultSearchFlightEntity(),

	@field:SerializedName("errorMessage")
	val errorMessage: String = ""
)