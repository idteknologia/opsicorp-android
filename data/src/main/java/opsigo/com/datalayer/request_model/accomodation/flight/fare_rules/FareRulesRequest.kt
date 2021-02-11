package opsigo.com.datalayer.request_model.accomodation.flight.fare_rules

import com.google.gson.annotations.SerializedName

data class FareRulesRequest(

        @field:SerializedName("TravelAgent")
        var travelAgent: String? = null,

        @field:SerializedName("Adult")
        var adult: Int? = null,

        @field:SerializedName("Infant")
        var infant: Int? = null,

        @field:SerializedName("Segments")
        var segments: List<SegmentFareRulesRequest?>? = null,

        @field:SerializedName("Child")
        var child: Int? = null,

        @field:SerializedName("CompanyCode")
        var companyCode: String? = null,

        @field:SerializedName("Provider")
        var provider: Int? = null
)