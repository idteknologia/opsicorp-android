package opsigo.com.domainlayer.model.travel_request

class EstimatedCostTravelRequestModel(
    var estFlight : Double = 0.0,
    var estAllowance : Double = 0.0,
    var estAllowanceDaily : Double = 0.0,
    var estAllowanceMeal : Double = 0.0,
    var estAllowanceTransport : Double = 0.0,
    var estAllowanceEvent : Double = 0.0,
    var estLaundry : Double = 0.0,
    var estHotel : Double = 0.0,
    var estTransportation :Double = 0.0,
    var estMiscellaneous :Double = 0.0,
    var total :Double =  0.0
)