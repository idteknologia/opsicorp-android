package opsigo.com.domainlayer.model.booking_contact

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class IdCartModel  (
    var id          :String = "",
    var title       :String = "",
    var fullname    :String = "",
    var idCart      :String = "",
    var mobilePhone :String = "",
    var email       :String = "",
    var birthDate   :String = "",
    ):Parcelable