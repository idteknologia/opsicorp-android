package opsigo.com.domainlayer.model.my_booking

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DetailFlightMyBookingModel (
    var status     : String  = "",
    var destinationCity: String = "",
    var originCity : String  = "",
    var pnrCode    : String? = "",
    var Segment    : ArrayList<PurchaseDetailTripFlightAndTrainModel> = ArrayList(),
    var passanger  : ArrayList<PassangerPurchaseModel> = ArrayList(),
):Parcelable