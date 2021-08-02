package opsigo.com.domainlayer.model.trip

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

data class TripResult(
    @SerializedName("data")
    val data: MutableList<Trip>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("page")
    val page: Int
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

    fun getDay(): String {
        return convertDate("EEEE")
    }

    fun isToday(): Boolean {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val sdfOutput = SimpleDateFormat("yyyy-MM-dd")
        val now = sdfOutput.format(Date())
        val dInput = sdfOutput.format(sdf.parse(startDate))
        return now.equals(dInput)
    }

    fun sDate(): String = convertDate(startDate, "EEE, d MMM yyyy", Locale.getDefault())

    fun eDate(): String = convertDate(returnDate, "EEE, d MMM yyyy", Locale.getDefault())

    private fun convertDate(date: String, format: String, local: Locale): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", local)
        val sdfOutput = SimpleDateFormat(format, local)
        val dInput = sdf.parse(startDate)
        return sdfOutput.format(dInput)
    }

    private fun convertDate(format: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("in"))
        val sdfOutput = SimpleDateFormat(format, Locale("in"))
        val dInput = sdf.parse(startDate)
        return sdfOutput.format(dInput)
    }
}

data class DetailTripResult(
    @SerializedName("trip")
    val trip: DetailTrip,
    var rateStay: Double
)

data class DetailTrip(
    @SerializedName("Id")
    val Id: String,
    @SerializedName("Code")
    val Code: String,
    @SerializedName("DestinationName")
    val destinationName: String,
    @SerializedName("Purpose")
    val Purpose: String,
    @SerializedName("StartDate")
    val StartDate: String,
    @SerializedName("EndDate")
    val EndDate: String,
    @SerializedName("Golper")
    val Golper: Int,
    @SerializedName("SpecificAreaTariff")
    val SpecificAreaTariff: Int,
    @SerializedName("CompanyCode")
    val CompanyCode:String,
    @SerializedName("CompanyName")
    val CompanyName:String,
    @SerializedName("RouteType")
    val RouteType:String,
    @SerializedName("TripType")
    val TripType:String,
    @SerializedName("CostCenter")
    val CostCenter: String,
    @SerializedName("IsDomestic")
    val IsDomestic : Boolean,
    @SerializedName("WbsNo")
    val WbsNo:String,
    @SerializedName("LaundryPcs")
    val LaundryPcs: Any,
    @SerializedName("AmountLaundry")
    val AmountLaundry : Number,
    @SerializedName("CurrLaundry")
    val CurrLaundry : String,
    @SerializedName("Routes")
    val Routes: MutableList<Route> = mutableListOf()
){
    fun cities(): List<String>{
       val cities = mutableSetOf<String>()
        Routes.forEach {
            cities.add(it.Origin)
            cities.add(it.Destination)
       }
        return cities.toList()
    }
}

data class Route(
    @SerializedName("Id")
    val Id: String,
    @SerializedName("Origin")
    val Origin: String,
    @SerializedName("Destination")
    val Destination : String
)
