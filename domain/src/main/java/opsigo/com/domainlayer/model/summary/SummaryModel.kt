package opsigo.com.domainlayer.model.summary

import opsigo.com.domainlayer.model.accomodation.flight.RoutesItemPertamina
import opsigo.com.domainlayer.model.create_trip_plane.UploadModel

data class SummaryModel(
        var tripId : String = "",
        var type : String = "",
        var idUser :String = "",
        var tripCode: String = "",
        var origin: String = "",
        var originName :String = "",
        var destination: String = "",
        var destinationName: String = "",
        var startDate: String = "",
        var returnDate: String = "",
        var remark: String? = null,
        var belongsToCollection: Any? = null,
        var budget: String = "",
        var budgetId : String = "",
        var expiredRemaining :String = "",
        var homepage: String? = null,
        var imdbId: String? = null,
        var creationDate :String= "",
        var overview: String? = null,
        var revenue: Int? = null,
        var runtime: Int? = null,
        var status: String = "",
        var statusView :String = "",
        var tagline: String = "",
        var purpose: String = "",
        var businessTripType: String = "",
        var totalExpenditure: String = "",
        var totalAllowance :String = "",

        var creationDateView : String = "",

        var allowance :String = "",
        var costCenter :String = "",
        var isDomestic :Boolean = false,

        var paymentStatus: String = "",
        var paymentStatusView : String = "",

        var contact: ContactModel = ContactModel(),
        var routes :ArrayList<RoutesItemPertamina> = ArrayList(),
        var attactment :ArrayList<UploadModel> = ArrayList(),
        var tripParticipantModels: List<TripParticipantsItemModel> = ArrayList()
)
