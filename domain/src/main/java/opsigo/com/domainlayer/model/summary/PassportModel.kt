package opsigo.com.domainlayer.model.summary

data class PassportModel (
        var passporNumber: String    = "",
        var fullname    : String    = "",
        var id           : String    = "",
        var title        : String    = "",
        var birtDate     : String    = "",
        var expiredDate  : String    = "",
        var nasionality  : String    = "",
        var email        : String    = "",
        var mobilePhone  : String    = ""
)