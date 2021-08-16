package opsigo.com.datalayer.mapper

import android.util.Log
import opsigo.com.datalayer.model.accomodation.hotel.search_hotel.CityItem
import opsigo.com.datalayer.model.accomodation.hotel.search_hotel.CountryByRouteEntity
import opsigo.com.datalayer.model.accomodation.hotel.search_hotel.CountryByRouteEntityItem
import opsigo.com.domainlayer.model.accomodation.hotel.CityHotelModel
import opsigo.com.domainlayer.model.accomodation.hotel.CountryHotel
import java.util.ArrayList

class CountryByRoutePertaminaMapper {

    fun mapping(countryByRouteEntity: Array<CountryByRouteEntityItem>?): ArrayList<CountryHotel> {
        val data = ArrayList<CountryHotel>()
        countryByRouteEntity?.forEach {
            val mData = CountryHotel()
            mData.countryName = it.countryName.toString()
            mData.isoCountryCode = it.isoCountryCode.toString()
            mData.cityHotelModel.addAll(listCityMapper(it.city,it.countryName.toString()))
            data.add(mData)
        }
        return data
    }

  /*  fun mapping(deserialize: CountryByRouteEntity): ArrayList<CountryHotel> {

        return data
    }
*/
    private fun listCityMapper(city: List<CityItem>,country:String): ArrayList<CityHotelModel> {
        val data = ArrayList<CityHotelModel>()
        city.forEach {
            val mData = CityHotelModel()
            mData.country  = country
            mData.cityName = it.cityName.toString()
            mData.idCity   = it.cityKey.toString()
            mData.searchByCityKey = it.searchByCityKey
            mData.lat      = it.latitude
            mData.long     = it.longitude
            data.add(mData)
        }
        return data
    }


}