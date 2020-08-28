package opsigo.com.datalayer.request_model.accomodation.hotel.booking

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class HotelReservationHotelRequest(

	@field:SerializedName("IsHsre")
	var isHsre: Boolean = false,

	@field:SerializedName("MapUri")
	var mapUri: String = "",

	@field:SerializedName("Area")
	var area: String = "",

	@field:SerializedName("IsViolatedHotelRules")
	var isViolatedHotelRules: Boolean = false,

	@field:SerializedName("RoomSelector")
	var roomSelector: String = "",

	@field:SerializedName("IsTourism")
	var isTourism: Boolean = false,

	@field:SerializedName("TourismTax")
	var tourismTax: Int = 0,

	@field:SerializedName("CauseViolatedRules")
	var causeViolatedRules: String = "",

	@field:SerializedName("Image")
	var image: String = "",

	@field:SerializedName("CancellationPoliciesView")
	var cancellationPoliciesView: List<String> = ArrayList()
)