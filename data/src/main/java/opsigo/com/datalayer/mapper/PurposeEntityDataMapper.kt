package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.PurposeEntity
import opsigo.com.domainlayer.model.PurposeModel
import org.json.JSONArray


class PurposeEntityDataMapper{

    fun transform(configEntity: JSONArray): ArrayList<PurposeModel> {
        val data = ArrayList<PurposeModel>()
        data.clear()
        for (i in 0 until configEntity.length()){
            val model = Serializer.deserialize(configEntity.getString(i),PurposeEntity::class.java)
            val mData = PurposeModel()
            mData.value = model.value
            data.add(mData)
        }
        return data
    }
}