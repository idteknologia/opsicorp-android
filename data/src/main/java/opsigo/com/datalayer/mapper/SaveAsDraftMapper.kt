package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.SummaryEntity
import opsigo.com.datalayer.model.create_trip_plane.save_as_daft.SaveAsDraftEntity
import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel

class SaveAsDraftMapper {
    fun mapping(data:SaveAsDraftEntity): SuccessCreateTripPlaneModel {
        val mData = SuccessCreateTripPlaneModel()
        mData.originId     = data.origin
        mData.originName = data.originName
        mData.status     = data.statusView
        mData.tripCode   = data.code
        mData.idTripPlant = data.id
        mData.purpose    = data.purpose
        mData.destinationId   = data.destination
        mData.destinationName = data.destinationName
        mData.createDate    = data.creationDate
        mData.createDateView    = data.creationDateView
        mData.startDate     = data.startDate
        mData.endDate       = data.returnDate
        mData.timeExpired   = data.expiredIn
        mData.costCenter    = data.costCenterId ?: ""
        mData.buggetId      = data.budgetId ?: ""
        return mData
    }

    fun mappingSuccesCheckout(responseString: String?): SuccessCreateTripPlaneModel {
        val data = Serializer.deserialize(responseString!!, SummaryEntity::class.java)
        val mData = SuccessCreateTripPlaneModel()
        mData.idTripPlant = data.id
        mData.tripCode    = data.code
        return mData
    }
}