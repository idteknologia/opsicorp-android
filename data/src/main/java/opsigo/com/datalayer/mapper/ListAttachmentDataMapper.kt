package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.TripAttachmentItem
import opsigo.com.domainlayer.Mapper
import opsigo.com.domainlayer.model.summary.TripAttachmentItemModel

class ListAttachmentDataMapper
constructor() : Mapper<List<TripAttachmentItem>, List<TripAttachmentItemModel>>() {

    override fun mapFrom(from: List<TripAttachmentItem>): List<TripAttachmentItemModel> {

        from.let {

            var tpModelList = it.map { data ->
                return@map TripAttachmentItemModel(
                        data.id,
                        data.tripPlanId,
                        data.description,
                        data.url)
            }

            return tpModelList
        }

    }
}

