package opsigo.com.domainlayer.model.settlement

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableField
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
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
    var TransportationModeName: String = ""

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

data class OtherEx(
    var ExpenseType: String = "",
    var Amount: Number = 0,
    var expenseName: String = "",
    var Description: String = "",
    var Currency: String = ""
)

@Parcelize
data class OtherExpense(
    var ExpenseName: String = "",
    var ExpenseType: String = "",
    var Amount: Number = 0,
    var Description: String = "",
    var Currency: String = ""
) : Parcelable {
    fun getValueAmount(): String {
        return try {
            if (Amount.toLong() > 0) {
                "$Amount"
            } else {
                ""
            }
        } catch (t: Throwable) {
            ""
        }
    }
}


@Parcelize
data class ModeTransport(
    var id: Int,
    var Disabled: Boolean,
    val Text: String,
    val Value: Int,
    val ValueInt: Int
) : Parcelable

@Parcelize
data class ExpenseType(
    @SerializedName("ExpenseType")
    var ExpenseType: String,
    @SerializedName("Description")
    var Description: String,
    @SerializedName("TypeFor")
    var TypeFor: Int
) : Parcelable {
    override fun toString(): String {
        return Description
    }
}

data class CalculateTransportResult(
    var isSuccess: Boolean, var amount: Double,
    @SerializedName("currency")
    var Currency: String
)

data class SubmitResult(
    @SerializedName("isSuccess")
    var isSuccess: Boolean,
    @SerializedName("isApproverSet")
    var isApproverSet: Boolean,
    @SerializedName("errorMessage")
    var errorMessage: Any,
    @SerializedName("Id")
    var idDraft : String? = null
)


@Parcelize
class Ticket : Parcelable, BaseObservable() {
    @SerializedName("PnrCode")
    var PnrCode: String = ""

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
    @get:Bindable
    var Currency: String = ""
    set(value) {
        field = value
        notifyPropertyChanged(BR.currency)
    }
}

@Parcelize
data class RouteTransport(
    var Route: String = "",
    var City: String = "",
    var enabled: Boolean = true
) : Parcelable, BaseObservable() {

    override fun toString(): String {
        return City
    }
}

@Parcelize
data class IntercityTransport(
    @SerializedName("Route")
    var Route: String = "",
    @SerializedName("City")
    var City: String = "",
    @SerializedName("Amount")
    var Amount: Number = 0,
    @SerializedName("Distance")
    var Distance: Number? = null,
    @SerializedName("TripType")
    var TripType: Int = 0,
    @SerializedName("Currency")
    var Currency: String = "",
    @SerializedName("TotalAmount")
    var TotalAmount: Number = 0,
    @SerializedName("Cost")
    var Cost: Number = 0,
    @SerializedName("IsFromPolicy")
    var IsFromPolicy: Boolean = false
) : Parcelable

data class IntercityTransportResult(@SerializedName("result") var result: IntercityTransport)

data class Attachment(
    @SerializedName("Id")
    var Id: String,
    @SerializedName("TripPlanId")
    var TripPlanId: String,
    @SerializedName("Description")
    var Description: String,
    @SerializedName("Url")
    var Url: String,
    @SerializedName("HasScanned")
    var HasScanned: Boolean,
    var type: String?
)

data class History(
    val Id : String,
    val SettlementId : String,
    val Description : String,
    val CreatedDate : String,
    val CreatedDateView : String
)
