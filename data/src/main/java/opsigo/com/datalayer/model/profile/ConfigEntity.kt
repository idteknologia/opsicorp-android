package opsigo.com.datalayer.model.profile

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ConfigEntity(

	@field:SerializedName("ShowPayment")
	var showPayment: String = "",

	@field:SerializedName("MobileTextColorLogo")
	var mobileTextColorLogo: String = "",

	@field:SerializedName("MaxParticipant")
	var maxParticipant: String = "",

	@field:SerializedName("ShowFamilyTime")
	var showFamilyTime: Boolean = false,

	@field:SerializedName("ImageUrl")
	var imageUrl: String = "",

	@field:SerializedName("IsBookAfterApprove")
	var isBookAfterApprove: Boolean = false,

	@field:SerializedName("MobileBackgroundColor")
	var mobileBackgroundColor: String = "",

	@field:SerializedName("MobileLogo")
	var mobileLogo: String = "",

	@field:SerializedName("MobileTextLogo")
	var mobileTextLogo: String = "",

	@field:SerializedName("TextFlashScreen")
	var textFlashScreen: String = "",

	@field:SerializedName("DefaultTravelAgent")
	var defaultTravelAgent: String = "",

	@field:SerializedName("ShowAllClass")
	var showAllClass: Boolean = false,

	@field:SerializedName("ShowBusinessTrip")
	var showBusinessTrip: Boolean = false,


	@field:SerializedName("Client")
    var client: Int = 0,

	@field:SerializedName("DefaultOrigin")
	var defaultOrigin: String = "",

	@field:SerializedName("DefaultDestination")
	var defaultDestination: String = "",

	@field:SerializedName("TravelingPurposeFormType")
	var travelingPurposeFormType: String = "",

	@field:SerializedName("ImageFrontUrl")
	var imageFrontUrl: String = "",

	@field:SerializedName("MobileBackgroundImage")
	var mobileBackgroundImage: String = "",

	@field:SerializedName("SetCurrency")
	var setCurrency: String = "",

	@field:SerializedName("IsShowCreateTripOnMobile")
	var isShowCreateTripOnMobile: Boolean = false,

	@field:SerializedName("ShowPersonalTrip")
    var isPersonalTrip: Boolean = false,

	@field:SerializedName("HsShowHotelNotComply")
	var hsShowHotelNotComply: Boolean = false,

	@field:SerializedName("ShowCostcenter")
	var showCostCenter: Boolean = false

)