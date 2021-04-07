package com.opsigo.travelaja


import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import androidx.test.rule.ActivityTestRule
import com.opsigo.travelaja.module.home.activity.HomeActivity
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.opsigo.travelaja.utility.Constants
import opsigo.com.datalayer.database.AccessDb
import opsigo.com.datalayer.database.OrderDbEntity
import org.hamcrest.CoreMatchers
import org.junit.Assert


@RunWith(AndroidJUnit4::class)
class TestDb {


    lateinit var context: Context

    @Rule
    @JvmField
    val rule  = ActivityTestRule(HomeActivity::class.java)

    @Before
    fun setup() {
        context = InstrumentationRegistry.getContext()

        print("okeee")
        addOrder()
    }

    @Test
    fun viewTest(){
        Assert.assertThat("1", CoreMatchers.`is`(AccessDb().getAll(context).toString()))
    }

    @Rule @JvmField
    val grantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//    @get:Rule var permissionRule = GrantPermissionRule.grant(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE)
    fun addOrder() {
        val data = OrderDbEntity()
        data.idUser           = ""
        data.departtemen      = ""
        data.purpose          = ""
        data.city             = ""
        data.date             = ""
        data.time             = "14:00-08:50"
        data.statusOrder      = "Card"
        data.statusTrip       = "Waiting"
        data.typeAccomodation = "Train"
        data.totalParticipant = ""
        data.tripCode         = System.currentTimeMillis().toString()
        data.listParticipant  = ""
        data.detailOrder      = ""
        data.detailListAccomodation  = Constants.DATA_LIST_TRAIN

        AccessDb().insertOrder(context,data)
    }
}