package com.opsigo.travelaja


import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.opsigo.travelaja.module.home.activity.HomeActivity
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import androidx.test.platform.app.InstrumentationRegistry
import com.opsigo.travelaja.utility.GetNameCountry
import org.hamcrest.CoreMatchers
import org.junit.Assert


@RunWith(AndroidJUnit4::class)
class TestHomeActivity {

    var lat = -7.3589352
    var lng = 112.6916272

    lateinit var instrumentationCtx: Context


    @Rule
    @JvmField
    val rule  = ActivityTestRule(HomeActivity::class.java)

    @get:Rule var permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION)
    lateinit var nameCountry : GetNameCountry

    @Before
    fun setup() {
//        instrumentationCtx = InstrumentationRegistry.getContext()
        nameCountry = GetNameCountry()
    }

    @Test
    fun viewTest(){

   //       test getNameCountry
          Assert.assertThat("Kecamatan Taman, Indonesia", CoreMatchers.`is`(nameCountry.nameCountry(instrumentationCtx,lat,lng)))

//           test flow ui when failed getDataLogin because internet not connect or etc
//        onView(withId(R.emplaoyId.btn_retry))
//                .perform(ViewActions.click())
//
//        onView(withId(R.emplaoyId.tv_weather_this_day))
//                .check(matches(isDisplayed()))

    }



}