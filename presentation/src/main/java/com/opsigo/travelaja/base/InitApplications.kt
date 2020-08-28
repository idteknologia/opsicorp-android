package com.opsigo.travelaja.base

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.multidex.MultiDex

import com.opsigo.travelaja.di.component.*
import com.opsigo.travelaja.locale.LocaleManager
import com.opsigo.travelaja.locale.LocalePrefrences
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.*

class InitApplications:Application() {

    override fun onCreate() {
        super.onCreate()

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


