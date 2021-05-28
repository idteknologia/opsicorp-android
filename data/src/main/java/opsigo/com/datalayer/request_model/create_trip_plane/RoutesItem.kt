package opsigo.com.datalayer.request_model.create_trip_plane

import com.google.gson.annotations.SerializedName

data class RoutesItem (
        @field:SerializedName("DepartureDateView")
        var departureDateView: String = "",

        @field:SerializedName("DepartureDate")
        var departureDate: String = "",

        @field:SerializedName("Origin")
        var origin: String = "",

        @field:SerializedName("Destination")
        var destination: String = "",

        @field:SerializedName("Transportation")
        var transportation: Int = 0
)