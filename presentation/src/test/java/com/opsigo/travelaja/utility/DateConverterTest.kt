package com.opsigo.travelaja.utility

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import java.text.SimpleDateFormat
import java.util.*


@RunWith(MockitoJUnitRunner::class)
class DateConverterTest {

    lateinit var dateConverter : DateConverter

    @Before
    fun setUp() {
        dateConverter = DateConverter()
    }

    @Test
    fun testSerializeHappyCase() {
        val formatter = SimpleDateFormat("EEEE,dd MMMM yyyy")

        var string = "2018-08-06 00:00:00"
        string = string.substring(0,10)

        println("xx : " + string)
        val obDate = Date()
        val obDateFormat =  SimpleDateFormat("yyyy-MM-dd")

        println(dateConverter.setDateFormatDayEEEddMMM(string))

        //val str = ""
        //val x = java.lang.Double.parseDouble(str)
        //println(x)
//        val year = DateConverter().formatingDateDefault("dd-MM-yyyy",string).split("-")[0]
//        val mount = DateConverter().formatingDateDefault("dd-MM-yyyy",string).split("-")[1].substring(0,3)
//        val day = DateConverter().formatingDateDefault("dd-MM-yyyy",string).split("-")[2]

//        println("a : " + day)
//        println("b : " + mount)
//        println("c : " + year)

//        println(obDateFormat.format(obDate.getTime()))
//        print(FormatingMonthIndonesian().format(formatter.format(obDate.getTime())))

//        assertThat("Senin, 02 September 2019", CoreMatchers.`is`(FormatingMonthIndonesian().format(formatter.format(obDate.getTime()))))

//        var imagePath = "pathimage/getDataLogin/user/0/com.khoiron.opsicorp/cache/CBN/6e58ae48-91fd-4b7b-bbeb-b071b8c49a36.jpg"
//        var splitImage = imagePath.split("/")
//        println(splitImage.get(splitImage.size-1))
//
//
//        print(dateConverter.getNameDayFromDate("2019-04-16"))
//        assertThat("Tuesday", CoreMatchers.`is`(dateConverter.getNameDayFromDate("2019-04-16")))

    }



}