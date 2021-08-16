package opsigo.com.domainlayer.model.accomodation.flight

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class SelectedSsrModel (
    var id          :String = "",
    var ssrCode     :String = "",
    var price       :String = "",
    var curency     :String = "",
    var ssrName     :String = "",
    var ssrType     :String = "",
    var ssrTypeName :String = ""
):Parcelable