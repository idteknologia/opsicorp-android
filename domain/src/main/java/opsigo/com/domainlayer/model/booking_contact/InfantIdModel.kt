package opsigo.com.domainlayer.model.booking_contact

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class InfantIdModel (
    val id    :String = "",
    val title :String = "",
    val name  :String = "",
    val birtday :String = ""
):Parcelable