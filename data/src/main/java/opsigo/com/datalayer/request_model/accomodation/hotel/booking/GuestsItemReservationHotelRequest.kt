package opsigo.com.datalayer.request_model.accomodation.hotel.booking

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class GuestsItemReservationHotelRequest(

		@field:SerializedName("Type")
	var type: Int = 0,

		@field:SerializedName("HomePhone")
	var homePhone: String = "",

		@field:SerializedName("Remarks")
	var remarks: List<String> = ArrayList(),

		@field:SerializedName("FirstName")
	var firstName: String = "",

		@field:SerializedName("IdNumber")
	var idNumber: String = "",

		@field:SerializedName("AssignedRoom")
	var assignedRoom: Int = 0,

		@field:SerializedName("OrderInRoom")
	var orderInRoom: Int = 0,

		@field:SerializedName("Title")
	var title: String = "",

		@field:SerializedName("Index")
	var index: Int = 0,

		@field:SerializedName("LastName")
	var lastName: String = "",

		@field:SerializedName("MobilePhone")
	var mobilePhone: String = "",

		@field:SerializedName("Nationality")
	var nationality: String = ""
)