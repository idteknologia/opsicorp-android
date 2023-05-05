package opsigo.com.datalayer.mapper

import opsigo.com.domainlayer.model.accomodation.flight.RoutesItemPertamina
import opsigo.com.domainlayer.model.create_trip_plane.UploadModel
import opsigo.com.datalayer.model.cart.SummaryEntity
import opsigo.com.domainlayer.model.create_trip_plane.ParticipantPertamina
import opsigo.com.domainlayer.model.summary.*

class SummaryEntityMapper() {

    fun mapFrom(from: SummaryEntity): SummaryModel {

        val summary = SummaryModel()
        summary.tripId          = from.id.toString()
        summary.type            = from.type
        summary.tripCode          = from.code.toString()
        summary.purpose         = if (from.purpose==null) "" else from.purpose.toString()
        summary.businessTripType  = if (from.businessTripType==null) "" else from.businessTripType.toString()
        summary.origin          = if (from.origin==null) "" else from.origin
        summary.originName      = if (from.originName==null) "" else from.originName
        summary.destination     = if (from.destinationName==null) "" else from.destinationName
        summary.destinationName = if (from.destinationView==null) "" else from.destinationView
        summary.startDate       = from.startDate.toString()
        summary.budgetId        = from.budgetId.toString()
        summary.budget          = from.totalBudget.toString()
        summary.returnDate      = from.returnDate.toString()
        summary.totalExpenditure = from.totalExpenditure.toString()
        summary.totalAllowance  = from.totalAllowance.toString()
        summary.totalPayment    = from.totalPayment.toString()
        summary.remark          = from.remark.toString()
        summary.status          = from.status.toString()
        summary.statusView      = from.statusView.toString()
        summary.creationDate    = from.creationDateView.toString()
        summary.idUser          = from.contact?.id.toString()
//        summary.employId        = from.contact.employeeId
        summary.creationDateView  = from.creationDateView.toString()
        summary.expiredRemaining  = from.timeLimitRemaining.toString()
        summary.trnNumber         = from.trnNumber.toString()
        summary.isDomestic        = from.isDomestic
        summary.isBookAfterApprove = from.isBookAfterApprove
        summary.isApproval          = from.isApproval
        summary.isPersonalTrip      = from.isPersonalTrip
        summary.isPrivateTrip       = from.isPrivateTrip
        summary.isChangeTrip        = from.isChangeTrip
        summary.nonCbt        = from.nonCbt
        summary.paymentStatus     = from.paymentStatus.toString()
        summary.paymentStatusView = from.paymentStatusView.toString()
        summary.isTripPartner = from.isWithPartner
        summary.parterName = from.partnerName.toString()
        summary.coverLatter = from.coverLetterUrl.toString()


        from.tripAttachments?.forEachIndexed { index, tripAttachmentsItem ->
            val uplaodModel = UploadModel()
            uplaodModel.id = tripAttachmentsItem.id.toString()
            uplaodModel.url = tripAttachmentsItem.url.toString()
            uplaodModel.nameImage = tripAttachmentsItem.description.toString()
            uplaodModel.pathLocalImage = tripAttachmentsItem.description.toString()
            uplaodModel.statusUploaded = "success"
            summary.attactment.add(uplaodModel)
        }

        from.tripParticipants?.forEachIndexed { index, tripParticipantsItem ->
            val tripParticipant = ParticipantPertamina()
            tripParticipant.estFlight = tripParticipantsItem?.estFlight!!.toInt()
            tripParticipant.estHotel = tripParticipantsItem.estHotel.toInt()
            tripParticipant.estTransportation = tripParticipantsItem.estTransportation.toInt()
            tripParticipant.estAllowance = tripParticipantsItem.estAllowance.toInt()
            tripParticipant.estAllowanceEvent = tripParticipantsItem.estAllowanceEvent.toInt()
            tripParticipant.estLaundry = tripParticipantsItem.estLaundry.toInt()
            tripParticipant.estTotal = tripParticipantsItem.estTotal.toInt()
            tripParticipant.email = tripParticipantsItem.email.toString()
            tripParticipant.positionName = tripParticipantsItem.positionName.toString()
            tripParticipant.costCenterCode = tripParticipantsItem.costCenterCode.toString()
            tripParticipant.costCenterName = tripParticipantsItem.costCenterDefaultName.toString()
            summary.tripParticipantItem.add(tripParticipant)
        }

        from.routes.forEachIndexed { index, routesItem ->
            val routesItinerary = RoutesItemPertamina()
            if (routesItem != null) {
                routesItinerary.transportation = routesItem.transportation
                routesItinerary.origin = routesItem.origin.toString()
                routesItinerary.destination = routesItem.destination.toString()
                routesItinerary.departureDate = routesItem.departureDate.toString()
                routesItinerary.departureDateView = routesItem.departureDateView.toString()
                summary.routes.add(routesItinerary)
            }
        }


        val contact = from.contact
        val contactModel = ContactEntityDataMapper().mapFrom(contact)
        summary.contact         = contactModel
        summary.tripParticipantModels   = ListParticipantsDataMapper().mapFrom(from)

        return summary

    }

}