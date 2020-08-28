package opsigo.com.datalayer.request_model.accomodation.train.validation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ContactValidationTrainRequest(

	@field:SerializedName("Email")
	var email: String= "",

	@field:SerializedName("HomePhone")
	var homePhone: String= "",

	@field:SerializedName("FirstName")
	var firstName: String= "",

	@field:SerializedName("Title")
	var title: String= "",

	@field:SerializedName("LastName")
	var lastName: String= "",

	@field:SerializedName("MobilePhone")
	var mobilePhone: String= ""
)