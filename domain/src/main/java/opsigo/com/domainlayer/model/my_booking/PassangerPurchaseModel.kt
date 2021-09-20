package opsigo.com.domainlayer.model.my_booking

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PassangerPurchaseModel (
    var age             :String = "",
    var totalBagage     :String = "",
    var Name            :String = "",
    var seatPassager    :String = "",
    ):Parcelable