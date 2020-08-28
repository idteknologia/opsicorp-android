package opsigo.com.datalayer.model

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class Passport(

	@field:SerializedName("Expire")
	val expire: String = "",

	@field:SerializedName("PassengerId")
	val passengerId: String = "",

	@field:SerializedName("Number")
	val number: String = "",

	@field:SerializedName("FirstName")
	val firstName: String = "",

	@field:SerializedName("OriginCountry")
	val originCountry: String = "",

	@field:SerializedName("Id")
	val id: String = "",

	@field:SerializedName("LastName")
	val lastName: String = ""
)