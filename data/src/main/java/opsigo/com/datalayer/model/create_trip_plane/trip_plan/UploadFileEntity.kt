package opsigo.com.datalayer.model.create_trip_plane.trip_plan

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class UploadFileEntity(

	@field:SerializedName("fileName")
	val fileName: String = "",

	@field:SerializedName("success")
	val success: String = "",

	@field:SerializedName("errMsg")
	val errMsg: String = "",

	@field:SerializedName("url")
	val url: String = ""
)