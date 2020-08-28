package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.TripFlightsItem
import opsigo.com.domainlayer.Mapper
import opsigo.com.domainlayer.model.summary.TripFlightsItemsModel

class ListTripFlightsDataMapper
constructor() : Mapper<List<TripFlightsItem>, List<TripFlightsItemsModel>>() {

    override fun mapFrom(from: List<TripFlightsItem>): List<TripFlightsItemsModel> {

        from.let {

            var tpModelList = it.map { data ->
                return@map TripFlightsItemsModel(
                        data.id,
                        data.tripItemId,
                        data.pnrId,
                        data.pnrCode,
                        data.airline,
                        data.origin,
                        data.destination
//                        ListSegmentDataMapper().mapFrom(data.segments),
//                        data.amount,
//                        data.isVoid
                )
            }

            return tpModelList
        }

    }
}