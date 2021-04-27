package opsigo.com.datalayer.model.travel_request

import com.google.gson.annotations.SerializedName

data class EstimatedCostEntity(

	@field:SerializedName("EstLaundry")
	val estLaundry: Double = 0.0,

	@field:SerializedName("EstFlight")
	val estFlight: Double = 0.0,

	@field:SerializedName("EstHotel")
	val estHotel: Double = 0.0,

	@field:SerializedName("EstTransportation")
	val estTransportation: Double = 0.0,

	@field:SerializedName("Total")
	val total: Double = 0.0,

	@field:SerializedName("EstAllowance")
	val estAllowance: Double = 0.0,

	@field:SerializedName("EstAllowanceEvent")
	val estAllowanceEvent: Double = 0.0
)
