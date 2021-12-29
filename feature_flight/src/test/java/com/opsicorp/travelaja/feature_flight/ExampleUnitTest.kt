package com.opsicorp.travelaja.feature_flight

import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        var data = ArrayList<String>()
        var startDate = "2021-12-10"
        var endDate   = "2021-12-20"
        var formatDate = "yyyy-MM-dd"

        val sdf = SimpleDateFormat(formatDate)
        val c = Calendar.getInstance()
        c.time = sdf.parse(startDate)
        while (!c.time.after(sdf.parse(endDate))){
            data.add(sdf.format(c.time))
            c.add(Calendar.DATE,1)
        }
    }
}