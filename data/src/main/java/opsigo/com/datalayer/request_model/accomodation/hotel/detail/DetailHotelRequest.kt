package opsigo.com.datalayer.request_model.accomodation.hotel.detail

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class DetailHotelRequest(

	@field:SerializedName("HotelKey")
	var hotelKey: String = "",

	@field:SerializedName("IncludeCancelPolicy")
	var includeCancelPolicy: Boolean = false,

	@field:SerializedName("IncludeReviews")
	var includeReviews: Boolean = false,

	@field:SerializedName("TravelAgent")
	var travelAgent: String = "",

	@field:SerializedName("CorrelationId")
	var correlationId: String = "",

	@field:SerializedName("IncludeImages")
	var includeImages: Boolean = false
)