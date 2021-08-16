package opsigo.com.domainlayer.model.accomodation.hotel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class GaleryModel (
	var description :String = "",
	var thumbUri 	:String = "",
	var imageUri 	:String = "",
	var position :Int = 0
	): Parcelable
