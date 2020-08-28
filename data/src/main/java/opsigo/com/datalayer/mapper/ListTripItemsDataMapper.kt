package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.TripItemsItem
import opsigo.com.domainlayer.Mapper
import opsigo.com.domainlayer.model.summary.TripItemsModel

class ListTripItemsDataMapper
constructor() : Mapper<List<TripItemsItem>, List<TripItemsModel>>() {

    override fun mapFrom(from: List<TripItemsItem>): List<TripItemsModel> {

        from.let {

            var tpModelList = it.map { data ->
                return@map TripItemsModel(
                        data.id,
                        data.amount,
                        data.status,
                        ListTripFlightsDataMapper().mapFrom(data.tripFlights),
                        data.employeeId,
                        data.employeeName
                )
            }

            return tpModelList
        }

    }
}