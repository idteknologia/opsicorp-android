package opsigo.com.domainlayer.usecase

import opsigo.com.domainlayer.callback.*

interface LoginRepository {
    fun getDataLogin(username:String, password:String, callbackLogin: CallbackLogin)
    fun getDataProfile(token:String,callbackProfile: CallbackProfile)
    fun getDataConfig(token:String,callbackConfig: CallbackConfig)
    fun getDataCountry(token: String,callbackCountry: CallbackCountry)
    fun getDataRegister(data :HashMap<Any, Any>,callback: CallbackString)
    fun getDataValidationOtp(data :HashMap<Any, Any>,callback: CallbackString)
    fun getCompletlyRegister(data :HashMap<Any, Any>,callback: CallbackString)

}