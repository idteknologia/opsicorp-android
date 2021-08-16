package opsigo.com.domainlayer.model.accomodation.flight

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class SsrItemModel (
    var ssrTypeName     :String = "",
    var ssrFlightNumber :String = "",
    var ssrItem         :ArrayList<DataSsrModel> = ArrayList()
):Parcelable