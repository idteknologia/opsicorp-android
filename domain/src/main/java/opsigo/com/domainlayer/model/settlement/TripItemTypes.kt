package opsigo.com.domainlayer.model.settlement

data class TripItemTypes(
    var Id: String,
    var Type: Int,
    var Name: String,
    var TripItems: MutableList<TripItem>
)

data class TripItem(
    var Id: String,
    var TripFlights: MutableList<TripData>,
    var TripHotels: MutableList<TripData>,
    var TripTrains: MutableList<TripData>
)

data class TripData(
    var Id: String,
    var Payments: MutableList<Payment>,
    var Segments: MutableList<Segment>,
    var Passengers: MutableList<Passenger>
)

data class Passenger(
    var Id: String,
    var TripFlightId:String,
    var TripTrainId:String,
    var Type: String,
    var Title: String,
    var FirstName: String,
    var LastName: String,
    var BirthDate: String,
    var Email: String,
    var HomePhone: String,
    var MobilePhone: String,
    var OtherPhone: String,
    var IdNumber: String,
    var Nationality: String,
    var CompanyCode: String,
    var CostCenterId: String,
    var items : Int
)

data class Segment(
    var Id: String,
    var TripFlightId: String,
    var FlightNumber: String,
    var AirlineImageUrl: String,
    var DepartDate: String,
    var DepartTime: String,
    var ArriveDate: String,
    var ArriveTime: String,
    var Origin: String,
    var OriginName: String,
    var Destination: String,
    var DestinationName: String,
    var Duration: String,
    var Category: String,
    var PositionName : String,

)

data class Payment(
    var Id: String,
    var TripFlightId: String,
    var Code: String,
    var Amount: Number,
    var Currency: String
)

