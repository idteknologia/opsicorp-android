package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.accomodation.hotel.city.CityHotelEntity
import opsigo.com.domainlayer.model.accomodation.hotel.CityHotelModel

class CityHotelMapper {
    fun mapping(data:CityHotelEntity):ArrayList<CityHotelModel>{
        val mData = ArrayList<CityHotelModel>()
        data.data.forEach {
            val model  = CityHotelModel()
            model.cityName = it.cityName
            model.idCity   = it.cityKey
            mData.add(model)
        }
        return mData
    }
}