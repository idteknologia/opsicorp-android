package opsigo.com.datalayer.model.accomodation.flight.upcomming

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class UpcomingFlightItemEntity(

	@field:SerializedName("Origin")
	val origin: String = "",

	@field:SerializedName("Destination")
	val destination: String = "",

	@field:SerializedName("TripPlanId")
	val tripPlanId: String = "",

	@field:SerializedName("DestinationCode")
	val destinationCode: String = "",

	@field:SerializedName("OriginCode")
	val originCode: String = "",

	@field:SerializedName("Purpose")
	val purpose: String = "",

	@field:SerializedName("Time")
	val time: String = "",

	@field:SerializedName("Date")
	val date: String = "",

	@field:SerializedName("TripPlanCode")
	val tripPlanCode: String = "",

	@field:SerializedName("Name")
	val name: String = ""
)