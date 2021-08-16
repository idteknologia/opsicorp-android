package opsigo.com.domainlayer.model.accomodation.hotel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class FacilityHotelModel (
        var code :String = "",
        var name :String= "",
        var image :Int= 0,
        var active: Boolean = false
        ):Parcelable