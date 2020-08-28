package opsigo.com.datalayer.request_model.accomodation.hotel.booking

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class BedsReservationHotelRequest(

	@field:SerializedName("Type")
	var type: String = "",

	@field:SerializedName("index")
	var index: Int = 0,

	@field:SerializedName("CountAdult")
	var countAdult: Int = 0
)