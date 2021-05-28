package opsigo.com.datalayer.request_model.create_trip_plane

import com.google.gson.annotations.SerializedName

data class RequestEstimatedCost (

        @field:SerializedName("TripType")
        var  tripType: String = "",

        @field:SerializedName("Purpose")
        var purpose: String = "",

        @field:SerializedName("StartDate")
        var  startDate: String = "",

        @field:SerializedName("EndDate")
        var  endDate: String = "",

        @field:SerializedName("Golper")
        var  golper: Int = 0,

        @field:SerializedName("Routes")
        var  routes: List<RoutesItem> = ArrayList(),

        @field:SerializedName("IsDomestic")
        var  isDomestic: Boolean = false,

        @field:SerializedName("WithPartner")
        var  withPartner: Boolean = false
)