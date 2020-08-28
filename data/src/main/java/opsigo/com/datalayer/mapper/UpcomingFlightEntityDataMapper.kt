package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.UpcomingFlightEntity
import opsigo.com.domainlayer.model.UpcomingFlightModel


class UpcomingFlightEntityDataMapper{

    fun transform(upcomingEntity: UpcomingFlightEntity): UpcomingFlightModel {
        val data = UpcomingFlightModel()

        data.total         = upcomingEntity.total

        return data
    }
}