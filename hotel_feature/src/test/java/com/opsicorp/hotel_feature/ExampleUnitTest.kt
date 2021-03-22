package com.opsicorp.hotel_feature

import org.junit.Assert.*
import org.junit.Test
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val mData = ArrayList<ModelTest>()
        val data = ModelTest("${1}000", 1)
        val data1 = ModelTest("${4}000", 2)
        val data2 = ModelTest("${6}000", 3)
        val data3 = ModelTest("${2}000", 4)
        mData.add(data)
        mData.add(data1)
        mData.add(data2)
        mData.add(data3)
        mData.sortBy { it.price.toInt() }
        mData.forEach {
            println(it.price)
        }
        mData.sortBy { it.price.toInt() }
        mData.reverse()
        mData.forEach {
            println(it.price)
        }

    }

    class ModelTest(val price: String, val nomer: Int)

    @Test
    fun testing(){
        testCalculationDate("27 01 2021","28 01 2021","dd MM yyyy")
    }

    fun testCalculationDate(inputString1:String,inputString2:String,formatDateInput:String){
        val myFormat = SimpleDateFormat(formatDateInput)
        try {
            val date1: Date = myFormat.parse(inputString1)
            val date2: Date = myFormat.parse(inputString2)
            val diff: Long = date2.getTime() - date1.getTime()
            System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }
}