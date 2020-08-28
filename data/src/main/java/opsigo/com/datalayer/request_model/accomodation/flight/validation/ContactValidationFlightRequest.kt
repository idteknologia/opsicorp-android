package opsigo.com.datalayer.request_model.accomodation.flight.validation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ContactValidationFlightRequest(

	@field:SerializedName("Email")
	var email: String? = null,

	@field:SerializedName("HomePhone")
	var homePhone: String? = null,

	@field:SerializedName("FirstName")
	var firstName: String? = null,

	@field:SerializedName("Title")
	var title: String? = null,

	@field:SerializedName("LastName")
	var lastName: String? = null,

	@field:SerializedName("MobilePhone")
	var mobilePhone: String? = null
)