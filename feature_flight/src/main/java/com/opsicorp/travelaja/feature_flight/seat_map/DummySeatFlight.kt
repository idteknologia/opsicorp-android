package com.opsicorp.travelaja.feature_flight.seat_map

import com.mobile.travelaja.utility.Constants
import opsigo.com.domainlayer.model.accomodation.flight.SeatFlightModel

class DummySeatFlight {

    val typeSeat            = "SEAT"
    val typeNumber          = "NUMBER"
    val typeSpacing         = "SPACING_CENTER"

    fun getDataSeat1(): ArrayList<SeatFlightModel> {
        val dataSeat = ArrayList<SeatFlightModel>()

        val modelNumber = SeatFlightModel()
        modelNumber.type       = typeNumber
        modelNumber.number     = "1"
        dataSeat.add(modelNumber)

        val modelSeat = SeatFlightModel()
        modelSeat.number     = "1"
        modelSeat.type       = typeSeat
        modelSeat.numberSeat = "1A"
        modelSeat.x          = 0
        modelSeat.y          = 0
        modelSeat.status     = Constants.PickSeat
        modelSeat.seatName   = "number1"
        modelSeat.price      = "70.000"
        dataSeat.add(modelSeat)


        val modelSeat2 = SeatFlightModel()
        modelSeat2.number     = "2"
        modelSeat2.type       = typeSeat
        modelSeat2.numberSeat = "2A"
        modelSeat2.x          = 0
        modelSeat2.y          = 0
        modelSeat2.status     = Constants.AvailableSeat
        modelSeat2.seatName   = "number2"
        modelSeat2.price      = "70.000"
        dataSeat.add(modelSeat2)


        val modelSpacing = SeatFlightModel()
        modelSpacing.type       = typeSpacing
        modelSpacing.number     = ""
        dataSeat.add(modelSpacing)


        val modelSeat3 = SeatFlightModel()
        modelSeat3.number     = "3"
        modelSeat3.type       = typeSeat
        modelSeat3.numberSeat = "3A"
        modelSeat3.x          = 0
        modelSeat3.y          = 0
        modelSeat3.status     = Constants.SoldSeat
        modelSeat3.seatName   = "number3"
        modelSeat3.price      = "70.000"
        dataSeat.add(modelSeat3)

        val modelSeat4 = SeatFlightModel()
        modelSeat4.number     = "4"
        modelSeat4.type       = typeSeat
        modelSeat4.numberSeat = "4A"
        modelSeat4.x          = 0
        modelSeat4.y          = 0
        modelSeat4.status     = Constants.AvailableSeat
        modelSeat4.seatName   = "number4"
        modelSeat4.price      = "70.000"
        dataSeat.add(modelSeat4)


        return dataSeat
    }


    fun getDataSeat2(): ArrayList<SeatFlightModel> {
        val dataSeat = ArrayList<SeatFlightModel>()

        val modelNumber = SeatFlightModel()
        modelNumber.type       = typeNumber
        modelNumber.number     = "1"
        dataSeat.add(modelNumber)

        val modelSeat = SeatFlightModel()
        modelSeat.number     = "1"
        modelSeat.type       = typeSeat
        modelSeat.numberSeat = "1A"
        modelSeat.x          = 0
        modelSeat.y          = 0
        modelSeat.status     = Constants.PickSeat
        modelSeat.seatName   = "number1"
        modelSeat.price      = "70.000"
        dataSeat.add(modelSeat)

        val modelSeat2 = SeatFlightModel()
        modelSeat2.number     = "2"
        modelSeat2.type       = typeSeat
        modelSeat2.numberSeat = "2A"
        modelSeat2.x          = 0
        modelSeat2.y          = 0
        modelSeat2.status     = Constants.AvailableSeat
        modelSeat2.seatName   = "number2"
        modelSeat2.price      = "70.000"
        dataSeat.add(modelSeat2)

        val modelSeat6 = SeatFlightModel()
        modelSeat6.number     = "3"
        modelSeat6.type       = typeSeat
        modelSeat6.numberSeat = "3A"
        modelSeat6.x          = 0
        modelSeat6.y          = 0
        modelSeat6.status     = Constants.AvailableSeat
        modelSeat6.seatName   = "number3"
        modelSeat6.price      = "70.000"
        dataSeat.add(modelSeat6)


        val modelSpacing = SeatFlightModel()
        modelSpacing.type       = typeSpacing
        modelSpacing.number     = ""
        dataSeat.add(modelSpacing)


        val modelSeat3 = SeatFlightModel()
        modelSeat3.number     = "4"
        modelSeat3.type       = typeSeat
        modelSeat3.numberSeat = "4A"
        modelSeat3.x          = 0
        modelSeat3.y          = 0
        modelSeat3.status     = Constants.SoldSeat
        modelSeat3.seatName   = "number4"
        modelSeat3.price      = "70.000"
        dataSeat.add(modelSeat3)

        val modelSeat4 = SeatFlightModel()
        modelSeat4.number     = "5"
        modelSeat4.type       = typeSeat
        modelSeat4.numberSeat = "5A"
        modelSeat4.x          = 0
        modelSeat4.y          = 0
        modelSeat4.status     = Constants.AvailableSeat
        modelSeat4.seatName   = "number5"
        modelSeat4.price      = "70.000"
        dataSeat.add(modelSeat4)

        val modelSeat5 = SeatFlightModel()
        modelSeat5.number     = "6"
        modelSeat5.type       = typeSeat
        modelSeat5.numberSeat = "6A"
        modelSeat5.x          = 0
        modelSeat5.y          = 0
        modelSeat5.status     = Constants.AvailableSeat
        modelSeat5.seatName   = "number6"
        modelSeat5.price      = "70.000"
        dataSeat.add(modelSeat5)

        return dataSeat
    }
}