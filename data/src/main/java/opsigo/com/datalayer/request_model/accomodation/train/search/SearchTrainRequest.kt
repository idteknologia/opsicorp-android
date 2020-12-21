package opsigo.com.datalayer.request_model.accomodation.train.search

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SearchTrainRequest(

	@field:SerializedName("Origin")
	var origin: String = "",

	@field:SerializedName("ReturnDate")
	var returnDate: String = "",

	@field:SerializedName("Destination")
	var destination: String = "",

	@field:SerializedName("Airline")
	var airline: String = "",

	@field:SerializedName("DepartDate")
	var departDate: String = "",

	@field:SerializedName("jobTitleId")
	var jobTitleId: String = "",


	@field:SerializedName("TravelAgent")
	var travelAgent: String = ""

)