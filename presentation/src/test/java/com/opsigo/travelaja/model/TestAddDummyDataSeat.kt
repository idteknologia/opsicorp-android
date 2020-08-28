package com.opsigo.travelaja.model

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class TestAddDummyDataSeat {

//    var dataCabin = ArrayList<CabinModel2>()

    @Test
    fun testAddDummyDataSeat() {
//        dataCabin = AddDummyDataSeat().addDummyDataCabin()
//
//        dataCabin.forEachIndexed { index, cabinModel2 ->
//            println("gerbang ${cabinModel2.numberCabin}")
//            cabinModel2.Seat.forEachIndexed { index, seatModel2 ->
//                println(seatModel2.numberSeat)
//            }
//        }


        var data = "Minggu, 1 Agustus 2016"
        setDateFrormattingDetailFlight(data)

    }

    fun setDateFrormattingDetailFlight(string: String){

        val mData = string.split(" ")[1]+" "+string.split(" ")[2].substring(0,3)

        Assert.assertThat("1 Agu", CoreMatchers.`is`(mData))
    }

}