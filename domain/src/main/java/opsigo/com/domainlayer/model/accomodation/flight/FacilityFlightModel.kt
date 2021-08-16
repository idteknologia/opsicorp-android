package opsigo.com.domainlayer.model.accomodation.flight

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class FacilityFlightModel (
    var nameFacility  :String = "",
    var valueFacility :String = "",
):Parcelable