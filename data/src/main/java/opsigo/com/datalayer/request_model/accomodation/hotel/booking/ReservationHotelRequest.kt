package opsigo.com.datalayer.request_model.accomodation.hotel.booking

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ReservationHotelRequest(

		@field:SerializedName("ConfirmationId")
	var confirmationId: String = "",

		@field:SerializedName("Guests")
	var guests: List<GuestsItemReservationHotelRequest> = ArrayList(),

		@field:SerializedName("Header")
	var header: HeaderReservationHotelRequest? = null,

		@field:SerializedName("DestinationCity")
	var destinationCity: String = "",

		@field:SerializedName("GuestPassport")
	var guestPassport: String = "",

		@field:SerializedName("Booking")
	var booking: BookingReservationHotelRequest = BookingReservationHotelRequest(),

		@field:SerializedName("CorrelationId")
	var correlationId: String = "",

		@field:SerializedName("Beds")
	var beds: BedsReservationHotelRequest = BedsReservationHotelRequest(),

		@field:SerializedName("Contact")
	var contact: ContactReservationHotelRequest = ContactReservationHotelRequest(),

		@field:SerializedName("Remark")
	var remark: String = "",

		@field:SerializedName("TravelProfileId")
	var travelProfileId: String = ""
)