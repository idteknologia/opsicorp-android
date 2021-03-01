package com.opsicorp.hotel_feature

import opsigo.com.datalayer.mapper.Serializer
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val data = "a,"
        assertEquals(data.substring(0,data.length-1), "a")
    }
}