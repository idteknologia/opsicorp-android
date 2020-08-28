package opsigo.com.domainlayer.model.summary

import opsigo.com.domainlayer.model.aprover.ParticipantModelDomain

data class TripParticipantsItemModel (
        var id: String = "",
        var totalTripPaidAirline: String = "",
        var totalTripPaidTrain: String = "",
        var totalTripPaidHotel: String = "",
        var costCenterCode: String = "",
        var costCenterName: String = "",
        var budgetCode: String = "",
        var budgetName: String = "",
        var budgetId  : String = "",
        var costId    : String = "",
        var tripItemTypes: List<TripItemTypesModel> = ArrayList(),
        var isApproveForm: Boolean = false,
        var status: String,
        var statusView: String,
        var fullName: String,
        var jobtitle:String,
        var employId:String,
        var dataApproval : ArrayList<ParticipantModelDomain>,
        var itemFlightModel: List<ItemFlightModel>,
        var itemTrainModel: List<ItemTrainModel>,
        var itemHotelModel: List<ItemHotelModel>
)