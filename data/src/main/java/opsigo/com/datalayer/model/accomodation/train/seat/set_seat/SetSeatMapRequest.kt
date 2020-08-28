package opsigo.com.datalayer.model.accomodation.train.seat.set_seat

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SetSeatMapRequest(

        @field:SerializedName("PnrId")
	val pnrId: String = "",

        @field:SerializedName("TrainSeat")
	val trainSeat: TrainSeatMapRequest = TrainSeatMapRequest(),

        @field:SerializedName("TravelAgent")
	val travelAgent: String = "",

        @field:SerializedName("ReferenceCode")
	val referenceCode: String = "",

        @field:SerializedName("TripId")
	val tripId: String = "",

        @field:SerializedName("PnrCode")
	val pnrCode: String = "",

        @field:SerializedName("TrainId")
	val trainId: String = ""
)