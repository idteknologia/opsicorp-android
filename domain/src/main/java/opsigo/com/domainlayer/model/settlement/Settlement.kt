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
    var TripCode: String = ""
    @get:Bindable
    var SpecificAreaTariff: Number = 0
    set(value){
        field = value
        notifyPropertyChanged(BR.specificAreaTariff)
    }
    @get:Bindable
    var SpecificAreaDays:Int = 0
    set(value) {
        field = value
        notifyPropertyChanged(BR.specificAreaDays)
    }
    var Golper:Int= 0

    fun bankSelected(): String? {
        if (BankTransfer.isNotEmpty() && BankAccount.isNotEmpty()) {
            return "$BankAccount - $BankTransfer"
        }
        return null
    }
}