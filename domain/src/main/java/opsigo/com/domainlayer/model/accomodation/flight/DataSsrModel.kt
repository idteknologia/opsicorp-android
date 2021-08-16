package opsigo.com.domainlayer.model.accomodation.flight

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DataSsrModel (
    var id      :String = "",
    var ssrCode :String = "",
    var pricing :String = "",
    var curency :String = "",
    var ssrName :String = "",
    var ssrType :String = "",
    var ssrTypeName :String = "",
    var selected :Boolean = false
    ):Parcelable