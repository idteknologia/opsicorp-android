package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.accomodation.flight.upcomming.UpcomingFlightEntity
import opsigo.com.domainlayer.model.UpcomingFlightModel


class UpcomingFlightEntityDataMapper{

    fun transform(upcomingEntity: UpcomingFlightEntity): UpcomingFlightModel {
        val data = UpcomingFlightModel()

        data.total         = upcomingEntity.total

        return data
    }
}