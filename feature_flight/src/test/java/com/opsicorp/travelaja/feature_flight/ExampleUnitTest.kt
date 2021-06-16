package com.opsicorp.travelaja.feature_flight

import com.mobile.travelaja.utility.DateConverter
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        print(DateConverter().getDate("2020-04-04","yyyy-MM-dd","yyyy-MM-dd"))
//        println(DateConverter().getNextDay("yyyy-MM-dd","yyyy-MM-dd","2021-05-09",3))
//        assertEquals(4, 2 + 2)
    }
}