package opsigo.com.datalayer.mapper

import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import org.json.JSONArray

class CityTripMapper {
    fun mapper(jsonArray:JSONArray):ArrayList<SelectNationalModel>{
        val data = ArrayList<SelectNationalModel>()
        for (i in 0 until jsonArray.length()){
            val model = SelectNationalModel()
            model.name = jsonArray.getJSONObject(i).getString("Name")
            model.id   = jsonArray.getJSONObject(i).getString("Code")
            data.add(model)
        }
        return data
    }
}