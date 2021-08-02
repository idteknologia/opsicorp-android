package com.mobile.travelaja.utility

import android.content.Context
import com.mobile.travelaja.R
import net.openid.appauth.AuthorizationException
import retrofit2.HttpException
import java.io.IOException
import java.text.DecimalFormat
import java.text.NumberFormat

object Utils {
    const val EMPTY = "Empty"
    const val ISNULL = "DATA NULL"
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
            callback.invoke(t.message?:"not description")
        }
    }

    @JvmStatic
    fun formatCurrency(value : Number) : String{
        try {
            val format = NumberFormat.getCurrencyInstance()
            val symbol = (format as DecimalFormat).decimalFormatSymbols
            symbol.currencySymbol = ""
            format.minimumFractionDigits = 0
            format.decimalFormatSymbols = symbol
            return  format.format(value)
        }catch (t : Throwable){
            return ""
        }
    }

    @JvmStatic
    fun doubleParse(value: Number?) : Number? {
        if (value != null){
            val format = DecimalFormat("###.###")
            return format.parse(value.toString())
        }else {
            return value
        }
    }
}