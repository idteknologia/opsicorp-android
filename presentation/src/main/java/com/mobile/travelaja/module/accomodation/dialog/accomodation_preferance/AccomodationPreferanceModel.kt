package com.mobile.travelaja.module.accomodation.dialog.accomodation_preferance

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class AccomodationPreferanceModel(
    var checked :Boolean = false,
    var name : String = "",
    var id   : String = "",
    var time : String = ""
): Parcelable