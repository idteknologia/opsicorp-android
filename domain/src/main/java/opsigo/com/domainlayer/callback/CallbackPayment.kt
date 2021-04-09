package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.PaymentModel


interface CallbackPayment {
    fun successLoad(data: PaymentModel)
    fun failedLoad(message:String)
}