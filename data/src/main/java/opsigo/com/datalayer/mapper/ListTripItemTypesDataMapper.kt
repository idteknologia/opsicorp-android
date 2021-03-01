package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.cart.TripItemTypesItem
import opsigo.com.domainlayer.Mapper
import opsigo.com.domainlayer.model.summary.TripItemTypesModel
import java.util.ArrayList

class ListTripItemTypesDataMapper : Mapper<List<TripItemTypesItem?>?, List<TripItemTypesModel>>() {

    override fun mapFrom(from: List<TripItemTypesItem?>?): List<TripItemTypesModel> {

        from.let {

            var tpModelList = it?.map { data ->
                return@map TripItemTypesModel(
                        data?.type,
                        ListTripItemsDataMapper().mapFrom(data?.tripItems),
                        data?.id,
                        data?.name)
            }

            if (!tpModelList.isNullOrEmpty()){
                return tpModelList
            }
            return ArrayList()
        }

    }
}