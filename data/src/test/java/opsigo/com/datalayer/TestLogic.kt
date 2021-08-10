package opsigo.com.datalayer

import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.signin.DataLoginModel
import org.junit.Test
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

    @Test
    fun testConverterDate(){
        val date = stringToDate("yyyy-MM-dd HH:mm","2021-08-10 13:20:10")
        print(SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date))
    }

    fun stringToDate(formatInput: String,dateString:String):Date{
        try {
            val mdformat = SimpleDateFormat(formatInput)
            val date = mdformat.parse(dateString)
            return date
        }catch (e: Exception){
            return Date()
        }
    }

    @Test
    fun removeDuplicateElementArray(){
        val data = ArrayList<models>()
        data.add(models("surabaya","1"))
        data.add(models("surabaya","1"))

        for (i in 0 until data.size) {
            var j = i + 1
            while (j < data.size) {
                if (data.get(i).name.equals(data.get(j).name)) {
                    data.removeAt(j)
                    j--
                }
                j++
            }
        }

        data.forEach {
            println(it.name)
            println(it.id)
        }
    }
    class models(
        val name :String= "",
        val id  :String= ""
        )
}