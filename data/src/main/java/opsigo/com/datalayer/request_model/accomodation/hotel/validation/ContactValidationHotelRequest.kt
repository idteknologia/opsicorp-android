package opsigo.com.datalayer.request_model.accomodation.hotel.validation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ContactValidationHotelRequest(

	@field:SerializedName("Email")
	var email: String = "",

	@field:SerializedName("FirstName")
	var firstName: String = "",

	@field:SerializedName("Title")
	var title: String = "",

	@field:SerializedName("LastName")
	var lastName: String = "",

	@field:SerializedName("MobilePhone")
	var mobilePhone: String = "",

	@field:SerializedName("Remark")
	var remark: String = ""
)