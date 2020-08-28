package opsigo.com.datalayer.model.signin.version

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class VersionEntity(

	@field:SerializedName("typeName")
	val typeName: String = "",

	@field:SerializedName("id")
	val id: String = "",

	@field:SerializedName("versionName")
	val versionName: String = "",

	@field:SerializedName("type")
	val type: String = "",

	@field:SerializedName("message")
	val message: String = "",

	@field:SerializedName("versionCode")
	val versionCode: String = "",

	@field:SerializedName("enabled")
	val enabled: Boolean = false,

	@field:SerializedName("info")
	val info: String = "",

	@field:SerializedName("statusCode")
	val statusCode: String = ""
)