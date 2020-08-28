package opsigo.com.datalayer.model

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class LoginEntity(

	@field:SerializedName("access_token")
	val accessToken: String = "",

	@field:SerializedName("refresh_token")
	val refreshToken: String = "",

	@field:SerializedName("employeeId")
	val employeeId: String = "",

	@field:SerializedName(".expires")
	val expires: String = "",

	@field:SerializedName("token_type")
	val tokenType: String = "",

	@field:SerializedName("userName")
	val userName: String = "",

	@field:SerializedName("expires_in")
	val expiresIn: Int = 0,

	@field:SerializedName(".issued")
	val issued: String = ""
)