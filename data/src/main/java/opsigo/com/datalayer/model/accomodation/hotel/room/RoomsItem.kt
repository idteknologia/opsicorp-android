package opsigo.com.datalayer.model.accomodation.hotel.room

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class RoomsItem(

		@field:SerializedName("RoomName")
	val roomName: String = "",

		@field:SerializedName("Message")
	val message: String = "",

		@field:SerializedName("Summaries")
	val summaries: List<String> = ArrayList(),

		@field:SerializedName("CancelLimit")
	val cancelLimit: String = "",

		@field:SerializedName("IsFullCharge")
	val isFullCharge: Boolean =  false,

		@field:SerializedName("RoomCodeHash")
	val roomCodeHash: String = "",

		@field:SerializedName("Type")
	val type: Int = 0,

		@field:SerializedName("RoomKey")
	val roomKey: String = "",

		@field:SerializedName("AppliedPolicies")
	val appliedPolicies: List<Any> = ArrayList(),

		@field:SerializedName("RoomSelector")
	val roomSelector: String = "",

		@field:SerializedName("IsGuaranteedBooking")
	val isGuaranteedBooking: Boolean = false,

		@field:SerializedName("BreakfastType")
	val breakfastType: String = "",

		@field:SerializedName("Display")
	val display: Boolean = false
)