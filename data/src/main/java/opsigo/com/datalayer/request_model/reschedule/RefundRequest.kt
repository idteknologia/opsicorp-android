package opsigo.com.datalayer.request_model.reschedule

import com.google.gson.annotations.SerializedName

data class RefundRequest(

	@field:SerializedName("TripCode")
	var tripCode: String? = null,

	@field:SerializedName("Participant")
	var participant: Participant? = null,

	@field:SerializedName("ListPnr")
	var listPnr: ListPnr? = null
)

data class FlightsItem(

	@field:SerializedName("Id")
	val id: String? = null
)

data class HotelsItem(

	@field:SerializedName("Id")
	val id: String? = null
)

data class ListAttachmentsItem(

	@field:SerializedName("Description")
	val description: String? = null,

	@field:SerializedName("Url")
	val url: String? = null
)

data class Participant(

	@field:SerializedName("Id")
    var id: String? = null
)

data class ListPnr(

	@field:SerializedName("Hotels")
	var hotels: ArrayList<HotelsItem> = ArrayList(),

	@field:SerializedName("ListAttachments")
	var listAttachments: ArrayList<ListAttachmentsItem> = ArrayList(),

	@field:SerializedName("Flights")
	var flights: ArrayList<FlightsItem> = ArrayList()
)
