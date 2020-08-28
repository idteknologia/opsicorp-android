package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.signin.CountryModel

interface CallbackCountry {
    fun successLoad(data: ArrayList<CountryModel>)
    fun failedLoad(message:String)
}