package opsigo.com.domainlayer.model.summary

data class PassportModel (
    var expire: String    = "",
    var passengerId: String   = "",
    var number: String    = "",
    var firstName: String = "",
    var originCountry: String = "",
    var id: String        = "",
    var lastName: String  = "",
    var title     :String = "",
    var birtDate  :String = "",
    var expiredDate :String = ""
)