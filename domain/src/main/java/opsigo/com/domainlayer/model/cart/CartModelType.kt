package opsigo.com.domainlayer.model.cart

import opsigo.com.domainlayer.model.summary.ItemFlightModel
import opsigo.com.domainlayer.model.summary.ItemHotelModel
import opsigo.com.domainlayer.model.summary.ItemTrainModel

class CartModelType {

    var typeCard       = 0
    var dataHeader     = CartHeaderModel()
    var dataCardFlight = ItemFlightModel()
    var dataCardTrain  = ItemTrainModel()
    var dataCardHotel  = ItemHotelModel()
//
//    var dataCardFlight = ItemCardFlightModel()
//    var dataCardTrain  = ItemCardTrainModel()
//    var dataCardHotel  = ItemCardHotelModel()

}