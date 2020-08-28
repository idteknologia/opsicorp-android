package opsigo.com.datalayer.model.accomodation.hotel.booking

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class GuestItemHotelEntity(

	@field:SerializedName("OtherPhone")
	val otherPhone: String = "",

	@field:SerializedName("Email")
	val email: String = "",

	@field:SerializedName("FirstName")
	val firstName: String = "",

	@field:SerializedName("IdNumber")
	val idNumber: String = "",

	@field:SerializedName("TicketNumberNew")
	val ticketNumberNew: String = "",

	@field:SerializedName("AssignedRoom")
	val assignedRoom: String = "",

	@field:SerializedName("OrderInRoom")
	val orderInRoom: String = "",

	@field:SerializedName("Title")
	val title: String = "",

	@field:SerializedName("Gender")
	val gender: String = "",

	@field:SerializedName("MobilePhone")
	val mobilePhone: String = "",

	@field:SerializedName("Nationality")
	val nationality: String = "",

	@field:SerializedName("Type")
	val type: String = "",

	@field:SerializedName("Passport")
	val passport: String = "",

	@field:SerializedName("HomePhone")
	val homePhone: String = "",

	@field:SerializedName("TicketNumber")
	val ticketNumber: String = "",

	@field:SerializedName("Remarks")
	val remarks: List<String?>? = null,

	@field:SerializedName("Id")
	val id: String = "",

	@field:SerializedName("LastName")
	val lastName: String = "",

	@field:SerializedName("Seq")
	val seq: Int? = null,

	@field:SerializedName("BirthDate")
	val birthDate: String = ""
)