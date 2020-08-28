package opsigo.com.datalayer.request_model.create_trip_plane

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class TripAttachmentsItemRequest(

	@field:SerializedName("Description")
	var description: String = "",

	@field:SerializedName("Url")
	var url: String = ""
)