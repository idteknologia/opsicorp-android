package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.accomodation.hotel.city.CityHotelEntity
import opsigo.com.datalayer.model.accomodation.hotel.country.CountryHotelEntity
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel

class CountryHotelMapper {
    fun mapping(data:CountryHotelEntity):ArrayList<SelectNationalModel>{
        val mData = ArrayList<SelectNationalModel>()
        data.data.forEach {
            val model  = SelectNationalModel()
            model.name = it.countryName
            model.id   = it.isoCountryCode
            mData.add(model)
        }
        return mData
    }
}