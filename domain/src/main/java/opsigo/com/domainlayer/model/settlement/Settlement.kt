package opsigo.com.domainlayer.model.settlement

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.annotations.SerializedName
import opsigo.com.domainlayer.BR

data class Settlement(
    @SerializedName("Id")
    val Id: String,
    @SerializedName("Code")
    val Code: String,
    @SerializedName("StatusView")
    val statusView: String,
    @SerializedName("Purpose")
    val purpose: String,
    @SerializedName("TripType")
    val tripType: String,
    @SerializedName("TripCode")
    val tripCode: String
)

data class RateStayResult(
    @SerializedName("result")
    var result: Double
)

data class Bank(
    @SerializedName("Id")
    val Id: String,
    @SerializedName("BankKey")
    val BankKey: String,
    @SerializedName("Account")
    val Account: String,
    @SerializedName("BankName")
    val BankName: String,
)

class SubmitSettlement : BaseObservable() {
    var BankTransfer: String = ""
    var BankAccount: String = ""
    var TripId: String = ""

    @get:Bindable
    var TripCode: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.tripCode)
        }

    @get:Bindable
    var SpecificAreaTariff: Number = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.specificAreaTariff)
        }

    @get:Bindable
    var SpecificAreaDays: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.specificAreaDays)
        }
    var Golper: Int = 0

    @get:Bindable
    var TotalSpecificAreaExpense: Number = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalSpecificAreaExpense)
        }
    @get:Bindable
    var TransportExpenses = mutableListOf(TransportExpenses())
    set(value) {
        field = value
        notifyPropertyChanged(BR.transportExpenses)
    }

    fun bankSelected(): String? {
        if (BankTransfer.isNotEmpty() && BankAccount.isNotEmpty()) {
            return "$BankAccount - $BankTransfer"
        }
        return null
    }
}

class TransportExpenses : BaseObservable() {
    @get:Bindable
    var City: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.city)
        }
    var TransportationType: Int = 1
    var TransportationMode: String = ""
    @get:Bindable
    var Amount: Number = 0
    set(value) {
        field = value
        notifyPropertyChanged(BR.amount)
    }
    var TripType: Int = 0
    var Currency: String = "IDR"
    @get:Bindable
    var TotalAmount: Number = 0
    set(value) {
        field = value
        notifyPropertyChanged(BR.totalAmount)
    }
}

data class ModeTransport(
    var Disabled: Boolean,
    val Text: String,
    val Value: Int
)

data class CalculateTransportResult(
    var isSuccess: Boolean, var amount: Double
)