package com.mobile.travelaja.utility

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mobile.travelaja.R
import com.squareup.picasso.Picasso
import net.openid.appauth.AuthorizationException
import retrofit2.HttpException
import java.io.IOException
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

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

    fun getDurationTime(startTime : String? , endTime : String ?) : String? {
        try {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val sTime = format.parse(startTime)
            val eTime = format.parse(endTime)
            val diffTime = eTime.time - sTime.time
            val secondsInMilli = 1000
            val minutesInMilli = secondsInMilli * 60
            val hoursInMilli = minutesInMilli * 60
            val daysInMilli = hoursInMilli * 24
            val elapsedDays = diffTime / daysInMilli
            val elapsedHours = (diffTime % daysInMilli) / hoursInMilli
            val elapsedMinutes = ((diffTime % daysInMilli) % hoursInMilli) / minutesInMilli
            var time = "${elapsedHours}h"
            if (elapsedMinutes > 0){
                time = "$time ${elapsedMinutes}m"
            }
            return time
        }catch (e : Exception){
            return null
        }
    }

    @BindingAdapter("app:imageLoadUrl")
    @JvmStatic
    fun setImageLoad(image : ImageView,url : String?){
        Picasso.get().load(url).into(image)
    }
}