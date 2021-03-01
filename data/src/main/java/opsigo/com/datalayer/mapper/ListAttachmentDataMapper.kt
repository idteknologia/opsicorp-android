package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.cart.TripAttachmentItem
import opsigo.com.domainlayer.Mapper
import opsigo.com.domainlayer.model.summary.TripAttachmentItemModel

class ListAttachmentDataMapper : Mapper<List<TripAttachmentItem>, List<TripAttachmentItemModel>>() {

    override fun mapFrom(from: List<TripAttachmentItem>): List<TripAttachmentItemModel> {

        from.let {

            var tpModelList = it.map { data ->
                return@map TripAttachmentItemModel(
                        data.id.toString(),
                        data.tripPlanId.toString(),
                        data.description.toString(),
                        data.url.toString())
            }

            return tpModelList
        }

    }
}

