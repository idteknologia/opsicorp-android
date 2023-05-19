package com.mobile.travelaja.module.signin.select_nationality.view

interface SelectNationalityView {
    fun callbackFromThisActivity(name: String,country: String,code:String,countryCode : String)
}