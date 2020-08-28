package opsigo.com.datalayer.request_model.accomodation.train.seatmap_train.seatseat

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SetSeatTrainRequest(

	@field:SerializedName("PnrId")
	var pnrId: String = "",

	@field:SerializedName("TrainSeat")
	var trainSeat: TrainSeat = TrainSeat(),

	@field:SerializedName("TravelAgent")
	var travelAgent: String = "",

	@field:SerializedName("ReferenceCode")
	var referenceCode: String = "",

	@field:SerializedName("TripId")
	var tripId: String = "",

	@field:SerializedName("TrainId")
	var trainId: String = "",

	@field:SerializedName("PnrCode")
	var pnrCode: String = ""
)