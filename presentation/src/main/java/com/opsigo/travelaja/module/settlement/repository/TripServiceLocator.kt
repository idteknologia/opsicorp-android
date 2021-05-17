package com.opsigo.travelaja.module.settlement.repository

import android.content.Context
import androidx.annotation.VisibleForTesting
import opsigo.com.datalayer.network.ServiceApi

interface TripServiceLocator {
    companion object{
        private val LOCK = Any()
        private var instance: TripServiceLocator ?= null

        fun instance(context : Context): TripServiceLocator {
            synchronized(LOCK){
                if (instance == null){
                    instance = DefaultTripServiceLocator(context)
                }
                return instance!!
            }
        }

        fun swap(locator : TripServiceLocator) : TripServiceLocator{
            instance = locator
            return instance!!
        }
    }

    fun getRepository() : TripRepository

    fun getServiceApi() : ServiceApi


}

open class DefaultTripServiceLocator(val context : Context) : TripServiceLocator{

    private val api by lazy {
        ServiceApi.createRequest(context)
    }

    override fun getRepository(): TripRepository = TripListRepository(getServiceApi())

    override fun getServiceApi(): ServiceApi = api

}