package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.accomodation.hotel.city.CityHotelEntity
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel

class CityHotelMapper {
    fun mapping(data:CityHotelEntity):ArrayList<SelectNationalModel>{
        val mData = ArrayList<SelectNationalModel>()
        data.data.forEach {
            val model  = SelectNationalModel()
            model.name = it.cityName
            model.id   = it.cityKey
            mData.add(model)
        }
        return mData
    }
}