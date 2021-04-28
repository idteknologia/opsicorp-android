package com.opsigo.travelaja.module.create_trip.newtrip_pertamina.viewmodel

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.opsigo.travelaja.BR
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

class Itinerary : BaseObservable(), Serializable {
    @get:Bindable
    var Transportation: Int = 1
        set(value) {
            field = value
            notifyPropertyChanged(BR.transportation)
        }

    @get:Bindable
    var DepartureDateView: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.departureDateView)
        }

    @get:Bindable
    var Origin: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.origin)
        }

    @get:Bindable
    var Destination: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.destination)
        }

    fun isEmptyField(): Boolean = DepartureDateView.isEmpty() || Origin.isEmpty() || Destination.isEmpty()
    fun isSame(): Boolean = Origin == Destination
    fun isComplete() = !isEmptyField() && !isSame()


}
