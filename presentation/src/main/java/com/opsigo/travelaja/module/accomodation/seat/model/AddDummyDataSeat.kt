package com.opsigo.travelaja.module.accomodation.seat.model

class AddDummyDataSeat {

    var dataCabin = ArrayList<CabinModel2>()

    fun addDummyDataCabin():ArrayList<CabinModel2>{
        addData()
        addData2()
        return dataCabin
    }

    fun addData() {
        var data = ArrayList<SeatModel2>()

        var seat = SeatModel2()
        seat.type = "bangku"
        seat.numberSeat = "1"
        seat.availability = "available"
        seat.selected = true
        seat.actived  = true
        seat.namePassager = "wijaya"


        var seat1 = SeatModel2()
        seat1.type = "bangku"
        seat1.numberSeat = "2"
        seat1.availability = "available"
        seat1.selected = true
        seat1.actived  = false
        seat1.namePassager = "wisnu"


        var seat2 = SeatModel2()
        seat2.seatNumberRow = "1"
        seat2.type          = "bukan bangku"
        seat2.numberSeat    = "3"

        var seat3 = SeatModel2()
        seat3.type = "bangku"
        seat3.availability = "available"
        seat3.numberSeat = "4"
        seat3.selected = true
        seat3.actived  = false
        seat3.namePassager = "Arip"

        var seat4 = SeatModel2()
        seat4.type = "bangku"
        seat4.availability = "available"
        seat4.numberSeat = "5"
        seat4.selected = true
        seat4.actived  = false
        seat4.namePassager = "Adi"

        var seat5 = SeatModel2()
        seat5.type = "bangku"
        seat5.availability = "available"
        seat5.numberSeat = "6"
        seat5.selected = false
        seat5.actived  = false

        var seat6 = SeatModel2()
        seat6.type = "bangku"
        seat6.availability = "available"
        seat6.numberSeat = "7"
        seat6.selected = false
        seat6.actived  = false

        var seat7 = SeatModel2()
        seat7.seatNumberRow  = "2"
        seat7.type = "bukan bangku"
        seat7.numberSeat = "8"
        seat7.selected = true
        seat7.actived  = false

        var seat8 = SeatModel2()
        seat8.type = "bangku"
        seat8.availability = "available"
        seat8.numberSeat = "9"
        seat8.selected = false
        seat8.actived  = false

        var seat9 = SeatModel2()
        seat9.type = "bangku"
        seat9.availability = "available"
        seat9.numberSeat = "10"
        seat9.selected = false
        seat9.actived  = false

        data.add(seat)
        data.add(seat1)
        data.add(seat2)
        data.add(seat3)
        data.add(seat4)
        data.add(seat5)
        data.add(seat6)
        data.add(seat7)
        data.add(seat8)
        data.add(seat9)

        var mData = CabinModel2()
        mData.numberCabin = "1"
        mData.Seat = data
        dataCabin.add(mData)

    }

    fun addData2() {
        var data = ArrayList<SeatModel2>()

        var seat = SeatModel2()
        seat.type = "bangku"
        seat.numberSeat = "11"
        seat.availability = "available"
        seat.selected = false
        seat.actived  = false


        var seat1 = SeatModel2()
        seat1.type = "bangku"
        seat1.numberSeat = "12"
        seat1.availability = "available"
        seat1.selected = false
        seat1.actived  = false


        var seat2 = SeatModel2()
        seat2.seatNumberRow = "1"
        seat2.type          = "bukan bangku"
        seat2.numberSeat    = "13"

        var seat3 = SeatModel2()
        seat3.type = "bangku"
        seat3.availability = "available"
        seat3.numberSeat = "14"
        seat3.selected = false
        seat3.actived  = false

        var seat4 = SeatModel2()
        seat4.type = "bangku"
        seat4.availability = "available"
        seat4.numberSeat = "15"
        seat4.selected = false
        seat4.actived  = false

        var seat5 = SeatModel2()
        seat5.type = "bangku"
        seat5.availability = "available"
        seat5.numberSeat = "16"
        seat5.selected = false
        seat5.actived  = false

        var seat6 = SeatModel2()
        seat6.type = "bangku"
        seat6.availability = "available"
        seat6.numberSeat = "17"
        seat6.selected = false
        seat6.actived  = false

        var seat7 = SeatModel2()
        seat7.seatNumberRow  = "2"
        seat7.type = "bukan bangku"
        seat7.numberSeat = "18"
        seat7.selected = true
        seat7.actived  = false

        var seat8 = SeatModel2()
        seat8.type = "bangku"
        seat8.availability = "available"
        seat8.numberSeat = "19"
        seat8.selected = false
        seat8.actived  = false

        var seat9 = SeatModel2()
        seat9.type = "bangku"
        seat9.availability = "available"
        seat9.numberSeat = "20"
        seat9.selected = false
        seat9.actived  = false

        data.add(seat)
        data.add(seat1)
        data.add(seat2)
        data.add(seat3)
        data.add(seat4)
        data.add(seat5)
        data.add(seat6)
        data.add(seat7)
        data.add(seat8)
        data.add(seat9)

        var mData = CabinModel2()
        mData.numberCabin = "2"
        mData.Seat = data
        dataCabin.add(mData)
    }
}