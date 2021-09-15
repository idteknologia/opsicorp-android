package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.travel_request.ChangeTripEntity
import opsigo.com.domainlayer.model.accomodation.flight.RoutesItemPertamina
import opsigo.com.domainlayer.model.create_trip_plane.UploadModel
import opsigo.com.domainlayer.model.travel_request.ChangeTripModel

class ChangeTripMapper {
    fun mapFrom(from: ChangeTripEntity): ChangeTripModel {

        val summary = ChangeTripModel()
        summary.tripId          = from.model?.id.toString()
        summary.tripCode          = from.model?.code.toString()
        summary.purpose         = from.model?.purpose ?: ""
        summary.businessTripType  = from.model?.businessTripType ?: ""
        summary.startDate       = from.model?.startDate.toString()
        summary.returnDate      = from.model?.returnDate.toString()
        summary.remark          = from.model?.remark.toString()
        summary.trnNumber         = from.model?.trnNumber.toString()
        summary.isDomestic        = from.model!!.isDomestic
        summary.isBookAfterApprove = from.model.isBookAfterApprove
        summary.isApproval          = from.model.isApproval
        summary.isPrivateTrip       = from.model.isPrivateTrip
        summary.isChangeTrip        = from.model.isChangeTrip
        summary.isCbt               = from.model.nonCbt
        summary.isTripPartner = from.model.withPartner
        summary.parterName = from.model.partnerName.toString()
        summary.isDisableCbt = from.isDisableCbt
        summary.tripCodeOld = from.model.tripCodeOld.toString()
        summary.tripIdOld = from.model.tripIdOld.toString()

        from.model.tripAttachments?.forEachIndexed { index, tripAttachmentsItem ->
            val uplaodModel = UploadModel()
            if (tripAttachmentsItem != null) {
                uplaodModel.id = tripAttachmentsItem.id.toString()
                uplaodModel.url = tripAttachmentsItem.url.toString()
                uplaodModel.nameImage = tripAttachmentsItem.description.toString()
                uplaodModel.pathLocalImage = tripAttachmentsItem.description.toString()
                uplaodModel.statusUploaded = "success"
                summary.attactment.add(uplaodModel)
            }
        }

        from.model.routes?.forEachIndexed { index, routesItem ->
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

        return summary
    }
}