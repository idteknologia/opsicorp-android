package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.signin.DataLoginModel

interface CallbackLogin {
    fun successGetData(data: DataLoginModel)
    fun failedGetData(message: String)
}