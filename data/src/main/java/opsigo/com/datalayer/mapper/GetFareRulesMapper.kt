package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.accomodation.flight.fare_rules.FareRulesResponseEntity
import opsigo.com.domainlayer.model.accomodation.flight.FareRulesModel
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel

class GetFareRulesMapper {
    fun mapper(response: String): ResultListFlightModel {
        val mData = ResultListFlightModel()
        val data = Serializer.deserialize(response,FareRulesResponseEntity::class.java)
        data.result?.forEach {
            val model = FareRulesModel()
            model.fareRulesCode = it?.code.toString()
            model.fareRulesName = it?.name.toString()
            model.fareRulesValues = it?.values as ArrayList<String>
            mData.dataFareRules.add(model)

        }
        return mData
    }
}