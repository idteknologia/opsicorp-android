package opsigo.com.datalayer.request_model.accomodation.flight.reservation

import com.google.gson.annotations.SerializedName

data class ReserveFlightRequest(

		@field:SerializedName("DataBooking")
	var dataBooking: DataBookingFlightRequest = DataBookingFlightRequest(),
//	val dataBookingFlightRequest: DataBookingFlightRequest = DataBookingFlightRequest(),
//var dataBooking: DataBookingReservationTrainRequest  = DataBookingReservationTrainRequest()
		@field:SerializedName("Header")
	var header: HeaderReserveFlightRequest = HeaderReserveFlightRequest()
//	var headerReserveFlightRequest: HeaderReserveFlightRequest = HeaderReserveFlightRequest()
)