package opsigo.com.datalayer.model.signin.version

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class RespVersionEntity(

	@field:SerializedName("data")
	val data: List<VersionEntity> = ArrayList(),

	@field:SerializedName("message")
	val message: String = ""
)