package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.SegmentsItem
import opsigo.com.domainlayer.Mapper
import opsigo.com.domainlayer.model.summary.SegmentsItemModel

class ListSegmentDataMapper
constructor() : Mapper<List<SegmentsItem>, List<SegmentsItemModel>>() {

    override fun mapFrom(from: List<SegmentsItem>): List<SegmentsItemModel> {

        from.let {

            var tpModelList = it.map { data ->
                return@map SegmentsItemModel(
                        data.id
//                        data.airlineName,
//                        data.airlineImageUrl,
//                        data.category,
//                        data.origin,
//                        data.destination
//                        data.isComply
                )
            }

            return tpModelList
        }

    }
}