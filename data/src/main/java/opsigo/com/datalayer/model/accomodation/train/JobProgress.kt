package opsigo.com.datalayer.model.accomodation.train

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class JobProgress(

	@field:SerializedName("RunStart")
	val runStart: String = "",

	@field:SerializedName("RunEnd")
	val runEnd: String = "",

	@field:SerializedName("ProgressNum")
	val progressNum: Int = 0,

	@field:SerializedName("Progress")
	val progress: String = "",

	@field:SerializedName("IsManual")
	val isManual: Boolean = false,

	@field:SerializedName("RowKey")
	val rowKey: String = "",

	@field:SerializedName("Text")
	val text: String = "",

	@field:SerializedName("Timestamp")
	val timestamp: String = "",

	@field:SerializedName("PnrCode")
	val pnrCode: String = "",

	@field:SerializedName("JobType")
	val jobType: String = "",

	@field:SerializedName("PnrId")
	val pnrId: String = "",

	@field:SerializedName("ETag")
	val eTag: String = "",

	@field:SerializedName("PartitionKey")
	val partitionKey: String = "",

	@field:SerializedName("ReferenceCode")
	val referenceCode: String = "",

	@field:SerializedName("Key")
	val key: String = ""
)