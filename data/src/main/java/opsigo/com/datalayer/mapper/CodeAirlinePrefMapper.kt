package opsigo.com.datalayer.mapper

import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.model.accomodation.flight.ailine_code.AirlineCodeEntity
import opsigo.com.domainlayer.model.accomodation.flight.airline_code.ListScheduleItem
import opsigo.com.domainlayer.model.accomodation.flight.airline_code.AirlineCodeCompanyModel

class CodeAirlinePrefMapper {
    fun mapping(string: String): AirlineCodeCompanyModel {
        val mData = AirlineCodeCompanyModel()
        val data = Serializer.deserialize(string,AirlineCodeEntity::class.java)
        data.listSchedule?.forEach {
            val model = ListScheduleItem()
            model.flightTripType = it?.flightTripType
            model.preferredCarriers = it?.preferredCarriers
            model.isShowPolicy = it?.isShowPolicy
            model.requestId = it?.requestId
            model.cabinClassList = it?.cabinClassList
            model.infant = it?.infant
            it?.routes?.forEachIndexed { index, routesItem ->
                val m = opsigo.com.domainlayer.model.accomodation.flight.airline_code.RoutesItem()
                m.departDate = routesItem?.departDate
                m.destination = routesItem?.destination
                m.origin  = routesItem?.origin
                model.routes?.add(m)
            }
            model.preferredAirlines = it?.preferredAirlines
            model.jobTitleId = it?.jobTitleId
            model.compCode= it?.compCode
            model.travelAgent= it?.travelAgent
            model.adult= it?.adult
            model.child= it?.child
            mData.listSchedule.add(model)
        }
        return mData
    }
}