package opsigo.com.datalayer.mapper

import android.util.Log
import opsigo.com.domainlayer.model.accomodation.hotel.NearbyAirportModel
import org.json.JSONArray

class AirportMapper {
    fun mapper(data:JSONArray):ArrayList<NearbyAirportModel>{
        val mData = ArrayList<NearbyAirportModel>()
        for (i in 0 until data.length()){
            val model = NearbyAirportModel()
            model.nameAirport = data.getJSONObject(i).getString("AirportName")
            model.cityName    = data.getJSONObject(i).getString("CityName")
            model.nameCountry = data.getJSONObject(i).getString("CountryName")
            model.latitude    = data.getJSONObject(i).getDouble("Latitude").toString()
            model.longitude   = data.getJSONObject(i).getDouble("Longitude").toString()
            model.id          = data.getJSONObject(i).getString("Id")
            model.code        = data.getJSONObject(i).getString("Code")
            model.cityCode    = data.getJSONObject(i).getString("CityCode")
            model.countryCode = data.getJSONObject(i).getString("CountryCode")
            mData.add(model)
        }
        return mData
    }
}