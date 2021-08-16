package opsigo.com.domainlayer.model.accomodation.flight

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class FareRulesModel (
    var fareRulesCode   :String = "",
    var fareRulesName   :String = "",
    var fareRulesValues :ArrayList<String> = ArrayList()
): Parcelable