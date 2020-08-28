package opsigo.com.datalayer.model.accomodation.flight.ssr

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SsrDataEntity(

		@field:SerializedName("Origin")
	val origin: String? = null,

		@field:SerializedName("IsSecondaryLevel")
	val isSecondaryLevel: Boolean? = null,

		@field:SerializedName("IsAdvanceBooking")
	val isAdvanceBooking: Boolean? = null,

		@field:SerializedName("IsComply")
	val isComply: Boolean? = null,

		@field:SerializedName("Destination")
	val destination: String? = null,

		@field:SerializedName("ArriveTime")
	val arriveTime: String? = null,

		@field:SerializedName("Airline")
	val airline: Int? = null,

		@field:SerializedName("IsHaveSport")
	val isHaveSport: Boolean = false,

		@field:SerializedName("ClassId")
	val classId: String? = null,

		@field:SerializedName("IsLowestFare")
	val isLowestFare: Boolean? = null,

		@field:SerializedName("MealType")
	val mealType: Int? = null,

		@field:SerializedName("ArriveDateTime")
	val arriveDateTime: String? = null,

		@field:SerializedName("IsHaveDrink")
	val isHaveDrink: Boolean = false,

		@field:SerializedName("DepartTime")
	val departTime: String? = null,

		@field:SerializedName("ActionTimeView")
	val actionTimeView: String? = null,

		@field:SerializedName("ArriveDate")
	val arriveDate: String? = null,

		@field:SerializedName("IsSecuritySensitivity")
	val isSecuritySensitivity: Boolean? = null,

		@field:SerializedName("LowestFare")
	val lowestFare: Double? = null,

		@field:SerializedName("Baggage")
	val baggage: Any? = null,

		@field:SerializedName("TimeLimit")
	val timeLimit: String? = null,

		@field:SerializedName("SportEquipmentType")
	val sportEquipmentType: Int? = null,

		@field:SerializedName("Ssrs")
	val ssrs: List<SsrsItem> = ArrayList(),

		@field:SerializedName("Num")
	val num: String? = null,

		@field:SerializedName("Amount")
	val amount: Double? = null,

		@field:SerializedName("DepartDateTime")
	val departDateTime: String? = null,

		@field:SerializedName("IsAirlineCompliance")
	val isAirlineCompliance: Boolean? = null,

		@field:SerializedName("DepartDate")
	val departDate: String? = null,

		@field:SerializedName("ActionTime")
	val actionTime: String? = null,

		@field:SerializedName("DrinkType")
	val drinkType: Int? = null,

		@field:SerializedName("ClassCode")
	val classCode: String? = null,

		@field:SerializedName("TimeLimitView")
	val timeLimitView: String? = null,

		@field:SerializedName("IsDoubleDrink")
	val isDoubleDrink: Boolean = false,

		@field:SerializedName("CrossDay")
	val crossDay: Int? = null,

		@field:SerializedName("IsHaveMeals")
	val isHaveMeals: Boolean = false,

		@field:SerializedName("FlightId")
	val flightId: String? = null,

		@field:SerializedName("FlightNumber")
	val flightNumber: String? = null,

		@field:SerializedName("Id")
	val id: String? = null,

		@field:SerializedName("IsRestrictedDest")
	val isRestrictedDest: Boolean? = null,

		@field:SerializedName("Seq")
	val seq: String? = null
)