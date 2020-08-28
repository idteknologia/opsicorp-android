package opsigo.com.datalayer.request_model.accomodation.flight.reservation

import com.google.gson.annotations.SerializedName

data class ContactFlightRequest(

	@field:SerializedName("Email")
	var email: String = "",

	@field:SerializedName("HomePhone")
	var homePhone: String = "",

	@field:SerializedName("FirstName")
	var firstName: String = "",

	@field:SerializedName("Title")
	var title: String = "",

	@field:SerializedName("LastName")
	var lastName: String = "",

	@field:SerializedName("MobilePhone")
	var mobilePhone: String = ""
)