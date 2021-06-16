package com.mobile.travelaja.module.signin.login.viewmodel

import opsigo.com.datalayer.network.ServiceApi

interface UserServiceLocator {
    companion object {
        private val LOCK = Any()
        private var instance: UserServiceLocator? = null

        fun instance(api: ServiceApi): UserServiceLocator {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = DefaultUserServiceLocator(api)
                }
                return instance!!
            }
        }

        fun swap(locator: UserServiceLocator): UserServiceLocator {
            instance = locator
            return instance!!
        }
    }

    fun getRepository(): UserRepository

    fun getServiceApi(): ServiceApi
}

open class DefaultUserServiceLocator(val api : ServiceApi) : UserServiceLocator {
    override fun getRepository(): UserRepository = UserDefaultRepository(api)

    override fun getServiceApi(): ServiceApi = api

}