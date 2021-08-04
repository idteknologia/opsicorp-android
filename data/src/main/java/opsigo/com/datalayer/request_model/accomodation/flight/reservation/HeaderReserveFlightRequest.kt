package opsigo.com.datalayer.request_model.accomodation.flight.reservation

import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.request_model.create_trip_plane.TripAttachmentsItemRequest
import opsigo.com.datalayer.request_model.reservation.TripParticipantsItem

data class HeaderReserveFlightRequest(

        //@field:SerializedName("TripParticipants")
        //val tripParticipants: List<TripParticipantsItem?>? = null,
        @field:SerializedName("Remark")
        var remark: String = "",

        @field:SerializedName("TrnNumber")
        var trnNumber: String = "",

        @field:SerializedName("StartDate")
        var startDate: String = "",

        @field:SerializedName("ReturnDate")
        var returnDate: String = "",

        @field:SerializedName("Status")
        var status: Int = 0,

        @field:SerializedName("IsBookAfterApprove")
        var isBookAfterApprove: Boolean = false,

        @field:SerializedName("IsPrivateTrip")
        var isPrivateTrip: Boolean = false,

        @field:SerializedName("Origin")
        var origin: String = "",

        @field:SerializedName("Destination")
        var destination: String = "",

        @field:SerializedName("Type")
        var type: Int = 0,

        @field:SerializedName("TripParticipants")
        var tripParticipants: List<TripParticipantsItem> = ArrayList(),

        @field:SerializedName("TravelAgentAccount")
        var travelAgentAccount: String = "",

        @field:SerializedName("Id")
        var idTripPlan: String = "",

        @field:SerializedName("Code")
        var codeTripPlan: String = "",

        @field:SerializedName("BusinessTripType")
        var businessTripType: String = "",

        @field:SerializedName("Purpose")
        var purpose: String = "",

        @field:SerializedName("BudgetId")
        var budgetId: String = "",

        @field:SerializedName("IsDomestic")
        var isDomestic: Boolean = false,

        @field:SerializedName("NonCbt ")
        var nonCbt: Boolean = false,

        @field:SerializedName("WbsNo")
        var wbsNo: String = "",

        @field:SerializedName("WithPartner")
        var withPartner: Boolean = false,

        @field:SerializedName("PartnerName")
        var partnerName: String = "",

        @field:SerializedName("Golper")
        var golper: Int = 0,

        @field:SerializedName("Routes")
        var routes: ArrayList<RoutestRequest> = ArrayList(),

        )