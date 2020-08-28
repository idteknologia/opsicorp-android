package opsigo.com.domainlayer.model.accomodation.flight

class SsrModel {
    var originCode      = ""
    var destinationCode = ""
    var flightNumber    = ""
    var mealType        = ""
    var drinkType       = ""
    var sportEquepmentType = ""
    var dataBagage      = ArrayList<DataSsr>()
    var dataSport       = ArrayList<DataSsr>()
    var dataMeal        = ArrayList<DataSsr>()
    var dataDrink       = ArrayList<DataSsr>()
    var dataOther       = ArrayList<DataSsr>()
    var isOther         = false
    var isBagage        = false
    var isHaveSport     = false
    var isHaveDrink     = false
    var isHaveMeal      = false
    var isDoubleDrink   = false
}