package opsigo.com.domainlayer.model.booking_contact

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class InfantIdModel (
    var id    :String = "",
    var title :String = "",
    var name  :String = "",
    var birtday :String = ""
):Parcelable