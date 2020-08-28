package opsigo.com.datalayer.model

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class TripParticipantCustomApprovalsItem(

	@field:SerializedName("IsHaveAirline")
	val isHaveAirline: Boolean? = null,

	@field:SerializedName("Company")
	val company: String = "",

	@field:SerializedName("HasHotel")
	val hasHotel: Boolean? = null,

	@field:SerializedName("InfoCompany")
	val infoCompany: String = "",

	@field:SerializedName("CompletelyReviewedDate")
	val completelyReviewedDate: String = "",

	@field:SerializedName("IsHaveHotel")
	val isHaveHotel: Boolean? = null,

	@field:SerializedName("ParentCompanyName")
	val parentCompanyName: Any? = null,

	@field:SerializedName("FollowUpHotel")
	val followUpHotel: String? = "",

	@field:SerializedName("HasTrain")
	val hasTrain: Boolean? = null,

	@field:SerializedName("CompanyName")
	val companyName: String = "",

	@field:SerializedName("ParentCompany")
	val parentCompany: Any? = null,

	@field:SerializedName("FollowUpTrain")
	val followUpTrain: String? = "",

	@field:SerializedName("FollowUpAirline")
	val followUpAirline: String? = "",

	@field:SerializedName("ApproverId")
	val approverId: String = "",

	@field:SerializedName("ApproverName")
	val approverName: String = "",

	@field:SerializedName("Id")
	val id: String = "",

	@field:SerializedName("HasAirline")
	val hasAirline: Boolean = false,

	@field:SerializedName("Layers")
	val layers: Int = 0,

	@field:SerializedName("CompanyCode")
	val companyCode: String = "",

	@field:SerializedName("TripParticipantId")
	val tripParticipantId: String = "",

	@field:SerializedName("IsCompletelyReviewed")
	val isCompletelyReviewed: Boolean = false
)