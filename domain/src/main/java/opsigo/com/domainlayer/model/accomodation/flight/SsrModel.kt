package opsigo.com.domainlayer.model.accomodation.flight

class SsrModel {
    var originCode      = ""
    var destinationCode = ""
    var flightNumber    = ""
    var mealType        = ""
    var drinkType       = ""
    var sportEquipmentType = ""
    var bagaggeSelected = DataSsrModel()
    var ssrSelected     = DataSsrModel()
    var dataBagage      = ArrayList<DataSsrModel>()
    var dataSsr         = ArrayList<SsrItemModel>()
    var isOther         = true
    var isBagage        = true
    var isHaveSport     = true
    var isHaveDrink     = true
    var isHaveMeal      = true
    var isDoubleDrink   = true
}