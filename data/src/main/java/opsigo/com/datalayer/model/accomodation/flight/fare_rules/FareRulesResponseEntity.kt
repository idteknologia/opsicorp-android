package opsigo.com.datalayer.model.accomodation.flight.fare_rules

import com.google.gson.annotations.SerializedName

class FareRulesResponseEntity (

        @field:SerializedName("result")
        val result: List<FareRulesDataEntity?>? = null,

        @field:SerializedName("errorMessage")
        val errorMessage: String? = null
)