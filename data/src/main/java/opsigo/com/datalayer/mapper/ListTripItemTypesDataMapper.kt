package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.TripItemTypesItem
import opsigo.com.domainlayer.Mapper
import opsigo.com.domainlayer.model.summary.TripItemTypesModel

class ListTripItemTypesDataMapper
constructor() : Mapper<List<TripItemTypesItem>, List<TripItemTypesModel>>() {

    override fun mapFrom(from: List<TripItemTypesItem>): List<TripItemTypesModel> {

        from.let {

            var tpModelList = it.map { data ->
                return@map TripItemTypesModel(
                        data.type,
                        ListTripItemsDataMapper().mapFrom(data.tripItems),
                        data.id,
                        data.name)
            }

            return tpModelList
        }

    }
}