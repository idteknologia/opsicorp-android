package opsigo.com.datalayer.model.approval.hotel

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.model.Passport

@Generated("com.robohorse.robopojogenerator")
data class GuestItem(

        @field:SerializedName("OtherPhone")
	val otherPhone: Any? = null,

        @field:SerializedName("Email")
	val email: Any? = null,

        @field:SerializedName("FirstName")
	val firstName: String? = null,

        @field:SerializedName("IdNumber")
	val idNumber: String? = null,

        @field:SerializedName("TicketNumberNew")
	val ticketNumberNew: Any? = null,

        @field:SerializedName("Title")
	val title: String? = null,

        @field:SerializedName("Gender")
	val gender: Any? = null,

        @field:SerializedName("MobilePhone")
	val mobilePhone: String? = null,

        @field:SerializedName("Nationality")
	val nationality: String? = null,

        @field:SerializedName("Type")
	val type: String? = null,

        @field:SerializedName("Passport")
	val passport: Passport? = null,

        @field:SerializedName("HomePhone")
	val homePhone: String? = null,

        @field:SerializedName("TicketNumber")
	val ticketNumber: String? = null,

        @field:SerializedName("Remarks")
	val remarks: List<Any?>? = null,

        @field:SerializedName("Id")
	val id: String? = null,

        @field:SerializedName("LastName")
	val lastName: String? = null,

        @field:SerializedName("Seq")
	val seq: Int? = null,

        @field:SerializedName("BirthDate")
	val birthDate: String? = null
)