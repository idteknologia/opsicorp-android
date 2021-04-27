package opsigo.com.datalayer.request_model.accomodation.hotel.booking

import com.google.gson.annotations.SerializedName

data class ReservationHotelRequest(

		@field:SerializedName("ConfirmationId")
	var confirmationId: String? = null,

		@field:SerializedName("Guests")
	var guests: List<GuestsItemReservationHotelRequest?>? = null,

		@field:SerializedName("Header")
	var header: HeaderReservationHotelRequest? = null,

		@field:SerializedName("DestinationCity")
	var destinationCity: String? = null,

		@field:SerializedName("GuestPassport")
	var guestPassport: String? = null,

		@field:SerializedName("Booking")
	var booking: BookingReservationHotelRequest? = null,

		@field:SerializedName("CorrelationId")
	var correlationId: String? = null,

		@field:SerializedName("Beds")
	var beds: BedsReservationHotelRequest? = null,

		@field:SerializedName("Contact")
	var contact: ContactReservationHotelRequest? = null,

		@field:SerializedName("Remark")
	var remark: String? = null,

		@field:SerializedName("TravelProfileId")
	var travelProfileId: Any? = null
)

data class GuestsItemReservationHotelRequest(

	@field:SerializedName("Type")
	var type: Int? = null,

	@field:SerializedName("HomePhone")
	var homePhone: String? = null,

	@field:SerializedName("Remarks")
	var remarks: List<String?>? = null,

	@field:SerializedName("FirstName")
	var firstName: String = "",

	@field:SerializedName("IdNumber")
	var idNumber: String? = null,


	@field:SerializedName("AssignedRoom")
	var assignedRoom: Int? = null,

	@field:SerializedName("OrderInRoom")
	var orderInRoom: Int? = null,

	@field:SerializedName("Title")
	var title: String? = null,

	@field:SerializedName("Index")
	var index: Int? = null,

	@field:SerializedName("LastName")
	var lastName: String = "",

	@field:SerializedName("MobilePhone")
	var mobilePhone: String? = null,

	@field:SerializedName("Nationality")
	var nationality: String? = null,

	@field:SerializedName("IsUseLocal")
	var isUseLocal: Boolean? = null

)

data class BedsReservationHotelRequest(

	@field:SerializedName("Type")
	var type: String? = null,

	@field:SerializedName("index")
	var index: Int? = null,

	@field:SerializedName("CountAdult")
	var countAdult: Int? = null
)

data class HotelReservationHotelRequest(

	@field:SerializedName("IsHsre")
	var isHsre: Boolean? = null,

	@field:SerializedName("MapUri")
	var mapUri: String? = null,

	@field:SerializedName("Area")
	var area: String? = null,

	@field:SerializedName("IsViolatedHotelRules")
	var isViolatedHotelRules: Boolean? = null,

	@field:SerializedName("RoomSelector")
	var roomSelector: String? = null,

	@field:SerializedName("IsTourism")
	var isTourism: Boolean? = null,

	@field:SerializedName("TourismTax")
	var tourismTax: Int? = null,

	@field:SerializedName("CauseViolatedRules")
	var causeViolatedRules: String? = null,

	@field:SerializedName("Image")
	var image: String? = null,

	@field:SerializedName("CancellationPoliciesView")
	var cancellationPoliciesView: List<String?>? = null
)

data class BookingReservationHotelRequest(

	@field:SerializedName("Origin")
	var origin: String? = null,

	@field:SerializedName("Destination")
	var destination: String? = null,

	@field:SerializedName("Remarks")
	var remarks: String? = null,

	@field:SerializedName("ReasonCode")
	var reasonCode: String? = null,

	@field:SerializedName("Members")
	var members: List<String?>? = null,

	@field:SerializedName("Hotel")
	var hotel: HotelReservationHotelRequest? = null
)

data class TripParticipantHotelRequest(

	@field:SerializedName("BudgetId")
	var budgetId: String? = null,

	@field:SerializedName("EmployeeId")
	var employeeId: String? = null,

	@field:SerializedName("CostCenterId")
	var costCenterId: String? = null
)

data class HeaderReservationHotelRequest(

		@field:SerializedName("Origin")
	var origin: String? = null,

		@field:SerializedName("ReturnDate")
	var returnDate: String? = null,

		@field:SerializedName("StartDate")
	var startDate: String? = null,

		@field:SerializedName("Destination")
	var destination: String? = null,

		@field:SerializedName("TripParticipants")
	var tripParticipants: List<TripParticipantHotelRequest?>? = null,

		@field:SerializedName("Type")
	var type: Int? = null,

		@field:SerializedName("TravelAgentAccount")
	var travelAgentAccount: String? = null,

		@field:SerializedName("Purpose")
	var purpose: String? = null,

		@field:SerializedName("Id")
		var idTripPlan: String = "",

		@field:SerializedName("Code")
	var code: Any? = null
)

data class ContactReservationHotelRequest(

	@field:SerializedName("Email")
	var email: String? = null,

	@field:SerializedName("FirstName")
	var firstName: String? = null,

	@field:SerializedName("Title")
	var title: String? = null,

	@field:SerializedName("LastName")
	var lastName: String? = null,

	@field:SerializedName("MobilePhone")
	var mobilePhone: String? = null,

	@field:SerializedName("Remark")
	var remark: String? = null
)
