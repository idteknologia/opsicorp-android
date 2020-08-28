package opsigo.com.datalayer.mapper
import opsigo.com.datalayer.model.accomodation.flight.prefered.PreferedAirlineEntity
import opsigo.com.domainlayer.model.accomodation.flight.CodeAirLineModel
import java.util.ArrayList

class CodeAirlineMapper {
    fun mapping(response: String): ArrayList<CodeAirLineModel> {
        val data  = ArrayList<CodeAirLineModel>()
        val model = Serializer.deserialize(response,PreferedAirlineEntity::class.java)
        model.list.forEach {
            val mData = CodeAirLineModel()
            mData.codeAirline = it.codeApi
            mData.nameAirline = it.airlineName.toString()
            mData.iataCode    = it.iataCode.toString()
            mData.isInternational = it.isInternational.toString()
            mData.isDomestik  = it.isDomestik.toString()
            mData.isPassportRequired = it.isPassportRequired.toString()
            mData.isGds       = it.isGds.toString()
            data.add(mData)
        }
        return data
    }

}