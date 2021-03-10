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
        val mData = ArrayList<ModelTest>()
        val data = ModelTest("${1}000",1)
        val data1 = ModelTest("${4}000",2)
        val data2 = ModelTest("${6}000",3)
        val data3 = ModelTest("${2}000",4)
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

    class ModelTest(val price:String,val nomer:Int)
}