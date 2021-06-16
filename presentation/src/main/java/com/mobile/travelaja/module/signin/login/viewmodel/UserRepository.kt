package com.mobile.travelaja.module.signin.login.viewmodel

import opsigo.com.datalayer.model.result.Result
import opsigo.com.datalayer.model.signin.LoginEntity

interface UserRepository {
    suspend fun onLogin(url : String ,body : MutableMap<String,Any>) : Result<LoginEntity>
}