package opsigo.com.datalayer.model.accomodation.hotel.search_hotel

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class PriceDetailsItem(

	@field:SerializedName("CountChild")
	val countChild: Int? = null,

	@field:SerializedName("RoomTypePrice")
	val roomTypePrice: RoomTypePrice? = null,

	@field:SerializedName("NightIndex")
	val nightIndex: Int? = null,

	@field:SerializedName("TotalRoom")
	val totalRoom: Int? = null,

	@field:SerializedName("TotalPrice")
	val totalPrice: Double? = null,

	@field:SerializedName("CountInfant")
	val countInfant: Int? = null,

	@field:SerializedName("CountAdult")
	val countAdult: Int? = null
)