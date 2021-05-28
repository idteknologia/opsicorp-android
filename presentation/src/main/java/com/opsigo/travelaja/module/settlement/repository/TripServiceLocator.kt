package com.opsigo.travelaja.module.settlement.repository

import android.content.Context
import androidx.annotation.VisibleForTesting
import opsigo.com.datalayer.network.ServiceApi

interface TripServiceLocator {
    companion object {
        private val LOCK = Any()
        private var instance: TripServiceLocator? = null

        fun instance(api: ServiceApi): TripServiceLocator {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = DefaultTripServiceLocator(api)
                }
                return instance!!
            }
        }

        fun swap(locator: TripServiceLocator): TripServiceLocator {
            instance = locator
            return instance!!
        }
    }

    fun getRepository(): TripRepository

    fun getServiceApi(): ServiceApi


}

open class DefaultTripServiceLocator(val api : ServiceApi) : TripServiceLocator {


    override fun getRepository(): TripRepository = TripListRepository(getServiceApi())

    override fun getServiceApi(): ServiceApi = api

}