package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.approval.list_approval.TotalApprovalEntity
import opsigo.com.domainlayer.model.aprover.TotalApprovalModel
import org.json.JSONArray

class TotalApprovalMapper {
    fun mapping(data: JSONArray):ArrayList<TotalApprovalModel>{
        val mData = ArrayList<TotalApprovalModel>()
        mData.clear()
        for (i in 0 until data.length()){
            val jsonModel = Serializer.deserialize(data[i].toString(), TotalApprovalEntity::class.java)
            val model = TotalApprovalModel()
            model.statusId = jsonModel.status
            model.statusName = jsonModel.statusView
            model.total      = jsonModel.total
            mData.add(model)
        }
        return mData
    }
}