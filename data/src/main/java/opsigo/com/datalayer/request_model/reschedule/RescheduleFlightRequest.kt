package opsigo.com.datalayer.request_model.reschedule

import com.google.gson.annotations.SerializedName

data class RescheduleFlightRequest(

	@field:SerializedName("TripCode")
	var tripCode: String? = null,

	@field:SerializedName("Participant")
	var participant: ParticipantReschedule? = null,

	@field:SerializedName("Reschedule")
	var reschedule: Reschedule? = null,

	@field:SerializedName("Attachment")
	var attachment: ArrayList<AttachmentItemReschedule> = ArrayList()
)

data class Reschedule(

	@field:SerializedName("BookingCode")
	var bookingCode: String? = null,

	@field:SerializedName("Type")
	var type: Int? = null,

	@field:SerializedName("ChangeTime")
	var changeTime: String? = null,

	@field:SerializedName("ChangeDate")
	var changeDate: String? = null,

	@field:SerializedName("ChangeNotes")
	var changeNotes: String? = null
)

data class ParticipantReschedule(

	@field:SerializedName("Id")
	var id: String? = null
)

data class AttachmentItemReschedule(

	@field:SerializedName("Description")
	val description: String? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("Url")
	val url: String? = null
)
