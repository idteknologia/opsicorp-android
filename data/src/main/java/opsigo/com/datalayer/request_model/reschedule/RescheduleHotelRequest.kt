package opsigo.com.datalayer.request_model.reschedule

import com.google.gson.annotations.SerializedName

data class RescheduleHotelRequest(

    @field:SerializedName("TripCode")
    var tripCode: String? = null,

    @field:SerializedName("Participant")
    var participant: ParticipantReschedule? = null,

    @field:SerializedName("Reschedule")
    var reschedule: RescheduleHotel? = null,

    @field:SerializedName("Attachment")
    var attachment: List<AttachmentItemReschedule?>? = null
)

data class RescheduleHotel(

    @field:SerializedName("BookingCode")
    var bookingCode: String? = null,

    @field:SerializedName("Type")
    var type: Int? = null,

    @field:SerializedName("ChangeCheckout")
    var changeCheckout: String? = null,

    @field:SerializedName("ChangeNotes")
    var changeNotes: String? = null,

    @field:SerializedName("ChangeCheckin")
    var changeCheckin: String? = null
)

