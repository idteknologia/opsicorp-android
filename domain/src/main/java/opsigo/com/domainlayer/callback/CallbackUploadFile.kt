package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.create_trip_plane.UploadModel

interface CallbackUploadFile {
    fun successLoad(data: UploadModel)
    fun failedLoad(message:String)
}