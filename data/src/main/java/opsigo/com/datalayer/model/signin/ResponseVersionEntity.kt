package opsigo.com.datalayer.model.signin

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ResponseVersionEntity(

	@field:SerializedName("VersionCode")
	val versionCode: String = "",

	@field:SerializedName("Type")
	val type: String = "",

	@field:SerializedName("TypeName")
	val typeName: String = "",

	@field:SerializedName("Message")
	val message: String = "",

	@field:SerializedName("Enable")
	val enable: Boolean = false,

	@field:SerializedName("VersionName")
	val versionName: String = "",

	@field:SerializedName("Id")
	val id: String = "",

	@field:SerializedName("Info")
	val info: String = "",

	@field:SerializedName("StatusCode")
	val statusCode: String = ""
)