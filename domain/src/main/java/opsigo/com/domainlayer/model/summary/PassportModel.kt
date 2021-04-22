package opsigo.com.domainlayer.model.summary

data class PassportModel (
        var passengerId: String   = "",
        var passporNumber: String    = "",
        var firstName: String = "",
        var originCountry: String = "",
        var id: String        = "",
        var lastName: String  = "",
        var title     :String = "",
        var birtDate  :String = "",
        var expiredDate :String = "",
        var nasionality :String = ""
)