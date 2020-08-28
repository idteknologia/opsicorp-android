package com.opsigo.travelaja.module.cart.model

class CartModel {
    var typeCard       = 0
    var dataHeader     = CartHeaderModel()
    var dataCardFlight = ItemCardFlightModel()
//    var dataCardFlight = ArrayList<ItemCardFlightModel>()
    var dataCardTrain  = ItemCardTrainModel()
    var dataCardHotel  = ItemCardHotelModel()
}