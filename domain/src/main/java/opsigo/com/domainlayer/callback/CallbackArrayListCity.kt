package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel

interface CallbackArrayListCity {
    fun successLoad(data: ArrayList<SelectNationalModel>)
    fun failedLoad(message:String)
}