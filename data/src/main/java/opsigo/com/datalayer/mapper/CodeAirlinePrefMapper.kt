package opsigo.com.datalayer.mapper

import opsigo.com.domainlayer.model.accomodation.flight.airline_code.AirlineCodeCompanyModel

class CodeAirlinePrefMapper {
    fun mapping(string: String): AirlineCodeCompanyModel {
        val data = Serializer.deserialize(string,AirlineCodeCompanyModel::class.java)
        return data
    }
}