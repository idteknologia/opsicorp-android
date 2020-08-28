package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.accomodation.flight.destination.DestinationAirportEntity
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import org.json.JSONArray

class DestinationFlightMapper {

    fun mapper(data : JSONArray):ArrayList<SelectNationalModel>{
        val mData = ArrayList<SelectNationalModel>()
        mData.clear()
        for (i in 0 until data.length()){
            val model = Serializer.deserialize(data.getString(i),DestinationAirportEntity::class.java)
            val dataCity = SelectNationalModel()
            dataCity.name = model.cityName+"-"+model.cityCode
            dataCity.id   = model.code
            mData.add(dataCity)
        }
        return mData
    }
}