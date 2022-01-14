package com.mobile.travelaja.base

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.multidex.MultiDex

import com.mobile.travelaja.di.component.*
import com.mobile.travelaja.locale.LocaleManager
import com.mobile.travelaja.locale.LocalePrefrences
import com.mobile.travelaja.utility.Globals
import opsigo.com.datalayer.network.MyURL
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.*

class InitApplications:Application() {

    override fun onCreate() {
        super.onCreate()
        setGlobal()
        MultiDex.install(this)

        startKoin {

            androidContext(this@InitApplications)
            modules(listOf(HomeComponent().modules,LoginComponent().modules,
                    AdapterComponent().modules,CreateTripComponent().modules,
                    AccomodationComponent().modules))
        }

        instance = this

        //set Language
        LocalePrefrences.getInstance(this).selectedLocale?.let {
            var locale = Locale(it.lang)
            LocaleManager.getInstance().setCurrentLocale(this, locale)
        }


        // receive os locale change event
        val filter = IntentFilter(Intent.ACTION_LOCALE_CHANGED)
        registerReceiver(osLocaleChangeReceiver, filter)

    }

    private fun setGlobal() {
        if (Globals.getBaseUrl(this).isEmpty() || Globals.getBaseUrl(this).isBlank())
            Globals.setBaseUrl(this, MyURL.URL_TRAVELAJA)
    }

    companion object {
        lateinit var instance: InitApplications
            private set

        val appContext: Context
            get() = LocaleManager.getInstance().wrapContext(instance)
    }

    private val osLocaleChangeReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
        }
    }

}


