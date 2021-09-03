package opsigo.com.domainlayer.model.my_booking

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PurchaseBookingContactModel(
	val email: String? = null,
	val firstName: String? = null,
	val fullName: String? = null,
	val title: String? = null,
	val lastName: String? = null,
	val mobilePhone: String? = null
):Parcelable

