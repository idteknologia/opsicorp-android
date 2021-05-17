package opsigo.com.domainlayer.model

import com.google.gson.annotations.SerializedName

data class ResultList<T>(
        @SerializedName("data")
        var items: MutableList<T> = mutableListOf(),
        var page : Int = 0
)