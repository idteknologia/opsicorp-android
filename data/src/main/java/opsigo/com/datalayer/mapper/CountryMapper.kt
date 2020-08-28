package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.general.CountryEntity
import opsigo.com.domainlayer.model.signin.CountryModel

class CountryMapper {
    fun mapping(data: CountryEntity):ArrayList<CountryModel>{
        val mData = ArrayList<CountryModel>()
        mData.clear()
        data.data.forEachIndexed { index, resultItemCountry ->
            val model = CountryModel()
            model.id        = resultItemCountry.code
            model.name      = resultItemCountry.name

            if(resultItemCountry.callCode == null) {
                model.callCode = ""
            }else{
                model.callCode = resultItemCountry.callCode
            }
            mData.add(model)
        }
        return mData
    }
}