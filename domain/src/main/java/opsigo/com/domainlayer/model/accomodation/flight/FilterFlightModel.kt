package opsigo.com.domainlayer.model.accomodation.flight

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class FilterFlightModel (
    var id   :String = "",
    var name :String = "",
    var time :String = "",
    var isSelected :Boolean = false
    ) : Parcelable