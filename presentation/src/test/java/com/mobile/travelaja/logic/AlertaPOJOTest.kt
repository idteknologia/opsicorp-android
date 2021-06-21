package com.mobile.travelaja.logic

import android.content.Context
import com.mobile.travelaja.utility.DateConverter
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AlertaPOJOTest {
    @Mock
    var mMockContext: Context? = null

    @Test
    fun setDiaByTextView() {
        val texto = ArrayList<tesh>()
//
//        for (i in 0 until 4){
//            val tesh = tesh()
//            tesh.name = "oke${i}"
//            texto.add(tesh)
//        }
//
//        val tes = tesh()
//        tes.name = "oke0"

        val number = DateConverter().getDate("2019-12-11","yyyy-mm-dd","EEEE, dd-MMMM-yyyy")

        print(number)
//            val entity = Serializer.deserialize(str!!, SummaryEntity::class.java)
//            var tripSummary = SummaryEntityMapper().mapFrom(entity)
    }

    class tesh{
        var name = ""
    }

}