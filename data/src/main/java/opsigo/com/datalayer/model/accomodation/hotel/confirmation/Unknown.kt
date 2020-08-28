package opsigo.com.datalayer.model.accomodation.hotel.confirmation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class Unknown(

	@field:SerializedName("TypeName")
	val typeName: String = "",

	@field:SerializedName("TypeCode")
	val typeCode: String = "",

	@field:SerializedName("CountRoom")
	val countRoom: Int = 0,

	@field:SerializedName("PricePerRoom")
	val pricePerRoom: Double = 0.0
)