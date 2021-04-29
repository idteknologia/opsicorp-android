package com.opsigo.travelaja.utility

import android.content.Context
import com.opsigo.travelaja.R
import java.io.IOException

object Utils {
    fun handleErrorMessage(context : Context,t : Throwable, callback:(errorString : String) -> Unit){
        if (t is IOException){
            callback.invoke(context.getString(R.string.no_internet))
        }else {
            callback.invoke("Maintenance")
        }
    }
}