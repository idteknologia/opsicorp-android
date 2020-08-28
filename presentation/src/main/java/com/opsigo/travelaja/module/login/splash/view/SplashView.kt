package com.opsigo.travelaja.module.login.splash.view

interface SplashView {
    fun loadingData()
    fun successLoadData()
    fun failedGetData(loadData: String)
    fun cancelApp()
    fun updateApp()
    fun showDialogUpdate(message: String, string: String)
    fun tokenExpired()
}