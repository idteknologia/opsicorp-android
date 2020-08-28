package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.create_trip_plane.UploadFileEntity
import opsigo.com.domainlayer.model.create_trip_plane.UploadModel

class UploadFileMapper {
    fun mapping(data:UploadFileEntity):UploadModel{
        val mData = UploadModel()
        mData.id = System.currentTimeMillis().toString()
        mData.nameImage = data.fileName
        mData.url       = data.url
        return mData
    }
}