package opsigo.com.datalayer.model.accomodation.purchase

import com.google.gson.annotations.SerializedName

data class DetailPurchaseEntity(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("errorMessage")
	val errorMessage: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class SegmentsItem(

	@field:SerializedName("Origin")
	val origin: String? = null,

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("Destination")
	val destination: String? = null,

	@field:SerializedName("AirlineImageUrl")
	val airlineImageUrl: String? = null,

	@field:SerializedName("DestinationCity")
	val destinationCity: String? = null,

	@field:SerializedName("Num")
	val num: Int? = null,

	@field:SerializedName("AirlineName")
	val airlineName: String? = null,

	@field:SerializedName("OriginCity")
	val originCity: String? = null,

	@field:SerializedName("Duration")
	val duration: String? = null,

	@field:SerializedName("ClassCategory")
	val classCategory: String? = null,

	@field:SerializedName("DestinationAirport")
	val destinationAirport: String? = null,

	@field:SerializedName("PnrCode")
	val pnrCode: String? = null,

	@field:SerializedName("OriginAirport")
	val originAirport: String? = null,

	@field:SerializedName("ClassCode")
	val classCode: String? = null,

	@field:SerializedName("DepartureTimeDisplay")
	val departureTimeDisplay: String? = null,

	@field:SerializedName("FlightNumber")
	val flightNumber: String? = null,

	@field:SerializedName("DepartureDate")
	val departureDate: String? = null,

	@field:SerializedName("DepartureDateDisplay")
	val departureDateDisplay: String? = null,

	@field:SerializedName("Seq")
	val seq: Int? = null
)

data class FlightsItem(

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("Passengers")
	val passengers: List<PassengersItem?>? = null,

	@field:SerializedName("DestinationCity")
	val destinationCity: String? = null,

	@field:SerializedName("Num")
	val num: Int? = null,

	@field:SerializedName("Segments")
	val segments: List<SegmentsItem?>? = null,

	@field:SerializedName("OriginCity")
	val originCity: String? = null,

	@field:SerializedName("PnrCode")
	val pnrCode: String? = null
)

data class BookingContact(

	@field:SerializedName("Email")
	val email: String? = null,

	@field:SerializedName("FirstName")
	val firstName: String? = null,

	@field:SerializedName("FullName")
	val fullName: String? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("LastName")
	val lastName: String? = null,

	@field:SerializedName("MobilePhone")
	val mobilePhone: String? = null
)

data class PriceDetailsItem(

	@field:SerializedName("Amount")
	val amount: Double? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("Index")
	val index: Int? = null
)

data class PassengersItem(

	@field:SerializedName("Type")
	val type: String? = null,

	@field:SerializedName("SeatName")
	val seatName: Any? = null,

	@field:SerializedName("FirstName")
	val firstName: String? = null,

	@field:SerializedName("IdNumber")
	val idNumber: Any? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("Index")
	val index: Int? = null,

	@field:SerializedName("LastName")
	val lastName: String? = null,

	@field:SerializedName("SeatNumber")
	val seatNumber: Any? = null,

	@field:SerializedName("IdType")
	val idType: Any? = null,

	@field:SerializedName("BirthDate")
	val birthDate: String? = null
)

data class Data(

	@field:SerializedName("PriceDetails")
	val priceDetails: List<PriceDetailsItem?>? = null,

	@field:SerializedName("Trains")
	val trains: List<TrainsItem?>? = null,

	@field:SerializedName("PaymentStatusText")
	val paymentStatusText: String? = null,

	@field:SerializedName("ItemType")
	val itemType: Int? = null,

	@field:SerializedName("ItemTypeText")
	val itemTypeText: String? = null,

	@field:SerializedName("BookingContact")
	val bookingContact: BookingContact? = null,

	@field:SerializedName("Code")
	val code: String? = null,

	@field:SerializedName("Flights")
	val flights: List<FlightsItem?>? = null,

	@field:SerializedName("PurchasedDate")
	val purchasedDate: String? = null,

	@field:SerializedName("TotalPaid")
	val totalPaid: Double? = null,

	@field:SerializedName("PaymentMethod")
	val paymentMethod: String? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("PaymentStatus")
	val paymentStatus: Int? = null,

	@field:SerializedName("Hotel")
	val hotel: Hotel? = null
)


data class TrainsItem(

	@field:SerializedName("Origin")
	val origin: String? = null,

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("Destination")
	val destination: String? = null,

	@field:SerializedName("TrainNumber")
	val trainNumber: String? = null,

	@field:SerializedName("TrainName")
	val trainName: String? = null,

	@field:SerializedName("DestinationCity")
	val destinationCity: String? = null,

	@field:SerializedName("Num")
	val num: Int? = null,

	@field:SerializedName("OriginCity")
	val originCity: String? = null,

	@field:SerializedName("Duration")
	val duration: String? = null,

	@field:SerializedName("ClassCategory")
	val classCategory: String? = null,

	@field:SerializedName("PnrCode")
	val pnrCode: String? = null,

	@field:SerializedName("ClassCode")
	val classCode: String? = null,

	@field:SerializedName("Passengers")
	val passengers: List<PassengersItem?>? = null,

	@field:SerializedName("OriginStation")
	val originStation: String? = null,

	@field:SerializedName("ArrivalDate")
	val arrivalDate: String? = null,

	@field:SerializedName("DestinationStation")
	val destinationStation: String? = null,

	@field:SerializedName("DepartureDate")
	val departureDate: String? = null
)


data class Hotel(

	@field:SerializedName("MapUri")
	val mapUri: String? = null,

	@field:SerializedName("StatusEnum")
	val statusEnum: Int? = null,

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("CheckInDateDisplay")
	val checkInDateDisplay: String? = null,

	@field:SerializedName("RoomsTotal")
	val roomsTotal: Int? = null,

	@field:SerializedName("RemarkHotel")
	val remarkHotel: String? = null,

	@field:SerializedName("Address")
	val address: String? = null,

	@field:SerializedName("CancellationInfo")
	val cancellationInfo: String? = null,

	@field:SerializedName("CheckInDate")
	val checkInDate: String? = null,

	@field:SerializedName("ImageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("City")
	val city: String? = null,

	@field:SerializedName("DurationDays")
	val durationDays: Int? = null,

	@field:SerializedName("BookingContact")
	val bookingContact: String? = null,

	@field:SerializedName("PnrCode")
	val pnrCode: Any? = null,

	@field:SerializedName("HotelRating")
	val hotelRating: Int? = null,

	@field:SerializedName("RoomCategory")
	val roomCategory: String? = null,

	@field:SerializedName("Area")
	val area: String? = null,

	@field:SerializedName("FreeBreakfast")
	val freeBreakfast: Boolean? = null,

	@field:SerializedName("RoomType")
	val roomType: String? = null,

	@field:SerializedName("Guests")
	val guests: List<GuestsItem?>? = null,

	@field:SerializedName("Country")
	val country: Any? = null,

	@field:SerializedName("HotelName")
	val hotelName: String? = null,

	@field:SerializedName("RoomService")
	val roomService: String? = null,

	@field:SerializedName("CountryCode")
	val countryCode: String? = null
)


data class GuestsItem(

	@field:SerializedName("Type")
	val type: String? = null,

	@field:SerializedName("SeatName")
	val seatName: Any? = null,

	@field:SerializedName("FirstName")
	val firstName: String? = null,

	@field:SerializedName("IdNumber")
	val idNumber: String? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("Index")
	val index: Int? = null,

	@field:SerializedName("LastName")
	val lastName: String? = null,

	@field:SerializedName("SeatNumber")
	val seatNumber: Any? = null,

	@field:SerializedName("IdType")
	val idType: Any? = null,

	@field:SerializedName("BirthDate")
	val birthDate: String? = null
)
