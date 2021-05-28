package com.opsigo.travelaja

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import opsigo.com.datalayer.model.result.City
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
    private var context = ApplicationProvider.getApplicationContext<Context>()


    @Test
    @Throws(Exception::class)
    fun addition_isCorrect() {
        Assert.assertEquals(4, (2 + 2).toLong())
    }

    @Test
    fun getFirstCharacter(){
        val name = "Jack "
        val first = name.split(" ")
        val uName = if (first.size >= 2){
            "${first[0].first()}${first[1].firstOrNull()?: ""}"
        }else {
            "${first[0].firstOrNull()}"
        }
        println(uName)
    }

    @Test
    fun conversionDate(){
        val date = "2021-04-15 00:00:00"
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val sdfOutput = SimpleDateFormat("EEEE", Locale("in"))
        val dInput = sdf.parse(date)
        val dOutput = sdfOutput.format(dInput)
        println(dOutput)
    }

    @Test
    fun isToday(){
        val date = "2021-04-15 00:00:00"
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val sdfOutput = SimpleDateFormat("yyyy-MM-dd", Locale("in"))
        val now = sdfOutput.format(Date())
        val dInput = sdfOutput.format(sdf.parse(date))
        println(now.equals(dInput))
    }

    @Test
    fun getCities(){
        val cities = TestUtil.getListObject<City>(context,R.raw.cities)
        assert(cities.isNotEmpty())
    }


}