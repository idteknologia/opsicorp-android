package opsigo.com.datalayer.datanetwork.dummy.accomodation

import opsigo.com.datalayer.model.cart.RoutesItem
import opsigo.com.datalayer.request_model.create_trip_plane.TripAttachmentsItemRequest
import opsigo.com.domainlayer.model.accomodation.flight.RouteMultiCityModel

class OrderAccomodationModel {

    var flightNumber         = ""
    var classFlightName      = ""
    var classFlightCode      = ""
    var typeTrip             = ""
    var idOrigin             = ""
    var idDestination        = ""
    var originName           = ""
    var destinationName      = ""
    var dateDeparture        = ""
    var dateArrival          = ""
    var adult                = 1
    var child                = 0
    var infant               = 0
    var airlinePreference    = ""
    var totalPassengerString = ""
    var totalPassengerInt    = ""
    var totalPassenger       = 0
    var originStationName    = ""
    var destinationStationName = ""
    var reasonCode             = ""
    var routes               = ArrayList<RouteMultiCityModel>()

}