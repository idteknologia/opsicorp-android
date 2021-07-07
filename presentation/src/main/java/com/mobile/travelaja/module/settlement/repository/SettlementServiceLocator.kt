package com.mobile.travelaja.module.settlement.repository

import opsigo.com.datalayer.network.ServiceApi

interface SettlementServiceLocator {
    companion object {
        private val LOCK = Any()
        private var instance: SettlementServiceLocator? = null

        fun instance(api: ServiceApi): SettlementServiceLocator {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = DefaultSettlementServiceLocator(api)
                }
                return instance!!
            }
        }

        fun swap(locator: SettlementServiceLocator): SettlementServiceLocator {
            instance = locator
            return instance!!
        }
    }

    fun getRepository(): SettlementRepository

    fun getServiceApi(): ServiceApi
}

open class DefaultSettlementServiceLocator(val api : ServiceApi) : SettlementServiceLocator {

    override fun getRepository(): SettlementRepository = DefaultSettlementRepository(getServiceApi())

    override fun getServiceApi(): ServiceApi = api

}