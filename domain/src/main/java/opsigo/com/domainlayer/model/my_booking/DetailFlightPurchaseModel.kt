package opsigo.com.domainlayer.model.my_booking

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DetailFlightPurchaseModel (
    var flights    : ArrayList<PurchaseDetailTripFlightAndTrainModel> = ArrayList(),
    var importan   : ArrayList<ImportanPreProductInfoModel> = ArrayList(),
    var passanger  : ArrayList<PassangerPurchaseModel> = ArrayList(),
    var totalPrize : String = ""
):Parcelable