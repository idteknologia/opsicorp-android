package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.travel_request.TypeActivityTravelRequestEntity
import opsigo.com.domainlayer.model.travel_request.TypeActivityTravelRequestModel

class TypeActivityMapper {
    fun mapping(response:TypeActivityTravelRequestEntity):ArrayList<TypeActivityTravelRequestModel>{
        val data = ArrayList<TypeActivityTravelRequestModel>()
        if (!response.typeActivityTravelRequestEntity.isNullOrEmpty()){
            response.typeActivityTravelRequestEntity.forEach {
                val mData = TypeActivityTravelRequestModel(it?.value.toString())
                data.add(mData)
            }
        }
        return data
    }
}