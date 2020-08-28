package opsigo.com.datalayer.request_model.reservation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ReservationFlightRequest(

	@field:SerializedName("DataBooking")
	val dataBooking: DataBooking = DataBooking(),

	@field:SerializedName("Header")
	val header: HeaderReservationRequest = HeaderReservationRequest()
)