package opsigo.com.domainlayer.model.my_booking

import opsigo.com.domainlayer.model.accomodation.hotel.FacilityHotelModel

data class ItemHotelPurchase(
	var voucerCode: String? = null,
	var hotelName: String? = null,
	var status: String? = null,
	var ratingStar: Int = 0,
	var address: String? = null,
	var checkInDate: String? = null,
	var checkOutDate: String? = null,
	var totalNight: String? = null,
	var timeCheckIn: String? = null,
	var timeCheckOut: String? = null,
	var nameBooker: String? = null,
	var latitude: String? = null,
	var longitude: String? = null,
	var classRoom : String? = null,
	var roomsTotal: Int? = null,
	var facility : ArrayList<FacilityHotelModel> = ArrayList(),
	var guests: List<GuestsItems?>? = null
)

data class GuestsItems(
	var firstName: String? = null,
	var bedType: String? = null,
	var maxBedType :String? = null,
	var index: Int? = null,
)

