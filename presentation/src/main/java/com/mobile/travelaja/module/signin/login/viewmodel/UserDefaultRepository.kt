package com.mobile.travelaja.module.signin.login.viewmodel

import kotlinx.coroutines.coroutineScope
import opsigo.com.datalayer.model.result.Result
import opsigo.com.datalayer.model.signin.LoginEntity
import opsigo.com.datalayer.network.ServiceApi

class UserDefaultRepository(private val api: ServiceApi) : UserRepository {

    override suspend fun onLogin(url: String, body: MutableMap<String, Any?>): Result<LoginEntity> = coroutineScope {
        try {
            val result = api.onLogin(url,body)
            Result.Success(result)
        }catch (ex : Throwable){
            Result.Error(ex)
        }
    }

}