package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.accomodation.flight.seat.SeatMapFlightEntity
import opsigo.com.datalayer.model.accomodation.flight.seat.SeatRowsItemItem
import opsigo.com.domainlayer.model.SeatTableModel
import opsigo.com.domainlayer.model.accomodation.flight.SeatAirlineModel
import opsigo.com.domainlayer.model.accomodation.flight.SeatFlightModel
import opsigo.com.domainlayer.model.accomodation.train.CabinModel

class SeatMapFlightMapper {
    val statusAvailableSeat = "Available"
    val statusSoldSeat      = "SOLD"
    val statusPickSeat      = "PICK"
    val typeSeat            = "SEAT"
    val typeNumber          = "NUMBER"
    val typeSpacing         = "SPACING_CENTER"

    fun mapper(string: String):ArrayList<SeatAirlineModel>{
        val mData = ArrayList<SeatAirlineModel>()

        val rspSeat = Serializer.deserialize(string,SeatMapFlightEntity::class.java)

        if (!rspSeat.result.rsFlightSeats.isNullOrEmpty()){

            rspSeat.result.rsFlightSeats.forEachIndexed {
                number, rsFlightSeatsItem ->

                val model = SeatAirlineModel()
                val seats = ArrayList<SeatFlightModel>()
                rsFlightSeatsItem.seatRows.forEachIndexed { index, model ->

                    if (model[0].seatNumber.toString().isNotEmpty()){
                        if (model[0].seatNumber.toString().matches("[a-zA-Z0-9]+".toRegex())){
                            seats.add(addModelNumber(1))
                        }
                        else{
                            seats.add(addModelNumber(-1))
                        }
                    }
                    else{
                        seats.add(addModelNumber(-1))
                    }

                    model.forEachIndexed { n, seatRowsItemItem ->

                        // gds
                        if("Seat".equals(seatRowsItemItem.seatType)){
                            if("Available".equals(seatRowsItemItem.availability)||"Open".equals(seatRowsItemItem.availability)){
                                seats.add(addModelSeatAvailable(seatRowsItemItem))
                            }
                            else{
                                seats.add(addModelSeatNotAvailable(seatRowsItemItem))
                            }
                        }
                        else if("Aisle".equals(seatRowsItemItem.seatType)){
                            seats.add(addModelSpacing(seatRowsItemItem))
                        }
                        // nongds
                        else if(seatRowsItemItem.seatType.isNotEmpty()){
                            if("Available".equals(seatRowsItemItem.availability)||"Open".equals(seatRowsItemItem.availability)){
                                seats.add(addModelSeatAvailable(seatRowsItemItem))
                            }
                            else{
                                if (seatRowsItemItem.seatNumber?.matches("[a-zA-Z0-9]+".toRegex())!!){
                                    seats.add(addModelSeatNotAvailable(seatRowsItemItem))
                                }
                                else{
                                    seats.add(addModelSpacing(seatRowsItemItem))
                                }
                            }
                        }
                        else{
                            seats.add(addModelSpacing(seatRowsItemItem))
                        }
                    }
                }

                if (!rsFlightSeatsItem.seats.isNullOrEmpty()){
                    if (seats.filter { it.status == statusAvailableSeat }.isNotEmpty()){
                        seats.filter { it.status == statusAvailableSeat }.first().status = statusPickSeat
                    }
                    model.dataSeat.addAll(seats)
                    model.nameFlight       = rsFlightSeatsItem.airline.toString()
                    model.nameAirCraft     = rsFlightSeatsItem.airCraftTypeName.toString()
                    model.totalRows        = rsFlightSeatsItem.seatRows[0].size+1

                    mData.add(model)
                }
            }
        }
        return mData
    }

    private fun addModelSeatAvailable(seatRowsItemItem: SeatRowsItemItem): SeatFlightModel {
        val st = SeatFlightModel()
        st.type       = typeSeat
        st.status     = statusAvailableSeat
        st.numberSeat = seatRowsItemItem.seatNumber.toString()
        st.x          = seatRowsItemItem.posX
        st.y          = seatRowsItemItem.posY
        st.seatName   = seatRowsItemItem.seatClassCode.toString()
        st.price      = seatRowsItemItem.seatFare.toString()
        return st
    }

    private fun addModelSpacing(seatRowsItemItem: SeatRowsItemItem): SeatFlightModel {
        val st = SeatFlightModel()
        st.type       = typeSpacing
        st.status     = ""
        st.numberSeat = ""
        st.x          = seatRowsItemItem.posX
        st.y          = seatRowsItemItem.posY
        return st
    }

    private fun addModelNumber(index: Int): SeatFlightModel {
        val st = SeatFlightModel()
        st.type       = typeNumber
        st.status     = ""
        if (index==-1){
            st.number     = ""
        }else{
            st.number     = index.toString()
        }
        st.x          = 0
        st.y          = 0
        return st
    }

    private fun addModelSeatNotAvailable(seatRowsItemItem: SeatRowsItemItem): SeatFlightModel {
        val st = SeatFlightModel()
        st.type       = typeSeat
        st.status     = statusSoldSeat
        st.numberSeat = seatRowsItemItem.seatNumber.toString()
        st.x          = seatRowsItemItem.posX
        st.y          = seatRowsItemItem.posY
        return st
    }
}