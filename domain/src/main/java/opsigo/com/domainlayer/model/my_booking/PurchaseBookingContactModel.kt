package opsigo.com.domainlayer.model.my_booking

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PurchaseBookingContactModel(
	var email: String? = null,
	var firstName: String? = null,
	var fullName: String? = null,
	var title: String? = null,
	var lastName: String? = null,
	var mobilePhone: String? = null
):Parcelable

