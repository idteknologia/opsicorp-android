package opsigo.com.datalayer.request_model.accomodation.flight.search

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SearchFlightRequest(

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

		@field:SerializedName("JobTitleId")
	var jobTitleId: String = "",

		@field:SerializedName("CompCode")
	var compCode: String = "",

		@field:SerializedName("TravelAgentCode")
	var travelAgentCode: String = "",

		@field:SerializedName("TravelAgentCode")
	var cabinFilterClasses: ArrayList<String> = ArrayList(),

		@field:SerializedName("TravelAgentCode")
	var preferredCarriers: ArrayList<String> = ArrayList()

)