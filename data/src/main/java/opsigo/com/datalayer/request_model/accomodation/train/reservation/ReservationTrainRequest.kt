package opsigo.com.datalayer.request_model.accomodation.train.reservation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ReservationTrainRequest(

		@field:SerializedName("Header")
		var header: HeaderReservationTrainRequest = HeaderReservationTrainRequest(),

		@field:SerializedName("DataBooking")
		var dataBooking: DataBookingReservationTrainRequest  = DataBookingReservationTrainRequest()
)