package opsigo.com.domainlayer.model.accomodation.hotel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class SelectRoomModel (
    var typeRefund          :String = "",
    var titleRoom           :String = "",
    var BedFacility         :String = "",
    var listFacility        :String = "",
    var policyDescription   :String = "",
    var prize               :String = "",
    var roomCodeHash        :String = "",
    var roomKey             :String = "",
    var breakfastType       :String = "",
    var cancelLimit         :String = "",
    var isBreakfast         :Boolean = false,
    var isGuaranteedBooking :Boolean = false,
    var isFullCharge        :Boolean = false,
    var summary: List<String> = ArrayList()
):Parcelable