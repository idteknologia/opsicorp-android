package opsigo.com.domainlayer.model.my_booking

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PassengersItem(
    val type: String? = null,
    val seatName: String? = null,
    val firstName: String? = null,
    val idNumber: String? = null,
    val title: String? = null,
    val index: Int? = null,
    val lastName: String? = null,
    val seatNumber: String? = null,
    val idType: String? = null,
    val birthDate: String? = null
): Parcelable