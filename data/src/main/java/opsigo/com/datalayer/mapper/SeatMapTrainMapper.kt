package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.accomodation.train.seat.get_seat.SeatMapTrainEntity
import opsigo.com.datalayer.model.accomodation.train.seat.get_seat.SeatRowsItemItem
import opsigo.com.domainlayer.model.SeatTableModel
import opsigo.com.domainlayer.model.accomodation.train.CabinModel

class SeatMapTrainMapper {
    val statusAvailableSeat = "Available"
    val statusSoldSeat      = "SOLD"
    val statusPickSeat      = "PICK"
    val typeSeat     = "SEAT"
    val typeNumber   = "NUMBER"
    val typeSpacing  = "SPACING_CENTER"

    fun mapper(dataList:ArrayList<SeatMapTrainEntity>):ArrayList<CabinModel>{
        val mData = ArrayList<CabinModel>()

        dataList.forEachIndexed { index, dataMapTrainEntity ->

            val model = CabinModel()

            val seats = ArrayList<SeatTableModel>()
            dataMapTrainEntity.seatRows.forEachIndexed { i, list ->

                seats.add(addModelNumber(i))
                list.forEachIndexed { indexChild, seatRowsItemItem ->
                    if (seatRowsItemItem.seatStatus==-1){
                        seats.add(addModelSpacing(seatRowsItemItem))
                    }
                    else if (seatRowsItemItem.seatStatus==1){
                        seats.add(addModelSeatNotAvailable(seatRowsItemItem))
                    }
                    else if (seatRowsItemItem.seatStatus==0){
                        seats.add(addModelSeatAvailable(seatRowsItemItem,dataMapTrainEntity.coachName))
                    }
                }
            }
            model.seatTables.addAll(seats)
            model.idCabins         = index
            model.totalSeatPerRows = dataMapTrainEntity.seatRows.first().size+1

            mData.add(model)
        }

        return mData
    }

    private fun addModelSeatAvailable(seatRowsItemItem: SeatRowsItemItem,coachName:String): SeatTableModel {
        val st = SeatTableModel()
        st.type       = typeSeat
        st.status     = statusAvailableSeat
        st.numberSeat = seatRowsItemItem.seatNumber
        st.x          = seatRowsItemItem.X
        st.y          = seatRowsItemItem.Y
        st.seatName   = coachName
        return st
    }

    private fun addModelSpacing(seatRowsItemItem: SeatRowsItemItem): SeatTableModel {
        val st = SeatTableModel()
        st.type       = typeSpacing
        st.status     = ""
        st.numberSeat = ""
        st.x          = seatRowsItemItem.X
        st.y          = seatRowsItemItem.Y
        return st
    }

    private fun addModelNumber(index: Int): SeatTableModel {
        val st = SeatTableModel()
        st.type       = typeNumber
        st.status     = ""
        st.number     = index.toString()
        st.x          = 0
        st.y          = 0
        return st
    }

    private fun addModelSeatNotAvailable(seatRowsItemItem: SeatRowsItemItem): SeatTableModel {
        val st = SeatTableModel()
        st.type       = typeSeat
        st.status     = statusSoldSeat
        st.numberSeat = seatRowsItemItem.seatNumber
        st.x          = seatRowsItemItem.X
        st.y          = seatRowsItemItem.Y
        return st
    }
}