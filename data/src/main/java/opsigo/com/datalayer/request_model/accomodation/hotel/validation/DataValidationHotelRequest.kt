package opsigo.com.datalayer.request_model.accomodation.hotel.validation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class DataValidationHotelRequest(

	@field:SerializedName("IsHsre")
	var isHsre: Boolean = false,

	@field:SerializedName("HotelKey")
	var hotelKey: String = "",

	@field:SerializedName("MapUri")
	var mapUri: String = "",

	@field:SerializedName("Area")
	var area: String = "",

	@field:SerializedName("ConfirmationId")
	var confirmationId: String = "",

	@field:SerializedName("RoomSelector")
	var roomSelector: String = "",

	@field:SerializedName("IsTourism")
	var isTourism: Boolean = false,

	@field:SerializedName("TourismTax")
	var tourismTax: Int = 0,

	@field:SerializedName("CorrelationId")
	var correlationId: String = "",

	@field:SerializedName("Image")
	var image: String = ""
)