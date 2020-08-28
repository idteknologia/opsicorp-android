package opsigo.com.datalayer.model

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class TripAttachmentItem(

	@field:SerializedName("Id")
	val id: String = "",

	@field:SerializedName("TripPlanId")
	val tripPlanId: String = "",

	@field:SerializedName("Description")
	val description: String = "",

	@field:SerializedName("Url")
	val url: String = ""
)