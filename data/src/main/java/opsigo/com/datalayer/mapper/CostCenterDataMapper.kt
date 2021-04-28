package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.create_trip_plane.trip_plan.CostCenterEntity
import opsigo.com.domainlayer.model.CostCenterModel
import org.json.JSONArray

class CostCenterDataMapper {
    fun transform(jsonArray: JSONArray):ArrayList<CostCenterModel>{
        val data = ArrayList<CostCenterModel>()
        for (i in 0 until jsonArray.length()){
            val model = Serializer.deserialize(jsonArray[i].toString(), CostCenterEntity::class.java)
            val mData = CostCenterModel()
            mData.idCost   = model.id
            mData.idBudget = model.budgetId
            mData.value    = model.amount
            mData.code     = model.code
            data.add(mData)
        }
        return data
    }
}