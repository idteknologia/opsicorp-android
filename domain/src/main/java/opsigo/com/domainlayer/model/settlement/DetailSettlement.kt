package opsigo.com.domainlayer.model.settlement

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.annotations.SerializedName
import okhttp3.internal.Util
import opsigo.com.domainlayer.BR
import opsigo.com.domainlayer.model.trip.Route

class DetailDraftSettlement(
    @SerializedName("model")
    val model: DetailSettlement?,
    @SerializedName("listTicket")
    val listTicket: List<Ticket> = listOf()
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
    @SerializedName("Id")
    var Id : String = ""
//    @SerializedName("Code")
//    var Code : String = ""
//    @SerializedName("IsCashAdvance")
//    var IsCashAdvance  : Boolean = false
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
//    @SerializedName("TripNumber")
//    var TripNumber : String = ""
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
//    @SerializedName("GolperTitle")
//    var GolperTitle: String = ""
//    @SerializedName("StartDateView")
//    var StartDateView : String = ""
//    @SerializedName("EndDateView")
//    var EndDateView : String = ""
//    @SerializedName("TripDateView")
//    var TripDateView : String = ""
    @SerializedName("DurationDay")
    var DurationDay : String = ""
    @SerializedName("Purpose")
    var Purpose : String = ""
    @SerializedName("RouteType")
    var RouteType : String = ""
    @SerializedName("TripType")
    var TripType : String = ""
//    @SerializedName("CostCenter")
//    var CostCenter : String = ""
//    @SerializedName("IsDomestic")
//    var IsDomestic : Boolean = false
//    @SerializedName("IsCBT")
//    var IsCBT: Boolean = false
//    @SerializedName("WbsNo")
//    var WbsNo : String =  ""
//    @SerializedName("LaundryPcs")
//    var LaundryPcs : Any? = null
    @SerializedName("AmountLaundry")
    var AmountLaundry : Number = 0
//    @SerializedName("AmountLaundrySubmit")
//    var AmountLaundrySubmit: Number = 0
    @SerializedName("CurrLaundry")
    var CurrLaundry : String = ""
    @SerializedName("AmountAllowance")
    var AmountAllowance : Number =  0
    @SerializedName("AmountAllowanceSubmit")
    var AmountAllowanceSubmit : Number = 0
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
    @SerializedName("TotalSpecificAreaExpenseSubmit")
    var TotalSpecificAreaExpenseSubmit : Number = 0
    @get:Bindable
    @SerializedName("TotalSpecificAreaExpense")
    var TotalSpecificAreaExpense : Number = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalSpecificAreaExpense)
        }
//    @SerializedName("AllowanceResidential")
//    var AllowanceResidential :Number =  0
//    @SerializedName("AllowanceResidentialSubmit")
//    var AllowanceResidentialSubmit :Number =  0
    @SerializedName("TransportExpenses")
    var TransportExpenses = mutableListOf<TransportExpenses>()
    @SerializedName("OtherTransportExpenses")
    var OtherTransportExpenses = mutableListOf<IntercityTransport>()
    @SerializedName("OtherExpenses")
    var OtherExpense = mutableListOf<OtherExpense>()
    @get:Bindable
    @SerializedName("Attachments")
    var Attachments = mutableListOf<Attachment>()
        set(value) {
            field = value
            notifyPropertyChanged(BR.attachments)
        }
    @get:Bindable
    @SerializedName("TicketRefunds")
    var TicketRefunds = mutableListOf<Ticket>()
        set(value) {
            field = value
            notifyPropertyChanged(BR.ticketRefunds)
        }
//    @SerializedName("TripDuration")
//    var TripDuration : Number = 0
    @get:Bindable
    @SerializedName("TotalTransportExpense")
    var TotalTransportExpense : Number = 0
    set(value) {
        field = value
        notifyPropertyChanged(BR.totalTransportExpense)
    }
    @get:Bindable
    @SerializedName("TotalTransportExpenseUsd")
    var TotalTransportExpenseUsd : Number = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalTransportExpenseUsd)
        }
    @get:Bindable
    @SerializedName("TotalOtherTransportExpense")
    var TotalOtherTransportExpense : Number = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalOtherTransportExpense)
        }
    @get:Bindable
    @SerializedName("TotalOtherTransportExpenseUsd")
    var TotalOtherTransportExpenseUsd : Number = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalOtherTransportExpenseUsd)
        }
    @get:Bindable
    @SerializedName("TotalOtherExpense")
    var TotalOtherExpense  :Number = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalOtherExpense)
        }
    @SerializedName("TotalOtherExpenseUsd")
    @get:Bindable
    var TotalOtherExpenseUsd  :Number = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalOtherExpenseUsd)
        }
    @SerializedName("TotalRefundTicket")
    var TotalRefundTicket :Number = 0
    @SerializedName("TotalRefundTicketUsd")
    var TotalRefundTicketUsd :Number = 0
    @SerializedName("TotalCashAdvance")
    var TotalCashAdvance :Number = 0
    @SerializedName("CashAdvanceCurr")
    var CashAdvanceCurr :Number = 0
    @SerializedName("TotalExpenseSubmit")
    var TotalExpenseSubmit :Number = 0
    @SerializedName("TotalExpenseSubmitUsd")
    var TotalExpenseSubmitUsd :Number = 0
    @SerializedName("TotalExpenseVerification")
    var TotalExpenseVerification :Number = 0
    @SerializedName("TotalExpenseVerificationUsd")
    var TotalExpenseVerificationUsd :Number = 0
    @SerializedName("TotalToBePaidIdr")
    var TotalToBePaidIdr :Number = 0
    @SerializedName("TotalToBePaidUsd")
    var TotalToBePaidUsd :Number = 0
    @SerializedName("CreatedDate")
    var CreatedDate  : String =  ""
    @SerializedName("CreatedDateView")
    var CreatedDateView  : String =  ""
//    @SerializedName("CreatedDateString")
//    var CreatedDateString  : String =  ""
//    @SerializedName("ModifiedDate")
//    var ModifiedDate  : String =  ""
//    @SerializedName("ModifiedDateView")
//    var ModifiedDateView  : String =  ""
//    @SerializedName("SubmittedDate")
//    var SubmittedDate  : String =  ""
//    @SerializedName("SubmittedDateView")
//    var SubmittedDateView  : String =  ""
//    @SerializedName("SubmittedDateString")
//    var SubmittedDateString  : String =  ""
    @SerializedName("CreatedBy")
    var CreatedBy  : String =  ""
//    @SerializedName("IsNeedSuperiorApprover")
//    var IsNeedSuperiorApprover  : Boolean =  false
    @SerializedName("EmployeeNik")
    var EmployeeNik  : String =  ""
    @SerializedName("EmployeeName")
    var EmployeeName  : String =  ""
//    @SerializedName("PositionId")
//    var PositionId  : String =  ""
    @SerializedName("PositionName")
    var PositionName  : String =  ""
//    @SerializedName("JobTitleId")
//    var JobTitleId  : String =  ""
//    @SerializedName("JobTitleName")
//    var JobTitleName  : String =  ""
//    @SerializedName("Email")
//    var Email  : String =  ""
    @SerializedName("Status")
    var Status  : Number =  0
    @SerializedName("StatusView")
    var StatusView  : String =  ""
    @SerializedName("Comment")
    var Comment :String =  ""
    @SerializedName("TripItemTypes")
    var TripItemTypes : List<TripItemTypes> = listOf()
    @SerializedName("PartnerName")
    var PartnerName : String = ""
    @SerializedName("Routes")
    val Routes: MutableList<Route> = mutableListOf()
    @SerializedName("ShowButtonApprove")
    var ShowButtonApprove: Boolean = false
    @SerializedName("IsReviseAll")
    var IsReviseAll : Boolean = false

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

    fun getTotalSpecificArea() : Number{
        if(TotalSpecificAreaExpense != null){
            return TotalSpecificAreaExpense
        }else if (TotalSpecificAreaExpenseSubmit != null){
            return TotalSpecificAreaExpenseSubmit
        }
        return 0
    }

}