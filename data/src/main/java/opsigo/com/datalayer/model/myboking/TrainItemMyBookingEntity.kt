package opsigo.com.datalayer.model.myboking

import com.google.gson.annotations.SerializedName

data class TrainItemMyBookingEntity(

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

data class PassengersItem(

	@field:SerializedName("Type")
	val type: String? = null,

	@field:SerializedName("SeatName")
	val seatName: String? = null,

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
	val seatNumber: String? = null,

	@field:SerializedName("IdType")
	val idType: String? = null,

	@field:SerializedName("BirthDate")
	val birthDate: String? = null
)
