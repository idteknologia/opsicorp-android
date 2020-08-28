package opsigo.com.datalayer.model.accomodation.hotel.booking

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class BookingHotelEntity(

        @field:SerializedName("errorMessage")
	val errorMessage: String? = null,

        @field:SerializedName("model")
	val model: ModelReservation = ModelReservation(),

        @field:SerializedName("currBudget")
	val currBudget: Double = 0.0,

        @field:SerializedName("isSuccess")
	val isSuccess: Boolean = false
)