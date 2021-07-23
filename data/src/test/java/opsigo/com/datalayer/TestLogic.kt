package opsigo.com.datalayer

import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.signin.DataLoginModel
import org.junit.Test
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TestLogic {

    @Test
    fun testLogic(){
        val data = DataLoginModel()

        val dataDummySegment: ArrayList<testSegment>
        dataDummySegment  = dummyData()

        val mData = ArrayList<testSegment2>()

        var num = 0
        var sec = 0
        dataDummySegment.forEachIndexed { index, testSegment ->
            if (num==testSegment.num){
                val model = testSegment2()
                model.name = testSegment.name
                model.testSegment.add(testSegment.num.toString())
                mData.add(model)
                num++
            }
            else{
                val model = testSegment2()
                model.name = testSegment.name
                model.testSegment.add(testSegment.num.toString())
                mData.get((num-1)).testSegment.add(testSegment.num.toString())
            }
        }

        mData.forEachIndexed { index, testSegment2 ->
            println(Serializer.serialize(testSegment2,testSegment2::class.java))
        }
//        assertEquals(Serializer.deserialize(data.employId, DataLoginModel::class.java).employId,"cgvs")
    }

    private fun dummyData(): java.util.ArrayList<testSegment> {
        val dataDummySegment = ArrayList<testSegment>()
        for (i in 0 until 2){
            val mData = testSegment()
            mData.num = 0
            mData.seq = 0
            mData.name  = "Garuda"
            dataDummySegment.add(mData)
        }

        for (i in 0 until 2){
            val mData = testSegment()
            mData.num = 1
            mData.seq = 1
            mData.name  = "Sriwijaya"
            dataDummySegment.add(mData)
        }

        for (i in 0 until 2){
            val mData = testSegment()
            mData.num = 2
            mData.seq = 2
            mData.name  = "Kalstar"
            dataDummySegment.add(mData)
        }
        return dataDummySegment
    }


    class testSegment {
        var num = 0
        var seq = 0
        var name = ""
    }

    class testSegment2{
        var name = ""
        var testSegment = ArrayList<String>()
    }

    @Test
    fun testContains(){
        var data = "cahd.doc"
        if (data.contains("pdf")||data.contains("doc")||data.contains("xls")||data.contains("xlsx")){

        }
        else{
            print("loh")
        }
    }

}