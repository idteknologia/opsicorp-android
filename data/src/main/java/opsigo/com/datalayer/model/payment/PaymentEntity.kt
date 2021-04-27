package opsigo.com.datalayer.model.payment

import com.google.gson.annotations.SerializedName

data class PaymentEntity(

        @field:SerializedName("data")
        val data: Data? = null,

        @field:SerializedName("errorMessage")
        val errorMessage: String? = "",

        @field:SerializedName("status")
        val status: Boolean? = null
)

data class Data(

        @field:SerializedName("paymentLink")
        val paymentLink: String? = ""
)