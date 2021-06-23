package com.mobile.travelaja

import com.google.zxing.common.StringUtils
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
//    private var context = ApplicationProvider.getApplicationContext<Context>()


    @Test
    @Throws(Exception::class)
    fun addition_isCorrect() {
        Assert.assertEquals(4, (2 + 2).toLong())
    }

    @Test
    fun getFirstCharacter() {
        val name = "Jack "
        val first = name.split(" ")
        val uName = if (first.size >= 2) {
            "${first[0].first()}${first[1].firstOrNull() ?: ""}"
        } else {
            "${first[0].firstOrNull()}"
        }
        println(uName)
    }

    @Test
    fun conversionDate() {
        val date = "2021-04-15 00:00:00"
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val sdfOutput = SimpleDateFormat("EEEE", Locale("in"))
        val dInput = sdf.parse(date)
        val dOutput = sdfOutput.format(dInput)
        println(dOutput)
    }

    @Test
    fun isToday() {
        val date = "2021-04-15 00:00:00"
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val sdfOutput = SimpleDateFormat("yyyy-MM-dd", Locale("in"))
        val now = sdfOutput.format(Date())
        val dInput = sdfOutput.format(sdf.parse(date))
        println(now.equals(dInput))
    }

    @Test
    fun getCities() {
//        val cities = TestUtil.getListObject<City>(context, R.raw.cities)
//        assert(cities.isNotEmpty())
    }

    @Test
    fun matchUrl() {
        val link = "https://pertamina-dtm3-qa.opsicorp.com"
        val link2 = "https://pertamina-dtm3-qa.opsicorp.com"
        assert(link.equals(link2))
    }


    @Test
    fun testing(){
        val data = "{\"Errors\":[\"[Message, Trip data for the period from 01.07.2021 00:00 to 05.07.2021 12:00 already exists]\"]}"
        val coba = JSONObject(data)
        println(coba.getJSONArray("Errors").getString(0))
    }

}