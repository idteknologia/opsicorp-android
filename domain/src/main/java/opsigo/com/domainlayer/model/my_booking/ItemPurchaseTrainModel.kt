package opsigo.com.domainlayer.model.my_booking

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemPurchaseTrainModel(
	var status : String  = "",
	var pnrCode: String? = null,
	var nameTrain : String = "",
	var trainCode  : String = "",
	var className  : String = "",
	var dateArrival : String = "",
	var dateDeparture : String = "",
	var originCity: String? = null,
	var originStation : String = "",
	var durationTime : String = "",
	var destinationCity: String? = null,
	var destinationStation :String = "",
	var passager : ArrayList<PassengersTrainModel> = ArrayList(),
):Parcelable

@Parcelize
data class PassengersTrainModel(
	var typeAge  : String? = null,
	var firstName: String? = null,
	var lastName : String? = null,
	var idNumber: String? = null,
	var title: String? = null,
	var index: Int? = null,
	var seatNumber: String? = null,
	var seatName : String = "",
	var idType: String? = null,
	var birthDate: String? = null
): Parcelable{
	fun fullName():String {
		return "${firstName} ${lastName}"
	}
	fun getSeatView():String{
		return "${seatName}/${seatNumber}"
	}
}




