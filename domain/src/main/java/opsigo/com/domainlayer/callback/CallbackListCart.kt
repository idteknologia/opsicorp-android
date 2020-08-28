package opsigo.com.domainlayer.callback

import opsigo.com.domainlayer.model.cart.CartModelAdapter

//import opsigo.com.domainlayer.model.aprover.CartModelAdapter


interface CallbackListCart {
    fun successLoad(approvalModel: ArrayList<CartModelAdapter>)
    fun failedLoad(message:String)
}