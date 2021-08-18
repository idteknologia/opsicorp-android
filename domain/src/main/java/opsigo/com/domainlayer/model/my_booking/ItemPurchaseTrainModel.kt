package opsigo.com.domainlayer.model.my_booking

data class ItemPurchaseTrainModel(
	val origin: String? = null,
	val status: String? = null,
	val destination: String? = null,
	val trainNumber: String? = null,
	val trainName: String? = null,
	val destinationCity: String? = null,
	val num: Int? = null,
	val originCity: String? = null,
	val duration: String? = null,
	val classCategory: String? = null,
	val pnrCode: String? = null,
	val classCode: String? = null,
	val passengers: List<PassengersItem?>? = null,
	val originStation: String? = null,
	val arrivalDate: String? = null,
	val destinationStation: String? = null,
	val departureDate: String? = null
)




