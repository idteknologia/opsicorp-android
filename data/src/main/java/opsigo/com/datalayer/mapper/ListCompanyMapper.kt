package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.request_model.accomodation.hotel.list_company.ListCompanyEntity
import opsigo.com.domainlayer.model.accomodation.hotel.ListCompanyModel
import org.json.JSONArray
import java.util.ArrayList

class ListCompanyMapper {
    fun mapping(deserialize: String?): ArrayList<ListCompanyModel> {
        val data = ArrayList<ListCompanyModel>()
        val jsnArray = JSONArray(deserialize)
        for (i in 0 until jsnArray.length()){
            val mData = Serializer.deserialize(jsnArray.get(i).toString(),ListCompanyEntity::class.java)
            mData.options?.forEach {
                val model = ListCompanyModel()
                model.cityCode    = mData.groupCode.toString()
                model.countryCode = mData.countryCode.toString()
                model.latitude    = it?.latitude.toString()
                model.longitude   = it?.longitude.toString()
                model.city        = mData.groupName.toString()
                model.nameCompany = it?.name.toString()
                data.add(model)
            }
        }
        return data
    }
}