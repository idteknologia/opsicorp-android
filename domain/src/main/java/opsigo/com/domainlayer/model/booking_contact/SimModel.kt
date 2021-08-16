package opsigo.com.domainlayer.model.booking_contact

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class SimModel (
    var id          :String = "",
    var idSim       :String = "",
    var title       :String = "",
    var name        :String = "",
    var email       :String = "",
    var birthDate   :String = "",
    var mobilePhone :String = ""
):Parcelable