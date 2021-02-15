package opsigo.com.domainlayer.model.accomodation.flight

class SsrModel {
    var originCode      = ""
    var destinationCode = ""
    var flightNumber    = ""
    var mealType        = ""
    var drinkType       = ""
    var sportEquipmentType = ""
    var bagaggeSelected = DataSsrModel()
    var bagaggeItemSelected = ArrayList<SelectedBaggageModel>()
    var ssrSelected     = ArrayList<SelectedSsrModel>()
    var dataBagage      = ArrayList<DataSsrModel>()
    var dataSsr         = ArrayList<SsrItemModel>()
    var isOther         = false
    var isBagage        = false
    var isHaveSport     = true
    var isHaveDrink     = true
    var isHaveMeal      = true
    var isDoubleDrink   = true
}