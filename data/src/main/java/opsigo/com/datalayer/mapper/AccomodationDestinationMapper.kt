package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.accomodation.train.DestinationTrainEntity
import opsigo.com.domainlayer.model.DestinationAccomodationModel
import org.json.JSONArray
import org.json.JSONObject

class AccomodationDestinationMapper {

    fun mapping(list: JSONArray):ArrayList<DestinationAccomodationModel>{
        val data = ArrayList<DestinationAccomodationModel>()
        for (i in 0 until list.length()){
            val model = Serializer.deserialize(list[i].toString(),DestinationTrainEntity::class.java)
            val mData = DestinationAccomodationModel()
            mData.nameCity   = model.text
            mData.code       = model.value
            data.add(mData)
        }
        return data
    }
}