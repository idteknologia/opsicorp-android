package opsigo.com.domainlayer.model.settlement

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.annotations.SerializedName
import opsigo.com.domainlayer.BR
import opsigo.com.domainlayer.model.trip.Route

data class DetailSettlementResult(
    @SerializedName("trip")
    val trip: DetailSettlement?,
    var rateStay: Double,
    @SerializedName("listTicket")
    val listTicket: List<Ticket> = listOf()
)

class DetailSettlement : BaseObservable(){
    @SerializedName("IsCashAdvance")
    var IsCashAdvance  : Boolean = false
    @SerializedName("BankTransfer")
    var BankTransfer : String = ""
    @SerializedName("BankAccount")
    var BankAccount : String = ""
    @SerializedName("TripId")
    var TripId :String =  ""
    @SerializedName("TripCode")
    @get:Bindable
    var TripCode : String = ""
    @SerializedName("TripNumber")
    var TripNumber : String = ""
    @SerializedName("StartDate")
    var StartDate : String = ""
    @SerializedName("EndDate")
    var EndDate : String = ""
    @SerializedName("CompanyCode")
    var CompanyCode : String = ""
    @SerializedName("CompanyName")
    var CompanyName : String = ""
    @SerializedName("Golper")
    var Golper : Int = 0
    @SerializedName("Purpose")
    var Purpose : String = ""
    @SerializedName("RouteType")
    var RouteType : String = ""
    @SerializedName("TripType")
    var TripType : String = ""
    @SerializedName("CostCenter")
    var CostCenter : String = ""
    @SerializedName("IsDomestic")
    var IsDomestic : Boolean = false
    @SerializedName("WbsNo")
    var WbsNo : String =  ""
    @SerializedName("LaundryPcs")
    var LaundryPcs : Any? = null
    @SerializedName("AmountLaundry")
    var AmountLaundry : Number = 0
    @SerializedName("CurrLaundry")
    var CurrLaundry : String = ""
    @SerializedName("AmountAllowance")
    var AmountAllowance : Number =  0
    @SerializedName("CurrAllowance")
    var CurrAllowance : String =  ""
    @SerializedName("SpecificAreaTariff")
    var SpecificAreaTariff :Number =  0
    @SerializedName("SpecificAreaDays")
    var SpecificAreaDays : Int = 0
    @SerializedName("UseSpecificArea")
    var UseSpecificArea : Boolean = false
    @SerializedName("IsStaySpecArea")
    var IsStaySpecArea : Boolean = false
    @SerializedName("CurrSpecificArea")
    var CurrSpecificArea : String = ""
    @SerializedName("AllowanceResidential")
    var AllowanceResidential :Number =  0
    @SerializedName("TotalTransportExpense")
    var TotalTransportExpense : Number = 0
    @SerializedName("TotalOtherTransportExpense")
    var TotalOtherTransportExpense : Number = 0
    @SerializedName("TotalSpecificAreaExpense")
    var TotalSpecificAreaExpense : Number = 0
    @SerializedName("TotalOtherExpense")
    var TotalOtherExpense  :Number =  0
    @SerializedName("TotalRefundTicket")
    var TotalRefundTicket :Number = 0
    @SerializedName("TotalCashAdvance")
    var TotalCashAdvance :Number = 0
    @SerializedName("CashAdvanceCurr")
    var CashAdvanceCurr :Number = 0
    @SerializedName("TotalToBePaidIdr")
    var TotalToBePaidIdr :Number = 0
    @SerializedName("TotalToBePaidUsd")
    var TotalToBePaidUsd :Number = 0
    @SerializedName("Comment")
    var Comment :String =  ""
    @SerializedName("PartnerName")
    var PartnerName : String = ""
    @SerializedName("TransportExpenses")
    var TransportExpenses = mutableListOf<TransportExpenses>()
    @SerializedName("Routes")
    val Routes: MutableList<Route> = mutableListOf()
    var OtherExpense = arrayListOf<OtherExpense>()

    fun cities(): List<String>{
        val cities = mutableSetOf<String>()
        Routes.forEach {
            cities.add(it.Origin)
            cities.add(it.Destination)
        }
        return cities.toList()
    }

    fun routes() : List<RouteTransport> {
        val routes = Routes.map {
            val t = RouteTransport()
            t.Route = it.Id
            t.City = "${it.Origin}-${it.Destination}"
            t
        }
        return routes
    }
}