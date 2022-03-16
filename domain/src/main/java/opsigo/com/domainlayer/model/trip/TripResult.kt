package opsigo.com.domainlayer.model.trip

import com.google.gson.annotations.SerializedName
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
    val DestinationName: String,
    @SerializedName("Purpose")
    val Purpose: String,
    @SerializedName("StartDate")
    val StartDate: String,
    @SerializedName("ReturnDate")
    val ReturnDate: String,
    @SerializedName("EndDate")
    val EndDate: String,
    @SerializedName("TripType")
    val TripType: String,
    @SerializedName("TripCode")
    val TripCode: String,
    @SerializedName("StatusView")
    val StatusView : String,
    @SerializedName("Routes")
    val Routes: MutableList<Route> = mutableListOf(),
    @SerializedName("IsCreatedSettlement")
    val IsCreatedSettlement : Boolean = false
) {

    fun nameCities(): String {
        var cities = ""
        Routes.forEach {
            cities += "${it.Origin} - ${it.Destination}"
        }
        return cities
    }

    fun tripDestination() : String{
        var dest = setOf<String>()
        Routes.forEach {
            dest = dest.plus(it.Origin)
            dest = dest.plus(it.Destination)
        }
       return dest.joinToString("-")
    }

    fun getDateNumber(): String {
        return convertDate("dd", StartDate)
    }

    fun getMonth(): String {
        return convertDate(StartDate, "MMM", Locale.getDefault())
    }

    fun getDay(): String {
        return convertDate(StartDate, "EEEE", Locale.getDefault())
    }

    fun getDirectionDate(): String {
        val sDate = convertDate("dd MMM", StartDate)
        val eDate = convertDate("dd MMM", EndDate)
        return "$sDate - $eDate"
    }

    fun isToday(): Boolean {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val sdfOutput = SimpleDateFormat("yyyy-MM-dd")
        val now = sdfOutput.format(Date())
        val dInput = sdfOutput.format(sdf.parse(StartDate))
        return now.equals(dInput)
    }

    fun sDate(): String = convertDate(StartDate, "EEE, d MMM yyyy", Locale.getDefault())

    fun eDate(): String = convertDate(ReturnDate, "EEE, d MMM yyyy", Locale.getDefault())

    fun endDate(): String = convertDate(EndDate, "EEE, d MMM yyyy", Locale.getDefault())


    private fun convertDate(date: String?, format: String, local: Locale): String {
        if (!date.isNullOrEmpty()) {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", local)
            val sdfOutput = SimpleDateFormat(format, local)
            val dInput = sdf.parse(date)
            return sdfOutput.format(dInput)
        } else {
            return ""
        }

    }

    private fun convertDate(format: String, date: String?): String {
        if (!date.isNullOrEmpty()) {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("in"))
            val sdfOutput = SimpleDateFormat(format, Locale("in"))
            val dInput = sdf.parse(date)
            return sdfOutput.format(dInput)
        } else {
            return ""
        }
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
    val CompanyCode: String,
    @SerializedName("CompanyName")
    val CompanyName: String,
    @SerializedName("RouteType")
    val RouteType: String,
    @SerializedName("TripType")
    val TripType: String,
    @SerializedName("CostCenter")
    val CostCenter: String,
    @SerializedName("IsDomestic")
    val IsDomestic: Boolean,
    @SerializedName("WbsNo")
    val WbsNo: String,
    @SerializedName("LaundryPcs")
    val LaundryPcs: Any,
    @SerializedName("AmountLaundry")
    val AmountLaundry: Number,
    @SerializedName("CurrLaundry")
    val CurrLaundry: String,
    @SerializedName("Routes")
    val Routes: MutableList<Route> = mutableListOf()
) {
    fun cities(): List<String> {
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
    val Destination: String
)
