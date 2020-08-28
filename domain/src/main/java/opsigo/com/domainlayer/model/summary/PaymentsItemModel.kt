package opsigo.com.domainlayer.model.summary

data class PaymentsItemModel (
    var amount: Double? = null,
    var currency: String? = null,
    var title: String? = null,
    var id: String? = null,
    var code: String? = null,
    var tripFlightId: String? = null,
    var seq: Int? = null
)