package opsigo.com.datalayer.model.accomodation.hotel.detail

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ImagesItem(

	@field:SerializedName("Description")
	val description: String = "",

	@field:SerializedName("ThumbUri")
	val thumbUri: String = "",

	@field:SerializedName("ImageUri")
	val imageUri: String = ""
)