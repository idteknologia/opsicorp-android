package com.opsigo.travelaja.module.item_custom.galery

import android.app.Activity
import android.content.Context
import android.content.Intent
import opsigo.com.domainlayer.model.accomodation.hotel.GaleryModel

object MyGalery {

    var CODE_GALERY = 1909
    var imagesGalery = ArrayList<GaleryModel>()

    fun show(context: Context){
        (context as Activity).startActivityForResult(Intent(context,MyGaleryActivity::class.java),CODE_GALERY)
    }

    fun setData(data:ArrayList<GaleryModel>):MyGalery{
        imagesGalery = data
        return MyGalery
    }

    fun result(requestCode: Int, resultCode: Int, data: Intent?,callback:CallbackGalery){
        when(requestCode){
            CODE_GALERY -> {
                if (resultCode == Activity.RESULT_OK){
                    val model = GaleryModel()
                    model.imageUri = data?.getStringExtra("image").toString()
                    model.description = data?.getStringExtra("title").toString()
                    model.position = data?.getIntExtra("position",0)!!
                    callback.result(model)
                }
                else{
                    callback.cancelled()
                }
            }
        }
    }

}