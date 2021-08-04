package opsigo.com.domainlayer.model.settlement

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import opsigo.com.domainlayer.BR
import java.util.*

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
    @get:Bindable
    var BankTransfer: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.bankTransfer)
        }
    var BankAccount: String = ""
    var TripId: String = ""

    @get:Bindable
    var TripCode: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.tripCode)
        }
    var StartDate: String = ""
    var EndDate: String = ""

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
    var TransportExpenses = mutableListOf<TransportExpenses>()
        set(value) {
            field = value
            notifyPropertyChanged(BR.transportExpenses)
        }
    var TotalTransportExpense: Number = 0
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

    @get:Bindable
    var TransportationType: Int = 1
        set(value) {
            field = value
            notifyPropertyChanged(BR.transportationType)
        }
    var nameTransportationMode: String = ""

    @get:Bindable
    var TransportationMode: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.transportationMode)
        }

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

class OtherExpense : BaseObservable() {
    @get:Bindable
    var expenseName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.expenseName)
        }
    var ExpenseType: String = ""
    var Amount: String = ""
    var Description: String = ""
    var Currency: String = ""
}

@Parcelize
data class ModeTransport(
    var id: Int,
    var Disabled: Boolean,
    val Text: String,
    val Value: Int
) : Parcelable

data class ExpenseType(
    var Disabled: Boolean,
    var Selected: Boolean,
    var Text: String,
    var Value: String
) {
    override fun toString(): String {
        return Text
    }
}

data class CalculateTransportResult(
    var isSuccess: Boolean, var amount: Double
)

data class SubmitResult(var isSuccess: Boolean, var isApproverSet: Boolean)


@Parcelize
class Ticket : Parcelable, BaseObservable() {
    @SerializedName("TicketNumber")
    @get:Bindable
    var TicketNumber: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.ticketNumber)
        }

    @SerializedName("Category")
    @get:Bindable
    var Category: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.category)
        }

    @SerializedName("Amount")
    var Amount: Number? = null

    @SerializedName("Price")
    @get:Bindable
    var Price: Number = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.price)
        }

    @SerializedName("Currency")
    var Currency: String = ""
}

@Parcelize
open class RouteTransport : Parcelable, BaseObservable() {
    @get:Bindable
    var Route: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.route)
        }

    @get:Bindable
    var City: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.city)
        }
}

@Parcelize
class IntercityTransport : RouteTransport(), Parcelable {
    @SerializedName("Amount")
    var Amount: Number = 0
    @get:Bindable
    @SerializedName("Distance")
    var Distance: Number? = null
    @SerializedName("TripType")
    var TripType: Int = 0
    @SerializedName("Currency")
    var Currency: String = ""
    @get:Bindable
    @SerializedName("TotalAmount")
    var TotalAmount: Number = 0
    set(value) {
        field = value
        notifyPropertyChanged(BR.totalAmount)
    }
    @get:Bindable
    @SerializedName("Cost")
    var Cost: Number = 0
    set(value) {
        field = value
        notifyPropertyChanged(BR.cost)
    }
    @get:Bindable
    @SerializedName("IsFromPolicy")
    var IsFromPolicy: Boolean = false
    set(value) {
        field = value
        notifyPropertyChanged(BR.isFromPolicy)
    }
}

data class IntercityTransportResult(@SerializedName("result") var result: IntercityTransport)
