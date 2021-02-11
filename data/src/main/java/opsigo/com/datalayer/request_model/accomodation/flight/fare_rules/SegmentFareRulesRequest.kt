package opsigo.com.datalayer.request_model.accomodation.flight.fare_rules

import com.google.gson.annotations.SerializedName

data class SegmentFareRulesRequest (

        @field:SerializedName("FlightId")
        var flightId: String? = null,

        @field:SerializedName("Num")
        var num: Int? = null,

        @field:SerializedName("FareRuleKeys")
        var fareRuleKeys: String? = null,

        @field:SerializedName("ClassId")
        var classId: String? = null,

        @field:SerializedName("Seq")
        var seq: Int? = null
)