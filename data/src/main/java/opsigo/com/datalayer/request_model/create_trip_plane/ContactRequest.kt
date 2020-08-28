package opsigo.com.datalayer.request_model.create_trip_plane

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ContactRequest(

	@field:SerializedName("FirstName")
	var firstName: String = "",

	@field:SerializedName("LastName")
	var lastName: String = ""
)