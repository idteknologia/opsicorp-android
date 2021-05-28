package opsigo.com.domainlayer.model.settlement

import com.google.gson.annotations.SerializedName

data class Settlement(
        @SerializedName("Id")
        val Id: String,
        @SerializedName("Code")
        val Code: String,
        @SerializedName("StatusView")
        val statusView : String,
        @SerializedName("Purpose")
        val purpose: String,
        @SerializedName("TripType")
        val tripType: String,
        @SerializedName("TripCode")
        val tripCode : String

)