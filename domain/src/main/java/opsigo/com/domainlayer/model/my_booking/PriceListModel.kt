package opsigo.com.domainlayer.model.my_booking

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PriceListModel(
	val amount: Double = 0.0,
	val title: String? = null,
	val index: Int? = null
):Parcelable

