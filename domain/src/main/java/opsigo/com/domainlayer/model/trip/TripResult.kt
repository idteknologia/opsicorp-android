package opsigo.com.domainlayer.model.trip

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class TripResult(
        @SerializedName("data")
        val data: MutableList<Trip>
)

data class Trip(
        @SerializedName("Id")
        val Id: String,
        @SerializedName("Code")
        val Code: String,
        @SerializedName("DestinationName")
        val destinationName: String,
        @SerializedName("Purpose")
        val purpose: String,
        @SerializedName("StartDate")
        val startDate: String,
        @SerializedName("ReturnDate")
        val returnDate: String
) {

    fun getDateNumber(): String {
        return convertDate("dd")
    }

    fun getMonth(): String {
        return convertDate("MMM")
    }

    fun getDay() : String {
        return convertDate("EEEE")
    }

    fun isToday() : Boolean {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val sdfOutput = SimpleDateFormat("yyyy-MM-dd")
        val now = sdfOutput.format(Date())
        val dInput = sdfOutput.format(sdf.parse(startDate))
        return now.equals(dInput)
    }

    private fun convertDate(format : String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("in"))
        val sdfOutput = SimpleDateFormat(format, Locale("in"))
        val dInput = sdf.parse(startDate)
        return sdfOutput.format(dInput)
    }
}
