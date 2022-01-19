package com.mobile.travelaja.utility

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.mobile.travelaja.R
import com.squareup.picasso.Picasso
import net.openid.appauth.AuthorizationException
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import java.net.SocketTimeoutException
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    const val EMPTY = "Empty"
    const val ISNULL = "DATA NULL"
    fun handleErrorMessage(
        context: Context,
        t: Throwable,
        callback: (errorString: String) -> Unit
    ) {
        if (t is IOException) {
            if (t is SocketTimeoutException){
                callback.invoke(context.getString(R.string.warning_timeout))
            }else
            callback.invoke(context.getString(R.string.no_internet))
        } else if (t is AuthorizationException) {
            if (t.code == 1) {
                callback.invoke("")
            } else {
                callback.invoke(t.errorDescription ?: "not description")
            }
        } else if (t is HttpException) {
            callback.invoke(t.localizedMessage)
        } else {
            callback.invoke(t.message ?: "not description")
        }
    }

    @JvmStatic
    fun formatCurrency(value: Number?): String {
        try {
            if (value == null) {
                return ""
            }
            val format = NumberFormat.getCurrencyInstance()
            val symbol = (format as DecimalFormat).decimalFormatSymbols
            symbol.currencySymbol = ""
            format.minimumFractionDigits = 0
            format.decimalFormatSymbols = symbol
            return format.format(value)
        } catch (t: Throwable) {
            return ""
        }
    }

    @JvmStatic
    fun doubleParse(value: Number?): Number? {
        if (value != null) {
            val format = DecimalFormat("###.###")
            return format.parse(value.toString())
        } else {
            return value
        }
    }

    @JvmStatic
    fun doubleParseStr(value: Number?): String{
        try {
            val format = DecimalFormat("###.###")
            return format.format(value)
        } catch (t: Throwable) {
            t.printStackTrace()
            return ""
        }
    }

    fun getDurationTime(startTime: String?, endTime: String?): String? {
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
            if (elapsedMinutes > 0) {
                time = "$time ${elapsedMinutes}m"
            }
            return time
        } catch (e: Exception) {
            return null
        }
    }

    @JvmStatic
    fun convertDate(date: String?, format: String): String? {
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val sdfOutput = SimpleDateFormat(format)
            val dInput = sdf.parse(date)
            sdfOutput.format(dInput)
        }catch (e : Exception){
            ""
        }

    }

    @BindingAdapter("app:imageLoadUrl")
    @JvmStatic
    fun setImageLoad(image: ImageView, url: String?) {
        Picasso.get().load(url).into(image)
    }

    @BindingAdapter("app:formatTextIdr")
    @JvmStatic
    fun formatTextCurrency(textView : TextView,amountIdr : Number?){
        val idr = formatCurrency(amountIdr)
        val idrLabel = textView.context.getString(R.string.other_expense_idr)
        "$idr $idrLabel".also { textView.text = it }
    }

    @BindingAdapter("app:formatTextUsd")
    @JvmStatic
    fun formatTextCurrencyUsd(textView : TextView,amountIdr : Number?){
        val idr = formatCurrency(amountIdr)
        val idrLabel = textView.context.getString(R.string.other_expense_usd)
        "$idr $idrLabel".also { textView.text = it }
    }

    @BindingAdapter("app:generateQrCode")
    @JvmStatic
    fun generateQrCode(image: ImageView, text : String?){
        if (text.isNullOrEmpty())
            return
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,200,200)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            image.setImageBitmap(bitmap)
        }catch (e : WriterException){
            e.printStackTrace()
        }
    }


    fun createMultipart(type : String?, uri : String) : MultipartBody.Part{
        val typeFile = type ?: "image/jpeg"
        val file = File(uri)
        val fileName = file.name
        val requestFile = file.asRequestBody(typeFile.toMediaType())
        val multipart = MultipartBody.Part.createFormData("file",fileName,requestFile)
        return multipart
    }
}