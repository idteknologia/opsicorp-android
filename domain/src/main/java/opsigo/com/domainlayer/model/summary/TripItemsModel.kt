package opsigo.com.domainlayer.model.summary

data class TripItemsModel (
        var id: String? = null,
        var amount: Double? = null,
        var status: Int? = null,
//        var isSecondaryLevel: Boolean? = null,
//        var isRemoved: Boolean? = null,
//        var isDownloadPnr: Boolean? = null,
//        var tripPlanId: String? = null,
//        var isManual: Boolean? = null,
//        var tripTrains: List<Any?>? = null,
//        var travelAgentAccount: String? = null,
//        var hasConfirmed: Boolean? = null,
//        var itemType: Int? = null,

//        var tripMemberId: String? = null,
//        var reasonCode: Any? = null,
//        var hasCostCenterUpdated: Boolean? = null,
//        var tripHotels: List<Any?>? = null,
        var tripFlights: List<TripFlightsItemsModel>,//? = null,
//        var isViolatedHotelRules: Boolean? = null,
//        var flightType: Int? = null,
        var employeeId: String? = null,
        var employeeName: String? = null
)