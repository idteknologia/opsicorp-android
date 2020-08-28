package opsigo.com.datalayer.request_model.accomodation.train.seatmap_train.getseat

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SeatMapTrainParamRequest(

	@field:SerializedName("Origin")
	var origin: String = "",

	@field:SerializedName("SubClass")
	var subClass: String = "",

	@field:SerializedName("Destination")
	var destination: String = "",

	@field:SerializedName("CarrierNumber")
	var carrierNumber: String = "",

	@field:SerializedName("FareBasisCode")
	var fareBasisCode: String = "",

	@field:SerializedName("DepartureDate")
	var departureDate: String = ""
)