package opsigo.com.domainlayer.model.my_booking

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ImportanPreProductInfoModel (
    var description : String = "",
    var icon : Int = 0
):Parcelable