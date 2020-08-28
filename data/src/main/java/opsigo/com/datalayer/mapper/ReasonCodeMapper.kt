package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.accomodation.reasoncode.ResponseReasonCodeEntity
import opsigo.com.domainlayer.model.accomodation.ReasonCodeModel

class ReasonCodeMapper {
    fun mapping(responseReasonCodeEntity: ResponseReasonCodeEntity):ArrayList<ReasonCodeModel>{

        val data = ArrayList<ReasonCodeModel>()
        data.clear()
        if (responseReasonCodeEntity.total>0){
            responseReasonCodeEntity.data.forEach {
                val model = ReasonCodeModel()
                model.idReason = it.id
                model.codeBrief   = it.codeBrief
                model.description = it.description
                data.add(model)
            }
        }

        return data
    }
}