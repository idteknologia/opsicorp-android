package com.mobile.travelaja.module.signin.splash.view

interface SplashView {
    fun loadingData()
    fun successLoadData()
    fun failedGetData(loadData: String)
    fun cancelApp()
    fun updateApp()
    fun showDialogUpdate(message: String, string: String)
    fun tokenExpired()
}