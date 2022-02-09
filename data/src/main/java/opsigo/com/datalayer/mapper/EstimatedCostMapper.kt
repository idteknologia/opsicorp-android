package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.travel_request.EstimatedCostEntity
import opsigo.com.domainlayer.model.travel_request.EstimatedCostTravelRequestModel

class EstimatedCostMapper {
    fun mapping(response:EstimatedCostEntity):EstimatedCostTravelRequestModel{
        val data = EstimatedCostTravelRequestModel()
        data.estAllowance = response.estAllowance
        data.estAllowanceDaily = response.estAllowanceDaily
        data.estAllowanceMeal = response.estAllowanceMeal
        data.estAllowanceTransport = response.estAllowanceTransport
        data.estMiscellaneous = response.estAllowanceMiscellaneous
        data.estAllowanceEvent = response.estAllowanceEvent
        data.estFlight    = response.estFlight
        data.estHotel     = response.estHotel
        data.estLaundry   = response.estLaundry
        data.total        = response.total
        data.estTransportation = response.estTransportation
        return data
    }
}