package opsigo.com.datalayer.model.accomodation.train.reservation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SegmentsItemReservationEntity(

	@field:SerializedName("Origin")
	val origin: String = "",

	@field:SerializedName("IsSecondaryLevel")
	val isSecondaryLevel: Boolean = false,

	@field:SerializedName("IsAdvanceBooking")
	val isAdvanceBooking: Boolean = false,

	@field:SerializedName("IsComply")
	val isComply: Boolean = false,

	@field:SerializedName("Destination")
	val destination: String = "",

	@field:SerializedName("ArriveTime")
	val arriveTime: String = "",

	@field:SerializedName("Category")
	val category: String = "",

	@field:SerializedName("TrainName")
	val trainName: String = "",

	@field:SerializedName("TripTrainId")
	val tripTrainId: String = "",

	@field:SerializedName("IsSecuritySensivity")
	val isSecuritySensivity: Boolean = false,

	@field:SerializedName("ArriveDateTime")
	val arriveDateTime: String = "",

	@field:SerializedName("DepartTime")
	val departTime: String = "",

	@field:SerializedName("CategoryCabin")
	val categoryCabin: String = "",

	@field:SerializedName("DescAdvanceBooking")
	val descAdvanceBooking: String = "",

	@field:SerializedName("AirportOrigin")
	val airportOrigin: String = "",

	@field:SerializedName("DestinationName")
	val destinationName: String = "",

	@field:SerializedName("ArriveDate")
	val arriveDate: String = "",

	@field:SerializedName("CurrierNumber")
	val currierNumber: String = "",

	@field:SerializedName("TransitDuration")
	val transitDuration: String = "",

	@field:SerializedName("KaiImageUrl")
	val kaiImageUrl: String = "",

	@field:SerializedName("FareBasisCode")
	val fareBasisCode: String = "",

	@field:SerializedName("Num")
	val num: Int? = null,

	@field:SerializedName("DepartDateTime")
	val departDateTime: String = "",

	@field:SerializedName("CityDestination")
	val cityDestination: String = "",

	@field:SerializedName("Duration")
	val duration: String = "",

	@field:SerializedName("DepartDate")
	val departDate: String = "",

	@field:SerializedName("OriginName")
	val originName: String = "",

	@field:SerializedName("ClassCode")
	val classCode: String = "",

	@field:SerializedName("AirportDestination")
	val airportDestination: String = "",

	@field:SerializedName("CityOrigin")
	val cityOrigin: String = "",

	@field:SerializedName("CountryOrigin")
	val countryOrigin: String = "",

	@field:SerializedName("Id")
	val id: String = "",

	@field:SerializedName("IsRestrictedDest")
	val isRestrictedDest: Boolean = false,

	@field:SerializedName("DescSecuritySensitivity")
	val descSecuritySensitivity: String = "",

	@field:SerializedName("CountryDestination")
	val countryDestination: String = ""
)