package opsigo.com.datalayer.model.accomodation.hotel.room

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class CancelPolicyHotel(

	@field:SerializedName("HotelKey")
	val hotelKey: String = "",

	@field:SerializedName("Rooms")
	val rooms: List<RoomsItem> = ArrayList()
)