package opsigo.com.datalayer.request_model.create_trip_plane

import com.google.gson.annotations.SerializedName

data class SaveAsDraftRequestPertamina (
        @field:SerializedName("StartDate")
        var  startDate: String = "",

        @field:SerializedName("Remark")
        var remark: String = "",

        @field:SerializedName("ReturnDate")
        var  returnDate: String = "",

        @field:SerializedName("Origin")
        var  origin: String = "",

        @field:SerializedName("Destination")
        var  destination: String = "",

        @field:SerializedName("Type")
        var  type: Int = 0,

        @field:SerializedName("WbsNo")
        var  wbsNo: String = "",

        @field:SerializedName("Golper")
        var  golper: Int = 0,

        @field:SerializedName("TripParticipants")
        var  tripParticipants: List<TripParticipantsPertaminaItem> = ArrayList(),

        @field:SerializedName("Routes")
        var  routes: List<RoutesItem> = ArrayList(),

        @field:SerializedName("TravelAgentAccount")
        var  travelAgentAccount: String = "",

        @field:SerializedName("Purpose")
        var  purpose: String = "",

        @field:SerializedName("IsDomestic")
        var  isDomestic: Boolean = false,

        @field:SerializedName("WithPartner")
        var  withPartner: Boolean = false,

        @field:SerializedName("PartnerName ")
        var  partnerName: String = "",

        @field:SerializedName("BusinessTripType")
        var  businessTripType: String = "",

        @field:SerializedName("TripAttachments")
        var  tripAttachments: List<TripAttachmentsItemRequest> = ArrayList<TripAttachmentsItemRequest>(),

)