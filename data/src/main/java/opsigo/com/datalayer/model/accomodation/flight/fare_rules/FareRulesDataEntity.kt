package opsigo.com.datalayer.model.accomodation.flight.fare_rules

import com.google.gson.annotations.SerializedName

class FareRulesDataEntity (

        @field:SerializedName("Values")
        val values: List<String?>? = null,

        @field:SerializedName("Code")
        val code: String? = null,

        @field:SerializedName("Name")
        val name: String? = null
)