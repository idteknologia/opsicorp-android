package opsigo.com.domainlayer.model.settlement

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.annotations.SerializedName
import okhttp3.internal.Util
import opsigo.com.domainlayer.BR
import opsigo.com.domainlayer.model.trip.Route

data class DetailDraftSettlement(
    @SerializedName("model")
    val model: DetailSettlement?
)

data class DetailSettlementResult(
    @SerializedName("trip")
    val trip: DetailSettlement?,
    var rateStay: Double,
    @SerializedName("listTicket")
    val listTicket: List<Ticket> = listOf(),
    @SerializedName("errorMessage")
    var errorMessage : String,
    @SerializedName("isSuccess")
    var isSuccess : Boolean
)

class DetailSettlement : BaseObservable(){
    @SerializedName("IsCashAdvance")
    var IsCashAdvance  : Boolean = false
    @SerializedName("BankTransfer")
    var BankTransfer : String = ""
    @SerializedName("BankAccount")
    var BankAccount : String = ""
    @get:Bindable
    @SerializedName("TripId")
    var TripId :String =  ""
    set(value) {
        field = value
        notifyPropertyChanged(BR.tripId)
    }
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
    @get:Bindable
    @SerializedName("SpecificAreaTariff")
    var SpecificAreaTariff :Number =  0
    set(value) {
        field = value
        notifyPropertyChanged(BR.specificAreaTariff)
    }
    @get:Bindable
    @SerializedName("SpecificAreaDays")
    var SpecificAreaDays : Int = 0
    set(value) {
        field = value
        notifyPropertyChanged(BR.specificAreaDays)
    }
    @get:Bindable
    @SerializedName("UseSpecificArea")
    var UseSpecificArea : Boolean = false
    set(value) {
        field = value
        notifyPropertyChanged(BR.useSpecificArea)
    }
    @get:Bindable
    @SerializedName("IsStaySpecArea")
    var IsStaySpecArea : Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.isStaySpecArea)
        }
    @SerializedName("CurrSpecificArea")
    var CurrSpecificArea : String = ""
    @SerializedName("AllowanceResidential")
    var AllowanceResidential :Number =  0
    @get:Bindable
    @SerializedName("TotalTransportExpense")
    var TotalTransportExpense : Number = 0
    set(value) {
        field = value
        notifyPropertyChanged(BR.totalTransportExpense)
    }
    @get:Bindable
    @SerializedName("TotalOtherTransportExpense")
    var TotalOtherTransportExpense : Number = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalOtherTransportExpense)
        }
    @get:Bindable
    @SerializedName("TotalSpecificAreaExpense")
    var TotalSpecificAreaExpense : Number = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalSpecificAreaExpense)
        }
    @get:Bindable
    @SerializedName("TotalOtherExpense")
    var TotalOtherExpense  :Number = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalOtherExpense)
        }
    @get:Bindable
    var TotalOtherExpenseUSD  :Number = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalOtherExpenseUSD)
        }
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
    @SerializedName("OtherExpenses")
    var OtherExpense = mutableListOf<OtherExpense>()
    @SerializedName("TicketRefunds")
    var TicketRefunds = mutableListOf<Ticket>()
    @get:Bindable
    @SerializedName("Attachments")
    var Attachments = mutableListOf<Attachment>()
    set(value) {
        field = value
        notifyPropertyChanged(BR.attachments)
    }
    @SerializedName("OtherTransportExpenses")
    var OtherTransportExpenses = mutableListOf<IntercityTransport>()
    @SerializedName("CreatedDateView")
    var CreatedDateView  : String =  ""


    fun cities(): List<String>{
        val cities = mutableSetOf<String>()
        Routes.forEach {
            cities.add(it.Origin)
            cities.add(it.Destination)
        }
        return cities.toList()
    }

    fun nameCities() : String {
        var cities = ""
        Routes.forEach {
            cities += "${it.Origin} - ${it.Destination}"
        }
        return cities
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

    fun totalTransportExpense() : Number {
        val totalIdr = TransportExpenses.filter { it.Currency == "IDR" }
            .sumOf { it.TotalAmount.toDouble() }
        return totalIdr
    }

}