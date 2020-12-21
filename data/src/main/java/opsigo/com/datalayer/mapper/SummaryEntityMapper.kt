package opsigo.com.datalayer.mapper

import android.util.Log
import opsigo.com.datalayer.model.SummaryEntity
import opsigo.com.domainlayer.model.create_trip_plane.UploadModel
import opsigo.com.domainlayer.model.summary.*

class SummaryEntityMapper() {

    fun mapFrom(from: SummaryEntity): SummaryModel {

        val contact = from.contact
        val contactModel = ContactEntityDataMapper().mapFrom(contact)

        val summary = SummaryModel()
        summary.tripId          = from.id
        summary.type            = from.type
        summary.code            = from.code
        summary.purpose         = from.purpose
        summary.origin          = if (from.origin==null) "" else from.origin
        summary.originName      = if (from.originName==null) "" else from.originName
        summary.destination     = if (from.destination==null) "" else from.destination
        summary.destinationName = if (from.destinationView==null) "" else from.destinationView
        summary.startDate       = from.startDate
        summary.budgetId        = from.budgetId
        summary.budget          = from.totalBudget
        summary.returnDate      = from.returnDate
        summary.totalExpenditure = from.totalExpenditure
        summary.totalAllowance  = from.totalAllowance
        summary.remark          = from.remark
        summary.status          = from.status
        summary.statusView      = from.statusView
        summary.creationDate    = from.creationDateView
        summary.idUser          = from.contact.id
//        summary.employId        = from.contact.employeeId
        summary.creationDateView  = from.creationDateView
        summary.expiredRemaining  = from.timeLimitRemaining
        
        from.tripAttachments.forEachIndexed { index, tripAttachmentsItem ->
            val uplaodModel = UploadModel()
            uplaodModel.id = tripAttachmentsItem.id
            uplaodModel.url = tripAttachmentsItem.url
            uplaodModel.nameImage = tripAttachmentsItem.description
            uplaodModel.pathLocalImage = tripAttachmentsItem.description
            uplaodModel.statusUploaded = "success"
            summary.attactment.add(uplaodModel)
        }
        summary.contact         = contactModel

        summary.tripParticipantModels   = ListParticipantsDataMapper().mapFrom(from)

        return summary

    }

}