package com.opsigo.travelaja.module.create_trip.newtrip_pertamina.viewmodel

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.opsigo.travelaja.BR
import kotlinx.android.parcel.Parcelize

@Parcelize
class Itinerary(
        @get:Bindable
        var Transportation: Int = 1,
        @get:Bindable
        var DepartureDateView: String = "",
        @get:Bindable
        var Origin: String = "",
        @get:Bindable
        var Destination: String = ""
) : Parcelable, BaseObservable() {

    fun setTransport(pos: Int) {
        Transportation = pos
        notifyPropertyChanged(BR.transportation)
    }

    fun setDate(date: String) {
        DepartureDateView = date
        notifyPropertyChanged(BR.departureDateView)
    }

    fun setOriginFrom(origin: String) {
        Origin = origin
        notifyPropertyChanged(BR.origin)
    }

    fun setDestinationTo(dest : String){
        Destination = dest
        notifyPropertyChanged(BR.destination)
    }

    fun isEmptyField(): Boolean = DepartureDateView.isEmpty() || Origin.isEmpty() || Destination.isEmpty()
    private fun isSame(): Boolean = Origin == Destination
    fun isComplete() = !isEmptyField() && !isSame()


}
