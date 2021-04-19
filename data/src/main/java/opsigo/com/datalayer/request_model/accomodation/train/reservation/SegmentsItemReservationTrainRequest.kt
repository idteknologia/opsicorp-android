package opsigo.com.datalayer.request_model.accomodation.train.reservation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SegmentsItemReservationTrainRequest(

	@field:SerializedName("Origin")
	var origin: String = "",

	@field:SerializedName("SubClass")
	var subClass: String = "",

	@field:SerializedName("Destination")
	var destination: String = "",

	@field:SerializedName("ArriveTime")
	var arriveTime: String = "",

	@field:SerializedName("TrainName")
	var trainName: String = "",

	@field:SerializedName("Num")
	var num: String = "",

	@field:SerializedName("DepartDate")
	var departDate: String = "",

	@field:SerializedName("CarrierNumber")
	var carrierNumber: String = "",

	@field:SerializedName("DepartTime")
	var departTime: String = "",

	@field:SerializedName("ClassKey")
	var classKey: String = "",

	@field:SerializedName("Class")
	var clas: String = "",

	@field:SerializedName("ArriveDate")
	var arriveDate: String = "",

	@field:SerializedName("JourneyCode")
	var journeyCode: String = "",

	@field:SerializedName("CauseViolatedTrainRules")
	var causeViolatedTrainRules: String = "",

	@field:SerializedName("IsSecuritySensitivity")
	var isSecuritySensitivity: Boolean = false,

	@field:SerializedName("DescSecuritySensitivity")
	var descSecuritySensitivity: String = "",

	@field:SerializedName("FareBasisCode")
	var fareBaseCode: String = "",

	@field:SerializedName("IsViolatedTrainRules")
	var isViolatedTrainRules: String = ""

)