package opsigo.com.datalayer.model.accomodation.flight.validation

import com.google.gson.annotations.SerializedName

data class Passport(

	@field:SerializedName("Expire")
	val expire: Any? = null,

	@field:SerializedName("PassengerId")
	val passengerId: String? = null,

	@field:SerializedName("Number")
	val number: Any? = null,

	@field:SerializedName("FirstName")
	val firstName: Any? = null,

	@field:SerializedName("OriginCountry")
	val originCountry: Any? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("LastName")
	val lastName: Any? = null
)