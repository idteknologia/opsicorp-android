package opsigo.com.datalayer.model.result

import com.google.gson.annotations.SerializedName

data class City(
        @SerializedName("Code")
        val cityCode: String,
        @SerializedName("CountryCode")
        val countryCode: String,
        @SerializedName("CountryName")
        val countryName: String,
        @SerializedName("Name")
        val cityName: String
)
