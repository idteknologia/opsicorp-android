package opsigo.com.datalayer.model.accomodation.hotel.detail

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ReviewsItem(

	@field:SerializedName("AuthorName")
	val authorName: String = "",

	@field:SerializedName("Rating")
	val rating: Int = 0,

	@field:SerializedName("Text")
	val text: String = "",

	@field:SerializedName("RelativeTime")
	val relativeTime: String = "",

	@field:SerializedName("Uri")
	val uri: String = ""
)