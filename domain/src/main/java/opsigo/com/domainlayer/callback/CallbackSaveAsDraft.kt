package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.create_trip_plane.save_as_draft.SuccessCreateTripPlaneModel


interface CallbackSaveAsDraft {
    fun successLoad(data: SuccessCreateTripPlaneModel)
    fun failedLoad(message:String)
}