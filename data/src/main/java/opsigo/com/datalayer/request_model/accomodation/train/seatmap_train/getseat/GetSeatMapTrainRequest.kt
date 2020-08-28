package opsigo.com.datalayer.request_model.accomodation.train.seatmap_train.getseat

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class GetSeatMapTrainRequest(
		@field:SerializedName("SeatParam")
		var seatParam: SeatMapTrainParamRequest = SeatMapTrainParamRequest(),

		@field:SerializedName("TravelAgent")
		var travelAgent: String = "",

		@field:SerializedName("TripId")
		var tripId: String = ""
)