package opsigo.com.domainlayer.model.accomodation.hotel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class RiviewHotelModel (
    var time    :String = "",
    var id      :String = "",
    var massage :String = "",
    var name    :String = "",
    var rating  :Int = 0,
    var image   :String = ""
    ):Parcelable