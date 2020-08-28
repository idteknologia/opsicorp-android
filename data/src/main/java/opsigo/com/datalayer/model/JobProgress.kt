package opsigo.com.datalayer.model

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class JobProgress(

	@field:SerializedName("RunStart")
	val runStart: String? = null,

	@field:SerializedName("RunEnd")
	val runEnd: String? = null,

	@field:SerializedName("ProgressNum")
	val progressNum: Double? = null,

	@field:SerializedName("Progress")
	val progress: String = "",

	@field:SerializedName("IsManual")
	val isManual: Boolean? = null,

	@field:SerializedName("RowKey")
	val rowKey: Any? = null,

	@field:SerializedName("Text")
	val text: String? = null,

	@field:SerializedName("Timestamp")
	val timestamp: String? = null,

	@field:SerializedName("PnrCode")
	val pnrCode: Any? = null,

	@field:SerializedName("JobType")
	val jobType: Any? = null,

	@field:SerializedName("PnrId")
	val pnrId: Any? = null,

	@field:SerializedName("ETag")
	val eTag: Any? = null,

	@field:SerializedName("PartitionKey")
	val partitionKey: Any? = null,

	@field:SerializedName("Key")
	val key: Any? = null
)