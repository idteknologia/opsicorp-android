package com.opsigo.travelaja.logic

import opsigo.com.datalayer.mapper.Serializer
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TestLoop {

    @Before
    fun setUp() {

    }

    var dates = ArrayList<String>()
    var model = ArrayList<TestingLop>()

    @Test
    fun test(){
        var data = ArrayList<TestingLop>()

        var mData = TestingLop()
        mData.date = "01-09-2019"
        mData.isi  = "cbgdakjac"
        mData.type = "body"

        var mData1 = TestingLop()
        mData1.date = "23-09-2019"
        mData1.isi  = "cbgdakjac"
        mData1.type = "body"

        var mData2 = TestingLop()
        mData2.date = "22-10-2019"
        mData2.isi  = "cbgdakjac"
        mData2.type = "body"

        var mData3 = TestingLop()
        mData3.date = "21-10-2019"
        mData3.isi  = "cbgdakjac"
        mData3.type = "body"

        data.add(mData)
        data.add(mData1)
        data.add(mData2)
        data.add(mData3)

        var lastDate = ""
        data.forEachIndexed { index, testingLop ->
            if (!testingLop.date.split("-")[1].equals(lastDate)){
                var mData9 = TestingLop()
                mData9.date = testingLop.date
                mData9.isi  = ""
                mData9.type = "Header"
                model.add(mData9)
                model.add(testingLop)
            }
            else{
                model.add(testingLop)
            }
            lastDate = testingLop.date.split("-")[1]
        }

        model.forEachIndexed { index, s ->
            println(Serializer.serialize(s,TestingLop::class.java))
        }

        Assert.assertThat("Tuesday", CoreMatchers.`is`(""))
    }

    class TestingLop{
        var date = ""
        var isi  = ""
        var type = ""
    }
}


