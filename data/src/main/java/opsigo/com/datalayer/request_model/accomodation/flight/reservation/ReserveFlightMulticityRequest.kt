package opsigo.com.datalayer.request_model.accomodation.flight.reservation

import com.google.gson.annotations.SerializedName

data class ReserveFlightMulticityRequest(

	@field:SerializedName("DataBooking")
	var dataBooking: DataBookingFlightRequest = DataBookingFlightRequest(),
	@field:SerializedName("Header")
	var header: HeaderReserveFlightMulticityRequest = HeaderReserveFlightMulticityRequest()
)