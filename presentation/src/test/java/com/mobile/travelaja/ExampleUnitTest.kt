package com.mobile.travelaja

import com.google.zxing.common.StringUtils
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test
import java.text.DecimalFormat
import java.text.NumberFormat
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
        val date = "2021-07-01T00:21:54Z"
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        val sdfOutput = SimpleDateFormat("yyyy-MM-dd", Locale("in"))
        val dInput = sdf.parse(date)
        val dOutput = sdfOutput.format(dInput)
        println(dOutput)
    }

    @Test
    fun isToday() {
        val date = "2021-07-01T00:21:54Z"
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        val sdfOutput = SimpleDateFormat("yyyy-MM-dd", Locale("in"))
        val now = sdfOutput.format(Date())
        val dInput = sdfOutput.format(sdf.parse(date))
        println(now.equals(dInput))
    }

    @Test
    fun formatNumber(){
        val format = NumberFormat.getCurrencyInstance()
        val symbol = (format as DecimalFormat).decimalFormatSymbols
        symbol.currencySymbol = ""
        format.decimalFormatSymbols = symbol
        format.minimumFractionDigits = 0
        val value = 0.0
        val result = format.format(value)
        println(result)
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