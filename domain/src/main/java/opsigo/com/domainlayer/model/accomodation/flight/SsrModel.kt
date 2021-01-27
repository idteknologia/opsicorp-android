package opsigo.com.domainlayer.model.accomodation.flight

class SsrModel {
    var originCode      = ""
    var destinationCode = ""
    var flightNumber    = ""
    var mealType        = ""
    var drinkType       = ""
    var sportEquipmentType = ""
    var bagaggeSelected = DataSsr()
    var ssrSelected     = DataSsr()
    var dataBagage      = ArrayList<DataSsr>()
    var dataSport       = ArrayList<DataSsr>()
    var dataMeal        = ArrayList<DataSsr>()
    var dataDrink       = ArrayList<DataSsr>()
    var dataOther       = ArrayList<DataSsr>()
    var isOther         = true
    var isBagage        = true
    var isHaveSport     = true
    var isHaveDrink     = true
    var isHaveMeal      = true
    var isDoubleDrink   = true
}