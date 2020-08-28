package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.signin.ResponseVersionEntity
import opsigo.com.datalayer.model.signin.version.RespVersionEntity

import opsigo.com.domainlayer.model.signin.CheckVersionModel

class CheckVersionMapper {
    fun mapper(data: ResponseVersionEntity):CheckVersionModel{
        val mData = CheckVersionModel()

        mData.status = data.statusCode
        mData.numberVersion = data.versionCode
        mData.message       = data.message

        return mData
    }

    fun mapping(data: RespVersionEntity):CheckVersionModel {

        var vModel = CheckVersionModel()

        vModel.enable           = data.data.get(0).enabled
        vModel.status           = data.data.get(0).statusCode
        vModel.numberVersion    = data.data.get(0).versionCode
        vModel.message          = data.data.get(0).message

        return vModel

    }

}