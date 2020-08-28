package opsigo.com.datalayer.request_model.accomodation.train.sync

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SyncTrainRequest(

	@field:SerializedName("trainId")
	var trainId: String = "",

	@field:SerializedName("tripPlanId")
	var tripPlanId: String = "",

	@field:SerializedName("pnrId")
	var pnrId: String = "",

	@field:SerializedName("travelAgent")
	var travelAgent: String = ""
)