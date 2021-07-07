package com.mobile.travelaja.utility

import android.content.Context
import com.mobile.travelaja.R
import net.openid.appauth.AuthorizationException
import retrofit2.HttpException
import java.io.IOException

object Utils {
    fun handleErrorMessage(context : Context,t : Throwable, callback:(errorString : String) -> Unit){
        if (t is IOException){
            callback.invoke(context.getString(R.string.no_internet))
        }else if (t is AuthorizationException) {
           if (t.code == 1){
               callback.invoke("")
           }else {
               callback.invoke(t.errorDescription ?:"not description")
           }
        }else if(t is HttpException){
            callback.invoke(t.localizedMessage)
        }else {
            callback.invoke("Maintenance")
        }
    }
}