package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.AirlinePreferenceEntity
import opsigo.com.domainlayer.model.AirlinePreferenceModel


class AirlinePreferenceEntityDataMapper{

    fun transform(upcomingEntity: AirlinePreferenceEntity): AirlinePreferenceModel {
        val data = AirlinePreferenceModel()

        //data.value         = upcomingEntity.airlineListEntity
        //data.value         = upcomingEntity.airlineListEntity.get(0).value

        return data
    }
}