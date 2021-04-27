package opsigo.com.datalayer.mapper

import opsigo.com.domainlayer.model.travel_request.TypeActivityTravelRequestModel
import org.json.JSONArray

class TypeActivityMapper {
    fun mapping(response:String):ArrayList<TypeActivityTravelRequestModel>{
        val data = ArrayList<TypeActivityTravelRequestModel>()
        val jSon = JSONArray(response)
        for (i in 0 until jSon.length()){
            val mData = jSon.getJSONObject(i).getString("Value")
            data.add(TypeActivityTravelRequestModel(mData))
        }
        return data
    }
}