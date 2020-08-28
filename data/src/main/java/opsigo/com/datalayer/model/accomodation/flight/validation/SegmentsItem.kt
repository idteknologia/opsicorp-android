package opsigo.com.datalayer.model.accomodation.flight.validation

import com.google.gson.annotations.SerializedName

data class SegmentsItem(

	@field:SerializedName("Origin")
	val origin: String = "",

	@field:SerializedName("BookingDate")
	val bookingDate: String = "",

	@field:SerializedName("IsComply")
	val isComply: Boolean = false,

	@field:SerializedName("DepartureTime")
	val departureTime: String = "",

	@field:SerializedName("ArriveTime")
	val arriveTime: String = "",

	@field:SerializedName("Airline")
	val airline: Int = 0,

	@field:SerializedName("Payments")
	val payments: List<Any?>? = null,

	@field:SerializedName("ClassId")
	val classId: String = "",

	@field:SerializedName("OriginView")
	val originView: String = "",

	@field:SerializedName("MealType")
	val mealType: Int = 0,

	@field:SerializedName("ArriveDateTime")
	val arriveDateTime: String = "",

	@field:SerializedName("CarrierNumber")
	val carrierNumber: String = "",

	@field:SerializedName("DescAdvanceBooking")
	val descAdvanceBooking: String = "",

	@field:SerializedName("ActionTimeView")
	val actionTimeView: String = "",

	@field:SerializedName("DestinationName")
	val destinationName: String = "",

	@field:SerializedName("FlightType")
	val flightType: String = "",

	@field:SerializedName("ArriveDate")
	val arriveDate: String = "",

	@field:SerializedName("DepartureDate")
	val departureDate: String = "",

	@field:SerializedName("IsSecuritySensitivity")
	val isSecuritySensitivity: Boolean = false,

	@field:SerializedName("LowestFare")
	val lowestFare: String = "",

	@field:SerializedName("Baggage")
	val baggage: String = "",

	@field:SerializedName("SelectedClass")
	val selectedClass: String = "",

	@field:SerializedName("ArrivalTime")
	val arrivalTime: String = "",

	@field:SerializedName("Status")
	val status: String = "",

	@field:SerializedName("SportEquipmentType")
	val sportEquipmentType: Int = 0,

	@field:SerializedName("AirlineImageUrl")
	val airlineImageUrl: String = "",

	@field:SerializedName("DurationAll")
	val durationAll: String = "",

	@field:SerializedName("DepartDateTime")
	val departDateTime: String = "",

	@field:SerializedName("IsAirlineCompliance")
	val isAirlineCompliance: Boolean = false,

	@field:SerializedName("Duration")
	val duration: String = "",

	@field:SerializedName("DepartDate")
	val departDate: String = "",

	@field:SerializedName("ClassCode")
	val classCode: String = "",

	@field:SerializedName("TimeLimitView")
	val timeLimitView: String = "",

	@field:SerializedName("IsDoubleDrink")
	val isDoubleDrink: Boolean = false,

	@field:SerializedName("DestinationView")
	val destinationView: String = "",

	@field:SerializedName("FlightNumber")
	val flightNumber: String = "",

	@field:SerializedName("Class")
	val classFlight: String = "",

	@field:SerializedName("Id")
	val id: String = "",

	@field:SerializedName("ReferenceCode")
	val referenceCode: String = "",

	@field:SerializedName("DescSecuritySensitivity")
	val descSecuritySensitivity: String = "",

	@field:SerializedName("JourneyCode")
	val journeyCode: String = "",

	@field:SerializedName("Seq")
	val seq: String = "",

	@field:SerializedName("BookingNumber")
	val bookingNumber: String = "",

	@field:SerializedName("JobProgress")
	val jobProgress: String = "",

	@field:SerializedName("IsSecondaryLevel")
	val isSecondaryLevel: Boolean = false,

	@field:SerializedName("IsAdvanceBooking")
	val isAdvanceBooking: Boolean = false,

	@field:SerializedName("BookingCode")
	val bookingCode: String = "",

	@field:SerializedName("Destination")
	val destination: String = "",

	@field:SerializedName("Category")
	val category: String = "",

	@field:SerializedName("TrainName")
	val trainName: String = "",

	@field:SerializedName("IsHaveSport")
	val isHaveSport: Boolean = false,

	@field:SerializedName("IsLowestFare")
	val isLowestFare: Boolean = false,

	@field:SerializedName("TripTrainId")
	val tripTrainId: String = "",

	@field:SerializedName("IsHaveDrink")
	val isHaveDrink: Boolean = false,

	@field:SerializedName("DepartTime")
	val departTime: String = "",

	@field:SerializedName("CategoryCabin")
	val categoryCabin: String = "",

	@field:SerializedName("TimeLimit")
	val timeLimit: String = "",

	@field:SerializedName("SubClass")
	val subClass: String = "",

	@field:SerializedName("TrainNumber")
	val trainNumber: String = "",

	@field:SerializedName("FareBasisCode")
	val fareBasisCode: String = "",

	@field:SerializedName("Ssrs")
	val ssrs: List<Any?>? = null,

	@field:SerializedName("Num")
	val num: String = "",

	@field:SerializedName("AirlineView")
	val airlineView: String = "",

	@field:SerializedName("Amount")
	val amount: Int = 0,

	@field:SerializedName("ActionTime")
	val actionTime: String = "",

	@field:SerializedName("DrinkType")
	val drinkType: Int = 0,

	@field:SerializedName("OriginName")
	val originName: String = "",

	@field:SerializedName("ArrivalDate")
	val arrivalDate: String = "",

	@field:SerializedName("IsHaveMeals")
	val isHaveMeals: Boolean = false,

	@field:SerializedName("FlightId")
	val flightId: String = "",

	@field:SerializedName("TripItemId")
	val tripItemId: String = "",

	@field:SerializedName("ClassKey")
	val classKey: String = "",

	@field:SerializedName("CarrierCode")
	val carrierCode: String = "",

	@field:SerializedName("IsRestrictedDest")
	val isRestrictedDest: Boolean = false,

	@field:SerializedName("TripFlightId")
	val tripFlightId: String = ""
)